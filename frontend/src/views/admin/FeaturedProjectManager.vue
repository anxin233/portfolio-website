<template>
  <div class="featured-manager">
    <div class="page-header">
      <h2>代表作品管理</h2>
    </div>

    <div v-if="loading" class="loading-text">加载中...</div>
    <template v-else>
      <div class="form-container glass-card">
        <h3 class="section-label">基本信息</h3>
        <div class="form-group">
          <label>项目标题</label>
          <input v-model="form.featured_title" placeholder="如: 轮胎企业产品生命周期管理系统（PLM）" />
        </div>
        <div class="form-group">
          <label>副标题</label>
          <input v-model="form.featured_subtitle" placeholder="如: 前端架构主导 · 全栈参与" />
        </div>
        <div class="form-group">
          <label>项目背景</label>
          <textarea v-model="form.featured_background" rows="4"></textarea>
        </div>
        <div class="form-group">
          <label>我的职责（每行一条，格式: 标题：描述）</label>
          <textarea v-model="form.featured_responsibilities" rows="6" placeholder="前端架构搭建：从零构建整体前端工程&#10;前端核心开发：复杂表单系统"></textarea>
        </div>
        <div class="form-group">
          <label>前端技术栈（逗号分隔）</label>
          <input v-model="form.featured_frontend_techs" placeholder="Vue 2/3,TypeScript,Element UI" />
        </div>
        <div class="form-group">
          <label>后端技术栈（逗号分隔）</label>
          <input v-model="form.featured_backend_techs" placeholder="Java,Spring Boot,MySQL" />
        </div>
        <div class="form-group">
          <label>项目亮点（每行一条）</label>
          <textarea v-model="form.featured_highlights" rows="5" placeholder="前端整体架构从零搭建&#10;支撑多部门协同的复杂业务流程"></textarea>
        </div>

        <div class="form-actions">
          <button class="btn-primary" @click="handleSaveConfig" :disabled="savingConfig">
            {{ savingConfig ? '保存中...' : '保存基本信息' }}
          </button>
          <span v-if="configSaved" class="save-tip">保存成功</span>
        </div>
      </div>

      <!-- Challenges -->
      <div class="challenges-section">
        <div class="section-header">
          <h3 class="section-label">技术难点与方案</h3>
          <button class="btn-primary btn-sm-primary" @click="openCreateChallenge">+ 新增</button>
        </div>

        <div class="challenge-list">
          <div v-if="!challenges.length" class="empty-text">暂无数据</div>
          <div v-for="(c, idx) in challenges" :key="c.id" class="challenge-row glass-card">
            <div class="row-order">{{ idx + 1 }}</div>
            <div class="row-content">
              <div class="row-title">{{ c.title }}</div>
              <div class="row-detail"><strong>问题：</strong>{{ c.problem }}</div>
              <div class="row-detail"><strong>方案：</strong>{{ c.solution }}</div>
              <div class="row-detail result" v-if="c.result"><strong>效果：</strong>{{ c.result }}</div>
            </div>
            <div class="row-actions">
              <button class="btn-sm btn-move" :disabled="idx === 0" @click="handleMoveChallenge(idx, 'up')">↑</button>
              <button class="btn-sm btn-move" :disabled="idx === challenges.length - 1" @click="handleMoveChallenge(idx, 'down')">↓</button>
              <button class="btn-sm btn-edit" @click="openEditChallenge(c)">编辑</button>
              <button class="btn-sm btn-delete" @click="handleDeleteChallenge(c)">删除</button>
            </div>
          </div>
        </div>
      </div>
    </template>

    <!-- Challenge Dialog -->
    <div v-if="showChallengeDialog" class="dialog-mask" @click.self="showChallengeDialog = false">
      <div class="dialog glass-card">
        <h3>{{ editingChallengeId ? '编辑技术难点' : '新增技术难点' }}</h3>
        <div class="form-group">
          <label>标题</label>
          <input v-model="challengeForm.title" placeholder="如: 复杂表单与多步骤流程" />
        </div>
        <div class="form-group">
          <label>问题</label>
          <textarea v-model="challengeForm.problem" rows="3"></textarea>
        </div>
        <div class="form-group">
          <label>方案</label>
          <textarea v-model="challengeForm.solution" rows="3"></textarea>
        </div>
        <div class="form-group">
          <label>效果（可选）</label>
          <input v-model="challengeForm.result" />
        </div>
        <div class="form-group">
          <label>排序</label>
          <input v-model.number="challengeForm.sortOrder" type="number" />
        </div>
        <div class="dialog-actions">
          <button class="btn-cancel" @click="showChallengeDialog = false">取消</button>
          <button class="btn-primary" @click="handleSaveChallenge" :disabled="savingChallenge">
            {{ savingChallenge ? '保存中...' : '保存' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import {
  adminGetSiteConfig, adminSaveSiteConfig,
  adminGetChallenges, adminCreateChallenge, adminUpdateChallenge, adminDeleteChallenge,
  type ChallengeData,
} from '@/api/blog'

const loading = ref(true)
const savingConfig = ref(false)
const configSaved = ref(false)

const form = ref<Record<string, string>>({
  featured_title: '',
  featured_subtitle: '',
  featured_background: '',
  featured_responsibilities: '',
  featured_frontend_techs: '',
  featured_backend_techs: '',
  featured_highlights: '',
})

const challenges = ref<ChallengeData[]>([])
const showChallengeDialog = ref(false)
const savingChallenge = ref(false)
const editingChallengeId = ref<number | null>(null)
const challengeForm = ref<ChallengeData>({
  title: '', problem: '', solution: '', result: '', sortOrder: 0,
})

async function fetchData() {
  loading.value = true
  try {
    const [configRes, challengesRes] = await Promise.all([
      adminGetSiteConfig(), adminGetChallenges(),
    ])
    const data = configRes.data || {}
    form.value = {
      featured_title: data.featured_title || '',
      featured_subtitle: data.featured_subtitle || '',
      featured_background: data.featured_background || '',
      featured_responsibilities: data.featured_responsibilities || '',
      featured_frontend_techs: data.featured_frontend_techs || '',
      featured_backend_techs: data.featured_backend_techs || '',
      featured_highlights: data.featured_highlights || '',
    }
    challenges.value = challengesRes.data || []
  } finally {
    loading.value = false
  }
}

async function handleSaveConfig() {
  savingConfig.value = true
  configSaved.value = false
  try {
    await adminSaveSiteConfig(form.value)
    configSaved.value = true
    setTimeout(() => { configSaved.value = false }, 2000)
  } finally {
    savingConfig.value = false
  }
}

function openCreateChallenge() {
  editingChallengeId.value = null
  challengeForm.value = { title: '', problem: '', solution: '', result: '', sortOrder: challenges.value.length + 1 }
  showChallengeDialog.value = true
}

function openEditChallenge(c: ChallengeData) {
  editingChallengeId.value = c.id!
  challengeForm.value = { ...c }
  showChallengeDialog.value = true
}

async function handleSaveChallenge() {
  if (!challengeForm.value.title) return
  savingChallenge.value = true
  try {
    if (editingChallengeId.value) {
      await adminUpdateChallenge(editingChallengeId.value, challengeForm.value)
    } else {
      await adminCreateChallenge(challengeForm.value)
    }
    showChallengeDialog.value = false
    const res = await adminGetChallenges()
    challenges.value = res.data || []
  } finally {
    savingChallenge.value = false
  }
}

async function handleMoveChallenge(idx: number, direction: 'up' | 'down') {
  const targetIdx = direction === 'up' ? idx - 1 : idx + 1
  if (targetIdx < 0 || targetIdx >= challenges.value.length) return
  const current = challenges.value[idx]
  const target = challenges.value[targetIdx]
  const currentOrder = current.sortOrder
  const targetOrder = target.sortOrder
  if (currentOrder === targetOrder) {
    current.sortOrder = direction === 'up' ? targetOrder - 1 : targetOrder + 1
  } else {
    current.sortOrder = targetOrder
    target.sortOrder = currentOrder
  }
  await Promise.all([
    adminUpdateChallenge(current.id!, current),
    adminUpdateChallenge(target.id!, target),
  ])
  const res = await adminGetChallenges()
  challenges.value = res.data || []
}

async function handleDeleteChallenge(c: ChallengeData) {
  if (!confirm(`确定删除「${c.title}」？`)) return
  await adminDeleteChallenge(c.id!)
  const res = await adminGetChallenges()
  challenges.value = res.data || []
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

.loading-text, .empty-text {
  text-align: center;
  color: var(--text-muted);
  padding: 40px 0;
}

.form-container {
  padding: 32px;
  margin-bottom: 32px;
}

.section-label {
  font-size: 1.1rem;
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--border-glass);
}

.form-group {
  margin-bottom: 16px;
}

.form-group label {
  display: block;
  font-size: 0.85rem;
  color: var(--text-secondary);
  margin-bottom: 6px;
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

.form-actions {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-top: 24px;
}

.btn-primary {
  padding: 10px 28px;
  background: var(--gradient-main);
  border: none;
  border-radius: var(--radius-sm);
  color: #fff;
  font-size: 0.9rem;
  cursor: pointer;
  font-family: var(--font-body);
  transition: opacity 0.2s;
}

.btn-primary:hover { opacity: 0.85; }
.btn-primary:disabled { opacity: 0.5; cursor: not-allowed; }

.btn-sm-primary {
  padding: 6px 16px;
  font-size: 0.82rem;
}

.save-tip {
  color: #22c55e;
  font-size: 0.9rem;
}

/* Challenges */
.challenges-section {
  margin-top: 8px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.challenge-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.challenge-row {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 20px 24px;
}

.row-order {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  background: var(--gradient-subtle);
  border: 1px solid var(--border-glass);
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: var(--font-mono);
  font-size: 0.8rem;
  color: var(--accent-cyan);
  flex-shrink: 0;
  margin-top: 2px;
}

.row-content {
  flex: 1;
  min-width: 0;
}

.row-title {
  font-weight: 600;
  font-size: 1rem;
  margin-bottom: 8px;
  color: var(--accent-cyan);
}

.row-detail {
  font-size: 0.85rem;
  color: var(--text-secondary);
  line-height: 1.6;
  margin-bottom: 4px;
}

.row-detail strong {
  color: var(--text-primary);
}

.row-detail.result {
  color: var(--accent-green, #22c55e);
}

.row-actions {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
}

.btn-sm {
  padding: 6px 14px;
  border-radius: var(--radius-sm);
  font-size: 0.82rem;
  cursor: pointer;
  border: 1px solid var(--border-glass);
  background: transparent;
  font-family: var(--font-body);
  transition: all 0.2s;
}

.btn-move {
  color: var(--accent-purple, #a855f7);
  border-color: rgba(168, 85, 247, 0.3);
  min-width: 32px;
  padding: 6px 8px;
  font-size: 0.95rem;
  line-height: 1;
}
.btn-move:hover:not(:disabled) { background: rgba(168, 85, 247, 0.1); }
.btn-move:disabled { opacity: 0.3; cursor: not-allowed; }

.btn-edit {
  color: var(--accent-cyan);
  border-color: rgba(0, 212, 255, 0.3);
}
.btn-edit:hover { background: rgba(0, 212, 255, 0.1); }

.btn-delete {
  color: var(--accent-pink);
  border-color: rgba(255, 45, 149, 0.3);
}
.btn-delete:hover { background: rgba(255, 45, 149, 0.1); }

/* Dialog */
.dialog-mask {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 200;
}

.dialog {
  width: 520px;
  max-width: 90vw;
  padding: 32px;
  max-height: 80vh;
  overflow-y: auto;
}

.dialog h3 {
  font-size: 1.2rem;
  margin-bottom: 20px;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
}

.btn-cancel {
  padding: 8px 20px;
  background: transparent;
  border: 1px solid var(--border-glass);
  border-radius: var(--radius-sm);
  color: var(--text-secondary);
  font-size: 0.9rem;
  cursor: pointer;
  font-family: var(--font-body);
  transition: all 0.2s;
}

.btn-cancel:hover {
  border-color: var(--text-primary);
  color: var(--text-primary);
}
</style>
