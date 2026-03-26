<template>
  <div class="blog-detail-page">
    <section class="section page-hero">
      <div class="container">
        <router-link to="/blog" class="back-link">
          <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2"><path d="M19 12H5M12 19l-7-7 7-7"/></svg>
          返回博客列表
        </router-link>
      </div>
    </section>

    <article class="section article-section" v-if="post">
      <div class="container article-container">
        <header class="article-header">
          <div class="article-meta">
            <span class="blog-category">{{ post.category }}</span>
            <span class="article-date">{{ post.date }}</span>
            <span class="article-read-time">{{ post.readTime }} min read</span>
          </div>
          <h1 class="article-title">{{ post.title }}</h1>
          <div class="article-tags">
            <span class="tag" v-for="t in post.tags" :key="t">{{ t }}</span>
          </div>
        </header>

        <div class="article-body" v-html="renderedContent"></div>

        <footer class="article-footer">
          <div class="article-nav">
            <router-link to="/blog" class="btn btn-outline">
              <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2"><path d="M19 12H5M12 19l-7-7 7-7"/></svg>
              返回列表
            </router-link>
          </div>
        </footer>
      </div>
    </article>

    <div v-else-if="loading" class="container loading-state">
      <p>加载中...</p>
    </div>

    <div v-else class="container not-found">
      <h2>文章未找到</h2>
      <router-link to="/blog" class="btn btn-primary">返回博客</router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getPostDetail, type BlogPost } from '@/api/blog'

const route = useRoute()
const post = ref<any>(null)
const loading = ref(true)

onMounted(async () => {
  const id = Number(route.params.id)
  if (!id) return
  try {
    const res: any = await getPostDetail(id)
    if (res.code === 200 && res.data) {
      const p = res.data as BlogPost
      post.value = {
        title: p.title,
        category: p.category,
        tags: p.tags ? p.tags.split(',').map((t: string) => t.trim()) : [],
        readTime: p.readTime,
        date: p.createdAt ? p.createdAt.substring(0, 10) : '',
        content: p.content || '',
      }
    }
  } catch (e) {
    console.error('Failed to load post', e)
  } finally {
    loading.value = false
  }
})

const renderedContent = computed(() => {
  if (!post.value) return ''
  return post.value.content
    .replace(/### (.*)/g, '<h3>$1</h3>')
    .replace(/## (.*)/g, '<h2>$1</h2>')
    .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
    .replace(/```([\s\S]*?)```/g, '<pre><code>$1</code></pre>')
    .replace(/`([^`]+)`/g, '<code>$1</code>')
    .replace(/\n- (.*)/g, '\n<li>$1</li>')
    .replace(/(<li>.*<\/li>\n?)+/g, '<ul>$&</ul>')
    .replace(/\n\n/g, '</p><p>')
    .replace(/^/, '<p>')
    .replace(/$/, '</p>')
})
</script>

<style scoped>
.page-hero {
  padding-top: calc(var(--header-height) + 30px);
  padding-bottom: 0;
}

.back-link {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: var(--text-secondary);
  font-size: 0.9rem;
  transition: color 0.3s;
}

.back-link:hover {
  color: var(--accent-cyan);
}

.article-container {
  max-width: 800px;
}

.article-header {
  margin-bottom: 48px;
  padding-bottom: 32px;
  border-bottom: 1px solid var(--border-glass);
}

.article-meta {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 20px;
}

.blog-category {
  font-family: var(--font-mono);
  font-size: 0.8rem;
  color: var(--accent-purple);
  padding: 2px 10px;
  background: rgba(123, 47, 247, 0.1);
  border-radius: 4px;
}

.article-date,
.article-read-time {
  font-size: 0.8rem;
  color: var(--text-muted);
  font-family: var(--font-mono);
}

.article-title {
  font-size: 2.2rem;
  font-weight: 800;
  line-height: 1.3;
  margin-bottom: 20px;
}

.article-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

/* Article Body */
.article-body {
  font-size: 1.05rem;
  line-height: 1.9;
  color: var(--text-secondary);
}

.article-body :deep(h2) {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 48px 0 16px;
  padding-bottom: 8px;
  border-bottom: 1px solid var(--border-glass);
}

.article-body :deep(h3) {
  font-size: 1.2rem;
  font-weight: 600;
  color: var(--text-primary);
  margin: 32px 0 12px;
}

.article-body :deep(p) {
  margin-bottom: 16px;
}

.article-body :deep(strong) {
  color: var(--accent-cyan);
}

.article-body :deep(code) {
  font-family: var(--font-mono);
  font-size: 0.9em;
  padding: 2px 8px;
  background: rgba(0, 212, 255, 0.08);
  border-radius: 4px;
  color: var(--accent-cyan);
}

.article-body :deep(pre) {
  background: var(--bg-secondary);
  border: 1px solid var(--border-glass);
  border-radius: var(--radius-sm);
  padding: 20px;
  margin: 24px 0;
  overflow-x: auto;
}

.article-body :deep(pre code) {
  background: none;
  padding: 0;
  font-size: 0.85rem;
  line-height: 1.6;
}

.article-body :deep(ul) {
  padding-left: 24px;
  margin-bottom: 16px;
}

.article-body :deep(li) {
  margin-bottom: 8px;
}

.article-footer {
  margin-top: 60px;
  padding-top: 32px;
  border-top: 1px solid var(--border-glass);
}

.loading-state {
  text-align: center;
  padding: 120px 0;
  color: var(--text-muted);
  font-size: 1.1rem;
}

.not-found {
  text-align: center;
  padding: 120px 0;
}

.not-found h2 {
  font-size: 1.5rem;
  margin-bottom: 24px;
  color: var(--text-muted);
}

@media (max-width: 768px) {
  .article-title {
    font-size: 1.6rem;
  }
}
</style>
