<template>
  <div class="post-list">
    <div class="page-header">
      <h1 class="page-title">文章管理</h1>
      <router-link to="/admin/post/new" class="btn btn-primary">
        ✏️ 写新文章
      </router-link>
    </div>

    <!-- Filters -->
    <div class="filters">
      <div class="filter-tabs">
        <button
          v-for="tab in tabs"
          :key="tab.value"
          class="filter-tab"
          :class="{ active: activeTab === tab.value }"
          @click="activeTab = tab.value"
        >
          {{ tab.label }}
          <span class="tab-count">{{ tab.count }}</span>
        </button>
      </div>

      <input
        v-model="searchQuery"
        type="text"
        placeholder="搜索文章标题..."
        class="search-input"
      />
    </div>

    <!-- Table -->
    <div class="table-wrapper glass-card">
      <table class="data-table">
        <thead>
          <tr>
            <th>标题</th>
            <th>分类</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in filteredPosts" :key="post.id">
            <td class="cell-title">
              <router-link :to="`/admin/post/edit/${post.id}`">{{ post.title }}</router-link>
            </td>
            <td><span class="tag">{{ post.category }}</span></td>
            <td>
              <span class="status-badge" :class="post.published ? 'published' : 'draft'">
                {{ post.published ? '已发布' : '草稿' }}
              </span>
            </td>
            <td class="cell-date">{{ formatDate(post.createdAt) }}</td>
            <td class="cell-actions">
              <button class="action-btn edit" @click="editPost(post.id!)">编辑</button>
              <button
                class="action-btn toggle"
                @click="togglePublish(post)"
              >
                {{ post.published ? '下架' : '发布' }}
              </button>
              <button class="action-btn delete" @click="confirmDelete(post)">删除</button>
            </td>
          </tr>
          <tr v-if="filteredPosts.length === 0">
            <td colspan="5" class="empty-cell">暂无文章</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Delete Confirm Modal -->
    <Teleport to="body">
      <div v-if="showDeleteModal" class="modal-overlay" @click.self="showDeleteModal = false">
        <div class="modal glass-card">
          <h3>确认删除</h3>
          <p>确定要删除「{{ deleteTarget?.title }}」吗？此操作不可撤销。</p>
          <div class="modal-actions">
            <button class="btn btn-outline" @click="showDeleteModal = false">取消</button>
            <button class="btn btn-danger" @click="doDelete">确认删除</button>
          </div>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { adminGetAllPosts, adminTogglePublish, adminDeletePost, type BlogPost } from '@/api/blog'

const router = useRouter()
const posts = ref<BlogPost[]>([])
const activeTab = ref('all')
const searchQuery = ref('')
const showDeleteModal = ref(false)
const deleteTarget = ref<BlogPost | null>(null)

const tabs = computed(() => {
  const total = posts.value.length
  const published = posts.value.filter(p => p.published).length
  return [
    { label: '全部', value: 'all', count: total },
    { label: '已发布', value: 'published', count: published },
    { label: '草稿', value: 'draft', count: total - published },
  ]
})

const filteredPosts = computed(() => {
  let list = posts.value
  if (activeTab.value === 'published') list = list.filter(p => p.published)
  if (activeTab.value === 'draft') list = list.filter(p => !p.published)
  if (searchQuery.value) {
    const q = searchQuery.value.toLowerCase()
    list = list.filter(p => p.title.toLowerCase().includes(q))
  }
  return list
})

function formatDate(dateStr?: string) {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleDateString('zh-CN', {
    year: 'numeric', month: '2-digit', day: '2-digit',
  })
}

function editPost(id: number) {
  router.push(`/admin/post/edit/${id}`)
}

async function togglePublish(post: BlogPost) {
  try {
    const res: any = await adminTogglePublish(post.id!)
    if (res.code === 200) {
      post.published = res.data.published
    }
  } catch (_) { /* handled */ }
}

function confirmDelete(post: BlogPost) {
  deleteTarget.value = post
  showDeleteModal.value = true
}

async function doDelete() {
  if (!deleteTarget.value?.id) return
  try {
    const res: any = await adminDeletePost(deleteTarget.value.id)
    if (res.code === 200) {
      posts.value = posts.value.filter(p => p.id !== deleteTarget.value!.id)
    }
  } catch (_) { /* handled */ }
  showDeleteModal.value = false
  deleteTarget.value = null
}

async function loadPosts() {
  try {
    const res: any = await adminGetAllPosts()
    if (res.code === 200) {
      posts.value = res.data
    }
  } catch (_) { /* handled */ }
}

onMounted(loadPosts)
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
}

.page-title {
  font-size: 1.8rem;
  font-weight: 800;
  background: var(--gradient-main);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.filters {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 16px;
}

.filter-tabs {
  display: flex;
  gap: 4px;
  background: var(--bg-glass);
  padding: 4px;
  border-radius: var(--radius-sm);
  border: 1px solid var(--border-glass);
}

.filter-tab {
  padding: 8px 16px;
  background: none;
  border: none;
  color: var(--text-secondary);
  font-size: 0.85rem;
  cursor: pointer;
  border-radius: 6px;
  transition: all 0.2s;
  font-family: var(--font-body);
  display: flex;
  align-items: center;
  gap: 6px;
}

.filter-tab.active {
  background: rgba(0, 212, 255, 0.1);
  color: var(--accent-cyan);
}

.tab-count {
  font-family: var(--font-mono);
  font-size: 0.75rem;
  opacity: 0.7;
}

.search-input {
  padding: 8px 16px;
  background: var(--bg-glass);
  border: 1px solid var(--border-glass);
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  font-size: 0.88rem;
  outline: none;
  width: 240px;
  font-family: var(--font-body);
  transition: border-color 0.3s;
}

.search-input:focus {
  border-color: var(--accent-cyan);
}

.search-input::placeholder {
  color: var(--text-muted);
}

/* Table */
.table-wrapper {
  overflow-x: auto;
  padding: 0;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table th {
  text-align: left;
  padding: 14px 20px;
  font-size: 0.8rem;
  font-weight: 600;
  color: var(--text-muted);
  text-transform: uppercase;
  letter-spacing: 0.5px;
  border-bottom: 1px solid var(--border-glass);
  white-space: nowrap;
}

.data-table td {
  padding: 16px 20px;
  font-size: 0.9rem;
  border-bottom: 1px solid var(--border-glass);
  vertical-align: middle;
}

.data-table tr:last-child td {
  border-bottom: none;
}

.data-table tr:hover td {
  background: rgba(0, 212, 255, 0.02);
}

.cell-title a {
  color: var(--text-primary);
  font-weight: 600;
  text-decoration: none;
}

.cell-title a:hover {
  color: var(--accent-cyan);
}

.cell-date {
  font-family: var(--font-mono);
  font-size: 0.8rem;
  color: var(--text-muted);
  white-space: nowrap;
}

.cell-actions {
  display: flex;
  gap: 8px;
  white-space: nowrap;
}

.action-btn {
  padding: 4px 12px;
  border: 1px solid var(--border-glass);
  border-radius: 6px;
  background: transparent;
  font-size: 0.78rem;
  cursor: pointer;
  transition: all 0.2s;
  font-family: var(--font-body);
}

.action-btn.edit {
  color: var(--accent-cyan);
  border-color: rgba(0, 212, 255, 0.2);
}
.action-btn.edit:hover {
  background: rgba(0, 212, 255, 0.08);
}

.action-btn.toggle {
  color: var(--accent-green);
  border-color: rgba(0, 228, 143, 0.2);
}
.action-btn.toggle:hover {
  background: rgba(0, 228, 143, 0.08);
}

.action-btn.delete {
  color: var(--accent-pink);
  border-color: rgba(255, 45, 149, 0.2);
}
.action-btn.delete:hover {
  background: rgba(255, 45, 149, 0.08);
}

.status-badge {
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}

.status-badge.published {
  background: rgba(0, 228, 143, 0.1);
  color: var(--accent-green);
}

.status-badge.draft {
  background: rgba(255, 255, 255, 0.03);
  color: var(--text-muted);
}

.empty-cell {
  text-align: center;
  color: var(--text-muted);
  padding: 48px 20px !important;
}

/* Modal */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  backdrop-filter: blur(4px);
}

.modal {
  padding: 32px;
  max-width: 420px;
  width: 90%;
  border: 1px solid rgba(255, 45, 149, 0.2);
}

.modal h3 {
  font-size: 1.2rem;
  margin-bottom: 12px;
}

.modal p {
  color: var(--text-secondary);
  font-size: 0.9rem;
  margin-bottom: 28px;
  line-height: 1.6;
}

.modal-actions {
  display: flex;
  gap: 12px;
  justify-content: flex-end;
}

.btn-danger {
  background: var(--accent-pink);
  color: #fff;
  border: none;
  padding: 10px 24px;
  border-radius: var(--radius-sm);
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  font-family: var(--font-body);
}

.btn-danger:hover {
  opacity: 0.9;
}

@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .filters {
    flex-direction: column;
    align-items: flex-start;
  }

  .search-input {
    width: 100%;
  }
}
</style>
