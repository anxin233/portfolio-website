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

    <div v-else class="container not-found">
      <h2>文章未找到</h2>
      <router-link to="/blog" class="btn btn-primary">返回博客</router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const postsData: Record<string, any> = {
  '1': {
    title: 'Vue 2 到 Vue 3 迁移实战：渐进式策略与踩坑记录',
    date: '2025-12-15',
    category: '前端',
    tags: ['Vue 3', 'Migration', 'Composition API'],
    readTime: 12,
    content: `
## 背景

在我们的轮胎PLM系统中，前端最初基于Vue 2 + Vuex构建。随着Vue 3的成熟和Composition API带来的代码组织优势，
我们决定进行渐进式迁移。但在一个正在运行的生产系统上做技术迁移，最大的挑战是：**不能停业务**。

## 迁移策略

我们没有选择一次性重写，而是制定了三阶段渐进策略：

### 阶段一：基础设施升级
- 将Vue版本升级到2.7（支持Composition API）
- 引入\`<script setup>\`语法，新模块优先使用
- 保持Vuex不变，渐进引入Pinia

### 阶段二：新模块全面Vue 3
- 新业务模块使用Vue 3 + Composition API
- 公共组件层做兼容封装
- 建立新的代码规范文档

### 阶段三：老模块逐步替换
- 按业务优先级排列迁移清单
- 每个迭代安排20%的技术改造工时
- 添加自动化测试覆盖改造模块

## 踩坑记录

### 1. 响应式系统差异
Vue 3的Proxy-based响应式在某些边界情况下行为不同...

### 2. 第三方库兼容性
Element UI到Element Plus的迁移不仅是API变化...

### 3. Vuex到Pinia的状态迁移
需要注意模块化store的映射关系...

## 总结

渐进式迁移的核心是**可控**和**可回滚**。每一步都应该是可以独立上线、独立回退的。
    `,
  },
  '2': {
    title: '企业级前端架构设计：从项目结构到团队协作',
    date: '2025-10-20',
    category: '架构',
    tags: ['Architecture', 'Engineering', 'Vue'],
    readTime: 15,
    content: `
## 为什么需要前端架构

当项目超过10个业务模块、开发人员超过3人时，没有架构约束的前端代码会迅速劣化。
在PLM系统中，我从零搭建了一套适合团队协作的前端架构。

## 目录结构设计

一个好的目录结构应该让人「看目录就知道这个项目做什么」：

\`\`\`
src/
├── modules/          # 按业务域划分
│   ├── product/      # 产品管理
│   ├── change/       # 变更管理
│   └── workflow/     # 流程引擎
├── shared/           # 跨模块共享
│   ├── components/   # 通用组件
│   ├── composables/  # 通用hooks
│   └── utils/        # 工具函数
├── layout/           # 布局组件
└── router/           # 路由配置
\`\`\`

## 组件设计原则

### 三层组件体系
1. **基础组件**：对UI库的二次封装，统一API
2. **业务组件**：特定领域的复合组件
3. **页面组件**：路由对应的容器组件

### 状态管理策略
- 全局状态：用户信息、权限、系统配置
- 模块状态：当前模块的业务数据
- 组件状态：UI交互状态

## 工程化规范

代码规范不是写文档就行，需要通过工具强制执行...

## 总结

前端架构不是一次性的工作，它需要随着业务演进持续调整。
关键是建立**约定**和**自动化**，让正确的做法成为最省力的做法。
    `,
  },
}

const post = computed(() => {
  const id = route.params.id as string
  return postsData[id] || null
})

const renderedContent = computed(() => {
  if (!post.value) return ''
  return post.value.content
    .replace(/## (.*)/g, '<h2>$1</h2>')
    .replace(/### (.*)/g, '<h3>$1</h3>')
    .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
    .replace(/`([^`]+)`/g, '<code>$1</code>')
    .replace(/```([\s\S]*?)```/g, '<pre><code>$1</code></pre>')
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
