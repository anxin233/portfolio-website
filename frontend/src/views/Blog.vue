<template>
  <div class="blog-page">
    <section class="section page-hero">
      <div class="container">
        <ScrollReveal>
          <h1 class="section-title">技术博客</h1>
          <p class="section-subtitle">记录思考过程，分享实践经验</p>
        </ScrollReveal>
      </div>
    </section>

    <section class="section">
      <div class="container">
        <!-- Category Filter -->
        <ScrollReveal>
          <div class="blog-filter">
            <button
              v-for="cat in categories"
              :key="cat.value"
              class="filter-btn"
              :class="{ active: activeCategory === cat.value }"
              @click="activeCategory = cat.value"
            >
              {{ cat.label }}
            </button>
          </div>
        </ScrollReveal>

        <!-- Blog List -->
        <div class="blog-list">
          <ScrollReveal v-for="(post, i) in filteredPosts" :key="post.id" :delay="i * 100">
            <router-link :to="`/blog/${post.id}`" class="blog-card glass-card gradient-border">
              <div class="blog-card-content">
                <div class="blog-meta">
                  <span class="blog-category">{{ post.category }}</span>
                  <span class="blog-date">{{ post.date }}</span>
                </div>
                <h2 class="blog-title">{{ post.title }}</h2>
                <p class="blog-excerpt">{{ post.excerpt }}</p>
                <div class="blog-footer">
                  <div class="blog-tags">
                    <span class="tag" v-for="t in post.tags" :key="t">{{ t }}</span>
                  </div>
                  <span class="blog-read-time">{{ post.readTime }} min</span>
                </div>
              </div>
              <div class="blog-arrow">→</div>
            </router-link>
          </ScrollReveal>
        </div>

        <div v-if="filteredPosts.length === 0" class="blog-empty">
          <p>该分类暂无文章，敬请期待 ✍️</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import ScrollReveal from '@/components/ScrollReveal.vue'
import { getPublishedPosts, getCategories, type BlogPost, type Category } from '@/api/blog'

const activeCategory = ref('all')
const posts = ref<any[]>([])
const loading = ref(true)
const categories = ref<{ label: string; value: string }[]>([{ label: '全部', value: 'all' }])

onMounted(async () => {
  try {
    const [postsRes, catsRes]: any[] = await Promise.all([getPublishedPosts(), getCategories()])
    if (postsRes.code === 200 && postsRes.data) {
      posts.value = postsRes.data.map((p: BlogPost) => ({
        id: p.id,
        title: p.title,
        excerpt: p.excerpt,
        category: p.category,
        tags: p.tags ? p.tags.split(',').map((t: string) => t.trim()) : [],
        readTime: p.readTime,
        date: p.createdAt ? p.createdAt.substring(0, 10) : '',
      }))
    }
    if (catsRes.code === 200 && catsRes.data) {
      categories.value = [
        { label: '全部', value: 'all' },
        ...catsRes.data.map((c: Category) => ({ label: c.name, value: c.name })),
      ]
    }
  } catch (e) {
    console.error('Failed to load data', e)
  } finally {
    loading.value = false
  }
})

const filteredPosts = computed(() => {
  if (activeCategory.value === 'all') return posts.value
  return posts.value.filter(p => p.category === activeCategory.value)
})
</script>

<style scoped>
.page-hero {
  padding-top: calc(var(--header-height) + 60px);
  padding-bottom: 40px;
}

.blog-filter {
  display: flex;
  gap: 8px;
  margin-bottom: 48px;
  flex-wrap: wrap;
}

.filter-btn {
  padding: 8px 20px;
  border: 1px solid var(--border-glass);
  border-radius: 20px;
  background: transparent;
  color: var(--text-secondary);
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.3s ease;
  font-family: var(--font-body);
}

.filter-btn:hover {
  border-color: var(--accent-cyan);
  color: var(--accent-cyan);
}

.filter-btn.active {
  background: rgba(0, 212, 255, 0.1);
  border-color: var(--accent-cyan);
  color: var(--accent-cyan);
}

.blog-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.blog-card {
  display: flex;
  align-items: center;
  padding: 32px;
  text-decoration: none;
  color: inherit;
  cursor: pointer;
}

.blog-card-content {
  flex: 1;
}

.blog-meta {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 12px;
}

.blog-category {
  font-family: var(--font-mono);
  font-size: 0.8rem;
  color: var(--accent-purple);
  padding: 2px 10px;
  background: rgba(123, 47, 247, 0.1);
  border-radius: 4px;
}

.blog-date {
  font-size: 0.8rem;
  color: var(--text-muted);
  font-family: var(--font-mono);
}

.blog-title {
  font-size: 1.3rem;
  font-weight: 700;
  margin-bottom: 10px;
  transition: color 0.3s;
}

.blog-card:hover .blog-title {
  color: var(--accent-cyan);
}

.blog-excerpt {
  font-size: 0.92rem;
  color: var(--text-secondary);
  line-height: 1.7;
  margin-bottom: 16px;
}

.blog-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.blog-tags {
  display: flex;
  gap: 6px;
  flex-wrap: wrap;
}

.blog-read-time {
  font-size: 0.8rem;
  color: var(--text-muted);
  font-family: var(--font-mono);
  white-space: nowrap;
}

.blog-arrow {
  font-size: 1.5rem;
  color: var(--text-muted);
  margin-left: 24px;
  transition: all 0.3s;
  flex-shrink: 0;
}

.blog-card:hover .blog-arrow {
  color: var(--accent-cyan);
  transform: translateX(6px);
}

.blog-empty {
  text-align: center;
  padding: 80px 0;
  color: var(--text-muted);
  font-size: 1.1rem;
}

@media (max-width: 768px) {
  .blog-card {
    padding: 20px;
    flex-direction: column;
    align-items: flex-start;
  }

  .blog-arrow {
    display: none;
  }

  .blog-title {
    font-size: 1.1rem;
  }
}
</style>
