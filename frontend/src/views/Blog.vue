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
import { ref, computed } from 'vue'
import ScrollReveal from '@/components/ScrollReveal.vue'

const activeCategory = ref('all')

const categories = [
  { label: '全部', value: 'all' },
  { label: '前端', value: '前端' },
  { label: '后端', value: '后端' },
  { label: '架构', value: '架构' },
  { label: '团队', value: '团队' },
]

const posts = [
  {
    id: '1',
    title: 'Vue 2 到 Vue 3 迁移实战：渐进式策略与踩坑记录',
    excerpt: '分享在生产项目中从 Vue 2 逐步迁移到 Vue 3 的完整策略，包括 Composition API 改造、状态管理切换、以及如何在不停业务的前提下平稳过渡。',
    date: '2025-12-15',
    category: '前端',
    tags: ['Vue 3', 'Migration', 'Composition API'],
    readTime: 12,
  },
  {
    id: '2',
    title: '企业级前端架构设计：从项目结构到团队协作',
    excerpt: '基于轮胎PLM系统的实战经验，总结大型B端前端项目的架构设计原则：模块划分、状态管理、组件抽象、以及工程化规范。',
    date: '2025-10-20',
    category: '架构',
    tags: ['Architecture', 'Engineering', 'Vue'],
    readTime: 15,
  },
  {
    id: '3',
    title: 'Spring Boot RESTful API 设计中的实践与反思',
    excerpt: '在参与后端开发过程中积累的 API 设计经验：统一响应格式、异常处理、接口版本管理与前后端协作约定。',
    date: '2025-08-10',
    category: '后端',
    tags: ['Spring Boot', 'REST', 'Java'],
    readTime: 10,
  },
  {
    id: '4',
    title: '从开发者到IT主管：技术管理的第一年',
    excerpt: '记录从纯技术角色转型为10人团队管理者的心路历程，包括如何平衡技术与管理、建立团队规范、以及跨部门沟通的技巧。',
    date: '2025-06-05',
    category: '团队',
    tags: ['Management', 'Leadership', 'Growth'],
    readTime: 8,
  },
  {
    id: '5',
    title: '复杂表单系统的设计思路：配置化 vs 组件化',
    excerpt: '在PLM系统中面对上百个业务表单时，如何选择合适的抽象层级？对比配置化驱动和组件化方案的优劣。',
    date: '2025-04-18',
    category: '前端',
    tags: ['Form Design', 'Vue', 'Architecture'],
    readTime: 11,
  },
  {
    id: '6',
    title: '前端性能优化实战：首屏加载从 5s 到 1.5s',
    excerpt: '在企业内网环境下，通过路由懒加载、组件按需引入、打包分析与CDN策略，将首屏加载时间压缩 70%。',
    date: '2025-02-22',
    category: '前端',
    tags: ['Performance', 'Vite', 'Optimization'],
    readTime: 9,
  },
]

const filteredPosts = computed(() => {
  if (activeCategory.value === 'all') return posts
  return posts.filter(p => p.category === activeCategory.value)
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
