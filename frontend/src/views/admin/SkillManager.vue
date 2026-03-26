<template>
  <div class="skill-manager">
    <div class="page-header">
      <h2>技能图谱管理</h2>
      <button class="btn-primary" @click="openCreate">+ 新增技能</button>
    </div>

    <div class="skill-list">
      <div v-if="loading" class="loading-text">加载中...</div>
      <div v-else-if="!items.length" class="empty-text">暂无技能数据</div>
      <div
        v-for="(item, idx) in items"
        :key="item.id"
        class="skill-row glass-card"
      >
        <div class="row-order">{{ idx + 1 }}</div>
        <div class="row-content">
          <div class="row-header">
            <span class="row-name">{{ item.name }}</span>
            <span class="row-level">{{ item.level }}%</span>
          </div>
          <div class="row-bar">
            <div class="row-bar-fill" :style="{ width: item.level + '%' }"></div>
          </div>
          <div class="row-desc">{{ item.description }}</div>
        </div>
        <div class="row-actions">
          <button class="btn-sm btn-move" :disabled="idx === 0" @click="handleMove(idx, 'up')">↑</button>
          <button class="btn-sm btn-move" :disabled="idx === items.length - 1" @click="handleMove(idx, 'down')">↓</button>
          <button class="btn-sm btn-edit" @click="openEdit(item)">编辑</button>
          <button class="btn-sm btn-delete" @click="handleDelete(item)">删除</button>
        </div>
      </div>
    </div>

    <!-- Dialog -->
    <div v-if="showDialog" class="dialog-mask" @click.self="showDialog = false">
      <div class="dialog glass-card">
        <h3>{{ editingId ? '编辑技能' : '新增技能' }}</h3>
        <div class="form-group">
          <label>技能名称</label>
          <input v-model="form.name" placeholder="如: Vue 2 / Vue 3" />
        </div>
        <div class="form-group">
          <label>熟练度（0-100）</label>
          <div class="level-input">
            <input v-model.number="form.level" type="range" min="0" max="100" class="range-input" />
            <span class="level-value">{{ form.level }}%</span>
          </div>
        </div>
        <div class="form-group">
          <label>描述</label>
          <textarea v-model="form.description" rows="2" placeholder="简要说明项目实战经验"></textarea>
        </div>
        <div class="form-group">
          <label>排序（数字越小越靠前）</label>
          <input v-model.number="form.sortOrder" type="number" />
        </div>
        <div class="dialog-actions">
          <button class="btn-cancel" @click="showDialog = false">取消</button>
          <button class="btn-primary" @click="handleSave" :disabled="saving">
            {{ saving ? '保存中...' : '保存' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import {
  adminGetSkills,
  adminCreateSkill,
  adminUpdateSkill,
  adminDeleteSkill,
  type SkillItem,
} from '@/api/blog'

const items = ref<SkillItem[]>([])
const loading = ref(true)
const showDialog = ref(false)
const saving = ref(false)
const editingId = ref<number | null>(null)

const form = ref<SkillItem>({
  name: '',
  level: 80,
  description: '',
  sortOrder: 0,
})

async function fetchData() {
  loading.value = true
  try {
    const res = await adminGetSkills()
    items.value = res.data || []
  } catch {
    items.value = []
  } finally {
    loading.value = false
  }
}

function openCreate() {
  editingId.value = null
  form.value = { name: '', level: 80, description: '', sortOrder: items.value.length + 1 }
  showDialog.value = true
}

function openEdit(item: SkillItem) {
  editingId.value = item.id!
  form.value = { ...item }
  showDialog.value = true
}

async function handleSave() {
  if (!form.value.name) return
  saving.value = true
  try {
    if (editingId.value) {
      await adminUpdateSkill(editingId.value, form.value)
    } else {
      await adminCreateSkill(form.value)
    }
    showDialog.value = false
    await fetchData()
  } finally {
    saving.value = false
  }
}

async function handleMove(idx: number, direction: 'up' | 'down') {
  const targetIdx = direction === 'up' ? idx - 1 : idx + 1
  if (targetIdx < 0 || targetIdx >= items.value.length) return
  const current = items.value[idx]
  const target = items.value[targetIdx]
  const currentOrder = current.sortOrder
  const targetOrder = target.sortOrder
  if (currentOrder === targetOrder) {
    current.sortOrder = direction === 'up' ? targetOrder - 1 : targetOrder + 1
  } else {
    current.sortOrder = targetOrder
    target.sortOrder = currentOrder
  }
  await Promise.all([
    adminUpdateSkill(current.id!, current),
    adminUpdateSkill(target.id!, target),
  ])
  await fetchData()
}

async function handleDelete(item: SkillItem) {
  if (!confirm(`确定删除「${item.name}」？`)) return
  await adminDeleteSkill(item.id!)
  await fetchData()
}

onMounted(fetchData)
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.page-header h2 {
  font-size: 1.4rem;
}

.btn-primary {
  padding: 8px 20px;
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

.skill-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.loading-text, .empty-text {
  text-align: center;
  color: var(--text-muted);
  padding: 40px 0;
}

.skill-row {
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

.row-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.row-name {
  font-weight: 600;
  font-size: 1rem;
}

.row-level {
  font-family: var(--font-mono);
  font-size: 0.85rem;
  color: var(--accent-cyan);
}

.row-bar {
  height: 4px;
  background: rgba(255, 255, 255, 0.06);
  border-radius: 2px;
  overflow: hidden;
  margin-bottom: 8px;
}

.row-bar-fill {
  height: 100%;
  background: var(--gradient-main);
  border-radius: 2px;
}

.row-desc {
  font-size: 0.85rem;
  color: var(--text-secondary);
  line-height: 1.5;
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
.btn-move:hover:not(:disabled) {
  background: rgba(168, 85, 247, 0.1);
}
.btn-move:disabled {
  opacity: 0.3;
  cursor: not-allowed;
}

.btn-edit {
  color: var(--accent-cyan);
  border-color: rgba(0, 212, 255, 0.3);
}
.btn-edit:hover {
  background: rgba(0, 212, 255, 0.1);
}

.btn-delete {
  color: var(--accent-pink);
  border-color: rgba(255, 45, 149, 0.3);
}
.btn-delete:hover {
  background: rgba(255, 45, 149, 0.1);
}

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
  width: 480px;
  max-width: 90vw;
  padding: 32px;
}

.dialog h3 {
  font-size: 1.2rem;
  margin-bottom: 20px;
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

.level-input {
  display: flex;
  align-items: center;
  gap: 16px;
}

.range-input {
  flex: 1;
  padding: 0 !important;
  border: none !important;
  background: transparent !important;
  accent-color: var(--accent-cyan);
  height: 6px;
}

.level-value {
  font-family: var(--font-mono);
  font-size: 1rem;
  color: var(--accent-cyan);
  font-weight: 700;
  min-width: 44px;
  text-align: right;
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

@media (max-width: 768px) {
  .skill-row {
    flex-wrap: wrap;
    padding: 16px;
  }

  .row-actions {
    width: 100%;
    justify-content: flex-end;
    margin-top: 12px;
  }
}
</style>
