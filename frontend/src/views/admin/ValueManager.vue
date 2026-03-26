<template>
  <div class="value-manager">
    <div class="page-header">
      <h2>工程理念管理</h2>
      <button class="btn-primary" @click="openCreate">+ 新增理念</button>
    </div>

    <div class="value-list">
      <div v-if="loading" class="loading-text">加载中...</div>
      <div v-else-if="!items.length" class="empty-text">暂无数据</div>
      <div
        v-for="(item, idx) in items"
        :key="item.id"
        class="value-row glass-card"
      >
        <div class="row-icon">{{ item.icon }}</div>
        <div class="row-content">
          <div class="row-title">{{ item.title }}</div>
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
        <h3>{{ editingId ? '编辑理念' : '新增理念' }}</h3>
        <div class="form-group">
          <label>图标（Emoji）<span class="hint">按 Win + . 打开表情面板</span></label>
          <input v-model="form.icon" placeholder="🎯" class="icon-input" />
        </div>
        <div class="form-group">
          <label>标题</label>
          <input v-model="form.title" placeholder="如: 业务驱动技术" />
        </div>
        <div class="form-group">
          <label>描述</label>
          <textarea v-model="form.description" rows="3" placeholder="详细描述这条工程理念"></textarea>
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
  adminGetValues,
  adminCreateValue,
  adminUpdateValue,
  adminDeleteValue,
  type ValueItemData,
} from '@/api/blog'

const items = ref<ValueItemData[]>([])
const loading = ref(true)
const showDialog = ref(false)
const saving = ref(false)
const editingId = ref<number | null>(null)

const form = ref<ValueItemData>({
  icon: '',
  title: '',
  description: '',
  sortOrder: 0,
})

async function fetchData() {
  loading.value = true
  try {
    const res = await adminGetValues()
    items.value = res.data || []
  } catch {
    items.value = []
  } finally {
    loading.value = false
  }
}

function openCreate() {
  editingId.value = null
  form.value = { icon: '', title: '', description: '', sortOrder: items.value.length + 1 }
  showDialog.value = true
}

function openEdit(item: ValueItemData) {
  editingId.value = item.id!
  form.value = { ...item }
  showDialog.value = true
}

async function handleSave() {
  if (!form.value.title) return
  saving.value = true
  try {
    if (editingId.value) {
      await adminUpdateValue(editingId.value, form.value)
    } else {
      await adminCreateValue(form.value)
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
    adminUpdateValue(current.id!, current),
    adminUpdateValue(target.id!, target),
  ])
  await fetchData()
}

async function handleDelete(item: ValueItemData) {
  if (!confirm(`确定删除「${item.title}」？`)) return
  await adminDeleteValue(item.id!)
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

.value-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.loading-text, .empty-text {
  text-align: center;
  color: var(--text-muted);
  padding: 40px 0;
}

.value-row {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 20px 24px;
}

.row-icon {
  font-size: 2rem;
  flex-shrink: 0;
  width: 40px;
  text-align: center;
  margin-top: 2px;
}

.row-content {
  flex: 1;
  min-width: 0;
}

.row-title {
  font-weight: 600;
  font-size: 1rem;
  margin-bottom: 6px;
}

.row-desc {
  font-size: 0.88rem;
  color: var(--text-secondary);
  line-height: 1.6;
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

.hint {
  margin-left: 8px;
  font-size: 0.78rem;
  color: var(--text-muted);
  font-weight: 400;
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

.icon-input {
  max-width: 120px;
  text-align: center;
  font-size: 1.5rem !important;
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
  .value-row {
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
