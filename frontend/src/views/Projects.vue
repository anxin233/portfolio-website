<template>
  <div class="projects-page">
    <section class="section page-hero">
      <div class="container">
        <ScrollReveal>
          <h1 class="section-title">项目经历</h1>
          <p class="section-subtitle">每个项目都经历了从需求分析到生产交付的完整过程</p>
        </ScrollReveal>
      </div>
    </section>

    <!-- Featured -->
    <section class="section">
      <div class="container">
        <ScrollReveal>
          <div class="featured-project glass-card">
            <div class="featured-badge">⭐ 代表作品</div>
            <h2 class="featured-title">轮胎企业产品生命周期管理系统（PLM）</h2>
            <p class="featured-subtitle">前端架构主导 · 全栈参与 · IT部门主管负责</p>

            <div class="featured-grid">
              <div class="featured-desc">
                <h3>项目背景</h3>
                <p>
                  面向轮胎制造企业的产品生命周期管理系统，支撑从产品需求、设计、变更、
                  版本控制到跨部门协同审批等核心业务流程。系统用户覆盖研发、工艺、质量、
                  生产等多个部门，业务流程复杂、数据维度多。
                </p>

                <h3>我的职责</h3>
                <ul>
                  <li><strong>前端架构搭建</strong>：从零构建整体前端工程，包括技术选型、目录规范、
                    组件体系、状态管理方案与构建流程</li>
                  <li><strong>前端核心开发</strong>：复杂表单系统、多级审批流程、数据展示与交互</li>
                  <li><strong>后端业务开发</strong>：参与核心模块API设计与实现</li>
                  <li><strong>需求对接</strong>：直接与业务部门沟通，将业务语言转化为技术方案</li>
                  <li><strong>团队管理</strong>：负责开发任务分配、代码评审与技术指导</li>
                </ul>
              </div>

              <div class="featured-meta">
                <div class="meta-block">
                  <h4>前端技术栈</h4>
                  <div class="meta-tags">
                    <span class="tag" v-for="t in plmFrontend" :key="t">{{ t }}</span>
                  </div>
                </div>
                <div class="meta-block">
                  <h4>后端技术栈</h4>
                  <div class="meta-tags">
                    <span class="tag" v-for="t in plmBackend" :key="t">{{ t }}</span>
                  </div>
                </div>
                <div class="meta-block">
                  <h4>项目亮点</h4>
                  <div class="highlights-list">
                    <div class="highlight" v-for="h in plmHighlights" :key="h">
                      <span class="highlight-check">✓</span>
                      {{ h }}
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="challenges">
              <h3>技术难点与方案</h3>
              <div class="challenge-grid">
                <div class="challenge-card" v-for="c in challenges" :key="c.title">
                  <h4>{{ c.title }}</h4>
                  <p class="challenge-problem"><strong>问题：</strong>{{ c.problem }}</p>
                  <p class="challenge-solution"><strong>方案：</strong>{{ c.solution }}</p>
                  <p class="challenge-result" v-if="c.result"><strong>效果：</strong>{{ c.result }}</p>
                </div>
              </div>
            </div>
          </div>
        </ScrollReveal>
      </div>
    </section>

    <!-- Other Projects -->
    <section class="section">
      <div class="container">
        <ScrollReveal>
          <h2 class="section-title">更多项目</h2>
          <p class="section-subtitle">持续积累的技术实践</p>
        </ScrollReveal>

        <div class="projects-grid">
          <ScrollReveal v-for="(p, i) in otherProjects" :key="p.title" :delay="i * 100">
            <div class="project-card glass-card gradient-border">
              <div class="project-card-header">
                <div class="project-icon">{{ p.icon }}</div>
                <div class="project-links">
                  <a v-if="p.github" :href="p.github" target="_blank" class="project-link">GitHub</a>
                  <a v-if="p.demo" :href="p.demo" target="_blank" class="project-link">Demo</a>
                </div>
              </div>
              <h3 class="project-name">{{ p.title }}</h3>
              <p class="project-desc">{{ p.desc }}</p>
              <div class="project-tags">
                <span class="tag" v-for="t in p.techs" :key="t">{{ t }}</span>
              </div>
            </div>
          </ScrollReveal>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import ScrollReveal from '@/components/ScrollReveal.vue'

const plmFrontend = ['Vue 2/3', 'TypeScript', 'Vuex/Pinia', 'Vue Router', 'Element UI/Plus', 'Webpack/Vite', 'SCSS', 'Axios']
const plmBackend = ['Java', 'Spring Boot', 'Spring MVC', 'MyBatis', 'MySQL', 'Redis', 'Maven']

const plmHighlights = [
  '前端整体架构从零搭建',
  '支撑多部门协同的复杂业务流程',
  '直接与业务人员沟通需求并落地',
  '带领团队完成开发与持续迭代',
  '系统稳定运行并持续维护优化',
]

const challenges = [
  {
    title: '复杂表单与多步骤流程',
    problem: 'PLM系统涉及大量多层级表单，且与审批流程强耦合，状态管理复杂。',
    solution: '设计统一的表单配置化体系与状态机模型，将表单校验、步骤控制、与后端流程状态对齐，减少硬编码。',
    result: '新业务表单开发效率提升，状态一致性问题显著减少',
  },
  {
    title: '前端架构的可扩展性',
    problem: '业务模块持续增加，需要保证新模块接入成本低且不影响已有功能。',
    solution: '采用模块化目录结构，抽取公共组件库与工具层，制定代码规范与路由约定，新人可快速上手。',
    result: '新模块接入周期可控，团队协作效率明显提升',
  },
  {
    title: 'Vue 2 到 Vue 3 的渐进迁移',
    problem: '在业务不停的前提下，需要逐步将技术栈从Vue 2迁移到Vue 3。',
    solution: '采用渐进式策略，新模块使用Vue 3 + Composition API，核心公共层做兼容封装，逐步替换老模块。',
    result: '平稳过渡，没有出现业务中断',
  },
]

const otherProjects = [
  {
    icon: '📊',
    title: '企业数据看板系统',
    desc: '面向管理层的经营数据可视化平台，支持多维度数据钻取与实时刷新。',
    techs: ['Vue 3', 'ECharts', 'Spring Boot', 'MySQL'],
    github: '',
    demo: '',
  },
  {
    icon: '🔧',
    title: '内部工具平台',
    desc: '统一的内部效率工具集，包括代码生成器、接口文档管理、部署辅助等。',
    techs: ['Vue 3', 'Node.js', 'Shell'],
    github: '',
    demo: '',
  },
  {
    icon: '📱',
    title: '移动端H5应用',
    desc: '面向一线操作人员的移动端数据采集与查询应用。',
    techs: ['Vue 3', 'Vant', 'Spring Boot'],
    github: '',
    demo: '',
  },
  {
    icon: '🧩',
    title: '前端组件库',
    desc: '团队内部共享的业务组件库，统一交互规范，提升开发效率。',
    techs: ['Vue 3', 'TypeScript', 'Storybook'],
    github: '',
    demo: '',
  },
]
</script>

<style scoped>
.page-hero {
  padding-top: calc(var(--header-height) + 60px);
  padding-bottom: 40px;
}

/* Featured */
.featured-project {
  padding: 48px;
  border: 1px solid rgba(123, 47, 247, 0.2);
}

.featured-badge {
  display: inline-block;
  padding: 6px 16px;
  background: rgba(123, 47, 247, 0.1);
  border: 1px solid rgba(123, 47, 247, 0.3);
  border-radius: 20px;
  font-size: 0.85rem;
  color: var(--accent-purple);
  margin-bottom: 20px;
}

.featured-title {
  font-size: 2rem;
  font-weight: 800;
  margin-bottom: 8px;
}

.featured-subtitle {
  font-family: var(--font-mono);
  color: var(--accent-cyan);
  font-size: 0.9rem;
  margin-bottom: 40px;
}

.featured-grid {
  display: grid;
  grid-template-columns: 1.2fr 1fr;
  gap: 40px;
  margin-bottom: 40px;
}

.featured-desc h3 {
  font-size: 1.1rem;
  margin-bottom: 12px;
  margin-top: 24px;
  color: var(--text-primary);
}

.featured-desc h3:first-child {
  margin-top: 0;
}

.featured-desc p {
  color: var(--text-secondary);
  line-height: 1.8;
  font-size: 0.95rem;
}

.featured-desc ul {
  list-style: none;
  padding: 0;
}

.featured-desc li {
  position: relative;
  padding-left: 20px;
  margin-bottom: 10px;
  color: var(--text-secondary);
  font-size: 0.9rem;
  line-height: 1.6;
}

.featured-desc li::before {
  content: '▸';
  position: absolute;
  left: 0;
  color: var(--accent-cyan);
}

.featured-desc li strong {
  color: var(--text-primary);
}

.meta-block {
  margin-bottom: 24px;
}

.meta-block h4 {
  font-size: 0.85rem;
  font-family: var(--font-mono);
  color: var(--text-muted);
  margin-bottom: 10px;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.meta-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.highlights-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.highlight {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 0.88rem;
  color: var(--text-secondary);
}

.highlight-check {
  color: var(--accent-green);
  font-weight: bold;
}

/* Challenges */
.challenges h3 {
  font-size: 1.2rem;
  margin-bottom: 24px;
  padding-top: 32px;
  border-top: 1px solid var(--border-glass);
}

.challenge-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.challenge-card {
  padding: 24px;
  background: var(--bg-glass);
  border-radius: var(--radius-sm);
  border: 1px solid var(--border-glass);
}

.challenge-card h4 {
  font-size: 1rem;
  margin-bottom: 14px;
  color: var(--accent-cyan);
}

.challenge-card p {
  font-size: 0.88rem;
  color: var(--text-secondary);
  line-height: 1.7;
  margin-bottom: 8px;
}

.challenge-card strong {
  color: var(--text-primary);
}

.challenge-result {
  color: var(--accent-green) !important;
}

/* Other Projects Grid */
.projects-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
}

.project-card {
  padding: 32px;
  display: flex;
  flex-direction: column;
  min-height: 240px;
}

.project-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.project-icon {
  font-size: 2rem;
}

.project-links {
  display: flex;
  gap: 12px;
}

.project-link {
  font-size: 0.8rem;
  font-family: var(--font-mono);
  color: var(--text-muted);
  transition: color 0.3s;
}

.project-link:hover {
  color: var(--accent-cyan);
}

.project-name {
  font-size: 1.15rem;
  margin-bottom: 10px;
}

.project-desc {
  font-size: 0.88rem;
  color: var(--text-secondary);
  line-height: 1.6;
  flex: 1;
  margin-bottom: 16px;
}

.project-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

@media (max-width: 768px) {
  .featured-project {
    padding: 24px;
  }

  .featured-grid {
    grid-template-columns: 1fr;
  }

  .featured-title {
    font-size: 1.5rem;
  }
}
</style>
