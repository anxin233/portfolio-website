<template>
  <div class="dashboard">
    <h1 class="page-title">仪表盘</h1>

    <div class="stats-grid">
      <div class="stat-card glass-card" v-for="stat in stats" :key="stat.label">
        <div class="stat-icon">{{ stat.icon }}</div>
        <div class="stat-info">
          <span class="stat-value">{{ stat.value }}</span>
          <span class="stat-label">{{ stat.label }}</span>
        </div>
      </div>
    </div>

    <div class="recent-section">
      <div class="section-header">
        <h2>最近文章</h2>
        <router-link to="/admin/posts" class="view-all">查看全部 →</router-link>
      </div>

      <div class="recent-list">
        <div
          v-for="post in recentPosts"
          :key="post.id"
          class="recent-item glass-card"
        >
          <div class="recent-info">
            <h3>{{ post.title }}</h3>
            <div class="recent-meta">
              <span class="tag">{{ post.category }}</span>
              <span class="meta-date">{{ formatDate(post.createdAt) }}</span>
            </div>
          </div>
          <span class="status-badge" :class="post.published ? 'published' : 'draft'">
            {{ post.published ? '已发布' : '草稿' }}
          </span>
        </div>

        <div v-if="recentPosts.length === 0" class="empty">
          暂无文章，去写一篇吧
        </div>
      </div>
    </div>

    <div class="quick-actions">
      <h2>快捷操作</h2>
      <div class="actions-grid">
        <router-link to="/admin/post/new" class="action-card glass-card gradient-border">
          <span class="action-icon">✏️</span>
          <span>写新文章</span>
        </router-link>
        <router-link to="/admin/posts" class="action-card glass-card gradient-border">
          <span class="action-icon">📋</span>
          <span>管理文章</span>
        </router-link>
        <router-link to="/" target="_blank" class="action-card glass-card gradient-border">
          <span class="action-icon">🌐</span>
          <span>查看网站</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { adminGetAllPosts, type BlogPost } from '@/api/blog'

const recentPosts = ref<BlogPost[]>([])
const stats = ref([
  { icon: '📝', label: '全部文章', value: 0 },
  { icon: '✅', label: '已发布', value: 0 },
  { icon: '📄', label: '草稿', value: 0 },
  { icon: '📁', label: '分类数', value: 0 },
])

function formatDate(dateStr?: string) {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleDateString('zh-CN')
}

onMounted(async () => {
  try {
    const res: any = await adminGetAllPosts()
    if (res.code === 200) {
      const posts: BlogPost[] = res.data
      recentPosts.value = posts.slice(0, 5)

      const published = posts.filter(p => p.published).length
      const categories = new Set(posts.map(p => p.category)).size
      stats.value = [
        { icon: '📝', label: '全部文章', value: posts.length },
        { icon: '✅', label: '已发布', value: published },
        { icon: '📄', label: '草稿', value: posts.length - published },
        { icon: '📁', label: '分类数', value: categories },
      ]
    }
  } catch (_) { /* handled by interceptor */ }
})
</script>

<style scoped>
.page-title {
  font-size: 1.8rem;
  font-weight: 800;
  margin-bottom: 32px;
  background: var(--gradient-main);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 24px;
}

.stat-icon {
  font-size: 2rem;
}

.stat-value {
  display: block;
  font-size: 2rem;
  font-weight: 800;
  font-family: var(--font-mono);
  color: var(--accent-cyan);
}

.stat-label {
  font-size: 0.85rem;
  color: var(--text-muted);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-header h2 {
  font-size: 1.2rem;
}

.view-all {
  font-size: 0.85rem;
  color: var(--accent-cyan);
}

.recent-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 40px;
}

.recent-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
}

.recent-info h3 {
  font-size: 0.95rem;
  font-weight: 600;
  margin-bottom: 6px;
}

.recent-meta {
  display: flex;
  align-items: center;
  gap: 12px;
}

.meta-date {
  font-size: 0.8rem;
  color: var(--text-muted);
  font-family: var(--font-mono);
}

.status-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
  flex-shrink: 0;
}

.status-badge.published {
  background: rgba(0, 228, 143, 0.1);
  color: var(--accent-green);
  border: 1px solid rgba(0, 228, 143, 0.2);
}

.status-badge.draft {
  background: rgba(255, 255, 255, 0.03);
  color: var(--text-muted);
  border: 1px solid var(--border-glass);
}

.empty {
  text-align: center;
  padding: 40px;
  color: var(--text-muted);
}

.quick-actions h2 {
  font-size: 1.2rem;
  margin-bottom: 20px;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  gap: 16px;
}

.action-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  padding: 28px 20px;
  text-decoration: none;
  color: var(--text-primary);
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  text-align: center;
}

.action-icon {
  font-size: 2rem;
}
</style>
