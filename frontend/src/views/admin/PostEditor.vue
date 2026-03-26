<template>
  <div class="post-editor">
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? '编辑文章' : '写新文章' }}</h1>
      <div class="header-actions">
        <button class="btn btn-outline" @click="saveDraft" :disabled="saving">保存草稿</button>
        <button class="btn btn-primary" @click="publish" :disabled="saving">
          {{ saving ? '保存中...' : '发布文章' }}
        </button>
      </div>
    </div>

    <div class="editor-grid">
      <!-- Left: Form -->
      <div class="editor-form">
        <div class="form-group">
          <label>标题</label>
          <input
            v-model="form.title"
            type="text"
            placeholder="输入文章标题"
            class="input-title"
          />
        </div>

        <div class="form-row">
          <div class="form-group">
            <label>分类</label>
            <select v-model="form.category" class="form-select">
              <option value="">选择分类</option>
              <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
            </select>
          </div>
          <div class="form-group">
            <label>阅读时间（分钟）</label>
            <input v-model.number="form.readTime" type="number" min="1" class="form-input" />
          </div>
        </div>

        <div class="form-group">
          <label>标签 <span class="label-hint">（英文逗号分隔）</span></label>
          <input v-model="form.tags" type="text" placeholder="Vue 3, TypeScript, Architecture" class="form-input" />
        </div>

        <div class="form-group">
          <label>摘要</label>
          <textarea v-model="form.excerpt" rows="3" placeholder="一两句话概括文章内容" class="form-textarea"></textarea>
        </div>

        <div class="form-group editor-content-group">
          <div class="content-header">
            <label>正文 <span class="label-hint">（支持 Markdown）</span></label>
            <button class="preview-toggle" @click="showPreview = !showPreview">
              {{ showPreview ? '隐藏预览' : '显示预览' }}
            </button>
          </div>
          <textarea
            v-model="form.content"
            rows="20"
            placeholder="使用 Markdown 编写文章正文..."
            class="form-textarea content-textarea"
          ></textarea>
        </div>
      </div>

      <!-- Right: Preview -->
      <div class="editor-preview glass-card" v-if="showPreview">
        <div class="preview-header">
          <span>实时预览</span>
        </div>
        <div class="preview-body" v-html="renderedContent"></div>
      </div>
    </div>

    <p v-if="errorMsg" class="error-msg">{{ errorMsg }}</p>
    <p v-if="successMsg" class="success-msg">{{ successMsg }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { adminCreatePost, adminUpdatePost, adminGetPost, type BlogPost } from '@/api/blog'

const route = useRoute()
const router = useRouter()

const isEdit = computed(() => !!route.params.id)
const showPreview = ref(true)
const saving = ref(false)
const errorMsg = ref('')
const successMsg = ref('')

const categories = ['前端', '后端', '架构', '团队', '其他']

const form = ref<BlogPost>({
  title: '',
  excerpt: '',
  content: '',
  category: '',
  tags: '',
  readTime: 5,
  published: false,
})

const renderedContent = computed(() => {
  const raw = form.value.content || ''
  return raw
    .replace(/### (.*)/g, '<h3>$1</h3>')
    .replace(/## (.*)/g, '<h2>$1</h2>')
    .replace(/# (.*)/g, '<h1>$1</h1>')
    .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
    .replace(/\*(.*?)\*/g, '<em>$1</em>')
    .replace(/`([^`]+)`/g, '<code>$1</code>')
    .replace(/```([\s\S]*?)```/g, '<pre><code>$1</code></pre>')
    .replace(/^- (.*)/gm, '<li>$1</li>')
    .replace(/(<li>.*<\/li>\n?)+/g, '<ul>$&</ul>')
    .replace(/\n\n/g, '</p><p>')
    .replace(/^(?!<)/, '<p>')
    .replace(/(?!>)$/, '</p>')
})

async function saveDraft() {
  form.value.published = false
  await savePost()
}

async function publish() {
  if (!form.value.title) {
    errorMsg.value = '请填写文章标题'
    return
  }
  if (!form.value.category) {
    errorMsg.value = '请选择文章分类'
    return
  }
  form.value.published = true
  await savePost()
}

async function savePost() {
  saving.value = true
  errorMsg.value = ''
  successMsg.value = ''

  try {
    let res: any
    if (isEdit.value) {
      res = await adminUpdatePost(Number(route.params.id), form.value)
    } else {
      res = await adminCreatePost(form.value)
    }

    if (res.code === 200) {
      successMsg.value = form.value.published ? '文章已发布！' : '草稿已保存！'
      if (!isEdit.value && res.data?.id) {
        setTimeout(() => router.push(`/admin/post/edit/${res.data.id}`), 800)
      }
    } else {
      errorMsg.value = res.message || '保存失败'
    }
  } catch (e: any) {
    errorMsg.value = e.message || '保存失败，请重试'
  } finally {
    saving.value = false
  }
}

onMounted(async () => {
  if (isEdit.value) {
    try {
      const res: any = await adminGetPost(Number(route.params.id))
      if (res.code === 200) {
        form.value = res.data
      }
    } catch (_) {
      errorMsg.value = '加载文章失败'
    }
  }
})

watch([errorMsg, successMsg], () => {
  if (errorMsg.value || successMsg.value) {
    setTimeout(() => { errorMsg.value = ''; successMsg.value = '' }, 3000)
  }
})
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  flex-wrap: wrap;
  gap: 16px;
}

.page-title {
  font-size: 1.8rem;
  font-weight: 800;
  background: var(--gradient-main);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.editor-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
  align-items: start;
}

.editor-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-group label {
  font-size: 0.85rem;
  font-weight: 600;
  color: var(--text-secondary);
}

.label-hint {
  font-weight: 400;
  color: var(--text-muted);
  font-size: 0.8rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.input-title {
  padding: 14px 18px;
  background: var(--bg-glass);
  border: 1px solid var(--border-glass);
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  font-size: 1.2rem;
  font-weight: 700;
  outline: none;
  font-family: var(--font-body);
  transition: border-color 0.3s;
}

.input-title:focus {
  border-color: var(--accent-cyan);
}

.input-title::placeholder {
  color: var(--text-muted);
  font-weight: 400;
}

.form-input,
.form-select,
.form-textarea {
  padding: 10px 14px;
  background: var(--bg-glass);
  border: 1px solid var(--border-glass);
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  font-size: 0.9rem;
  outline: none;
  font-family: var(--font-body);
  transition: border-color 0.3s;
}

.form-input:focus,
.form-select:focus,
.form-textarea:focus {
  border-color: var(--accent-cyan);
}

.form-select {
  cursor: pointer;
}

.form-select option {
  background: var(--bg-secondary);
  color: var(--text-primary);
}

.form-textarea {
  resize: vertical;
  line-height: 1.7;
}

.content-textarea {
  font-family: var(--font-mono);
  font-size: 0.88rem;
  min-height: 500px;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.preview-toggle {
  background: none;
  border: 1px solid var(--border-glass);
  color: var(--text-muted);
  padding: 4px 12px;
  border-radius: 6px;
  font-size: 0.78rem;
  cursor: pointer;
  font-family: var(--font-body);
  transition: all 0.2s;
}

.preview-toggle:hover {
  border-color: var(--accent-cyan);
  color: var(--accent-cyan);
}

/* Preview */
.editor-preview {
  position: sticky;
  top: 92px;
  max-height: calc(100vh - 120px);
  overflow-y: auto;
  padding: 0;
}

.preview-header {
  padding: 12px 20px;
  border-bottom: 1px solid var(--border-glass);
  font-size: 0.85rem;
  color: var(--text-muted);
  font-weight: 600;
}

.preview-body {
  padding: 24px;
  font-size: 0.95rem;
  line-height: 1.8;
  color: var(--text-secondary);
}

.preview-body :deep(h1) {
  font-size: 1.6rem;
  color: var(--text-primary);
  margin: 24px 0 12px;
}

.preview-body :deep(h2) {
  font-size: 1.3rem;
  color: var(--text-primary);
  margin: 20px 0 10px;
  padding-bottom: 6px;
  border-bottom: 1px solid var(--border-glass);
}

.preview-body :deep(h3) {
  font-size: 1.1rem;
  color: var(--text-primary);
  margin: 16px 0 8px;
}

.preview-body :deep(strong) {
  color: var(--accent-cyan);
}

.preview-body :deep(code) {
  font-family: var(--font-mono);
  font-size: 0.85em;
  padding: 2px 6px;
  background: rgba(0, 212, 255, 0.08);
  border-radius: 4px;
  color: var(--accent-cyan);
}

.preview-body :deep(pre) {
  background: var(--bg-primary);
  border: 1px solid var(--border-glass);
  border-radius: var(--radius-sm);
  padding: 16px;
  margin: 16px 0;
  overflow-x: auto;
}

.preview-body :deep(pre code) {
  background: none;
  padding: 0;
}

.preview-body :deep(ul) {
  padding-left: 20px;
  margin: 8px 0;
}

.preview-body :deep(li) {
  margin-bottom: 4px;
}

.error-msg {
  margin-top: 16px;
  padding: 10px 16px;
  background: rgba(255, 45, 149, 0.08);
  border: 1px solid rgba(255, 45, 149, 0.2);
  border-radius: var(--radius-sm);
  color: var(--accent-pink);
  font-size: 0.9rem;
}

.success-msg {
  margin-top: 16px;
  padding: 10px 16px;
  background: rgba(0, 228, 143, 0.08);
  border: 1px solid rgba(0, 228, 143, 0.2);
  border-radius: var(--radius-sm);
  color: var(--accent-green);
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .editor-grid {
    grid-template-columns: 1fr;
  }

  .editor-preview {
    position: static;
    max-height: 400px;
  }

  .form-row {
    grid-template-columns: 1fr;
  }
}
</style>
