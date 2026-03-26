<template>
  <div class="profile-manager">
    <div class="page-header">
      <h2>个人简介管理</h2>
    </div>

    <div v-if="loading" class="loading-text">加载中...</div>
    <div v-else class="form-container glass-card">
      <div class="form-group">
        <label>简介标题</label>
        <input v-model="form.profile_title" placeholder="如: 你好，我是一名全栈工程师" />
      </div>
      <div class="form-group">
        <label>页面副标题</label>
        <input v-model="form.profile_subtitle" placeholder="如: 全栈工程师 · IT团队负责人 · 持续学习者" />
      </div>
      <div class="form-group">
        <label>介绍段落 1（支持 &lt;strong&gt; 标签加粗）</label>
        <textarea v-model="form.profile_intro_1" rows="3"></textarea>
      </div>
      <div class="form-group">
        <label>介绍段落 2</label>
        <textarea v-model="form.profile_intro_2" rows="3"></textarea>
      </div>
      <div class="form-group">
        <label>介绍段落 3</label>
        <textarea v-model="form.profile_intro_3" rows="3"></textarea>
      </div>
      <div class="form-group">
        <label>技术标签（逗号分隔）</label>
        <input v-model="form.profile_tags" placeholder="前端架构,Vue 2/3,React,Java" />
        <div class="tags-preview" v-if="form.profile_tags">
          <span class="tag" v-for="t in form.profile_tags.split(',').map(s => s.trim()).filter(Boolean)" :key="t">{{ t }}</span>
        </div>
      </div>

      <div class="form-actions">
        <button class="btn-primary" @click="handleSave" :disabled="saving">
          {{ saving ? '保存中...' : '保存配置' }}
        </button>
        <span v-if="saved" class="save-tip">保存成功</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { adminGetSiteConfig, adminSaveSiteConfig } from '@/api/blog'

const loading = ref(true)
const saving = ref(false)
const saved = ref(false)

const form = ref<Record<string, string>>({
  profile_title: '',
  profile_subtitle: '',
  profile_intro_1: '',
  profile_intro_2: '',
  profile_intro_3: '',
  profile_tags: '',
})

async function fetchData() {
  loading.value = true
  try {
    const res = await adminGetSiteConfig()
    const data = res.data || {}
    form.value = {
      profile_title: data.profile_title || '',
      profile_subtitle: data.profile_subtitle || '',
      profile_intro_1: data.profile_intro_1 || '',
      profile_intro_2: data.profile_intro_2 || '',
      profile_intro_3: data.profile_intro_3 || '',
      profile_tags: data.profile_tags || '',
    }
  } finally {
    loading.value = false
  }
}

async function handleSave() {
  saving.value = true
  saved.value = false
  try {
    await adminSaveSiteConfig(form.value)
    saved.value = true
    setTimeout(() => { saved.value = false }, 2000)
  } finally {
    saving.value = false
  }
}

onMounted(fetchData)
</script>

<style scoped>
.page-header {
  margin-bottom: 24px;
}

.page-header h2 {
  font-size: 1.4rem;
}

.loading-text {
  text-align: center;
  color: var(--text-muted);
  padding: 40px 0;
}

.form-container {
  padding: 32px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 0.85rem;
  color: var(--text-secondary);
  margin-bottom: 8px;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 10px 14px;
  background: var(--bg-primary);
  border: 1px solid var(--border-glass);
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  font-size: 0.9rem;
  font-family: var(--font-body);
  outline: none;
  transition: border-color 0.2s;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group textarea:focus {
  border-color: var(--accent-cyan);
}

.form-group textarea {
  resize: vertical;
}

.tags-preview {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-top: 10px;
}

.form-actions {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-top: 28px;
}

.btn-primary {
  padding: 10px 28px;
  background: var(--gradient-main);
  border: none;
  border-radius: var(--radius-sm);
  color: #fff;
  font-size: 0.95rem;
  cursor: pointer;
  font-family: var(--font-body);
  transition: opacity 0.2s;
}

.btn-primary:hover { opacity: 0.85; }
.btn-primary:disabled { opacity: 0.5; cursor: not-allowed; }

.save-tip {
  color: #22c55e;
  font-size: 0.9rem;
}
</style>
