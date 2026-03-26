<template>
  <div class="home">
    <!-- Hero Section -->
    <section class="hero">
      <ParticleBackground />
      <div class="container hero-content">
        <div class="hero-badge">
          <span class="badge-dot"></span>
          开放合作机会
        </div>
        <h1 class="hero-title">
          全栈工程师<br />
          <TypeWriter :texts="titles" :type-speed="70" :pause-time="2500" />
        </h1>
        <p class="hero-desc">
          2017年至今，专注企业级B端系统。前端架构与工程化主导，深度参与Java后端开发。<br />
          带领10人技术团队，从需求沟通到交付落地的全链路负责人。
        </p>
        <div class="hero-actions">
          <router-link to="/projects" class="btn btn-primary">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7" rx="1"/><rect x="14" y="3" width="7" height="7" rx="1"/><rect x="3" y="14" width="7" height="7" rx="1"/><rect x="14" y="14" width="7" height="7" rx="1"/></svg>
            查看项目
          </router-link>
          <router-link to="/blog" class="btn btn-outline">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 20h9"/><path d="M16.5 3.5a2.121 2.121 0 013 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>
            技术博客
          </router-link>
        </div>

        <div class="hero-stats">
          <div class="stat" v-for="stat in stats" :key="stat.label">
            <span class="stat-number">{{ stat.value }}</span>
            <span class="stat-label">{{ stat.label }}</span>
          </div>
        </div>
      </div>
      <div class="hero-scroll-hint">
        <div class="scroll-line"></div>
      </div>
    </section>

    <!-- Tech Stack -->
    <section class="section tech-section">
      <div class="container">
        <ScrollReveal>
          <h2 class="section-title">技术栈</h2>
          <p class="section-subtitle">多年实战沉淀，前后端双线并行</p>
        </ScrollReveal>

        <div class="tech-grid">
          <ScrollReveal v-for="(group, i) in techGroups" :key="group.title" :delay="i * 100">
            <div class="tech-group glass-card gradient-border">
              <div class="tech-group-icon">{{ group.icon }}</div>
              <h3 class="tech-group-title">{{ group.title }}</h3>
              <div class="tech-tags">
                <span class="tag" v-for="tech in group.techs" :key="tech">{{ tech }}</span>
              </div>
            </div>
          </ScrollReveal>
        </div>
      </div>
    </section>

    <!-- Featured Project -->
    <section class="section featured-section">
      <div class="container">
        <ScrollReveal>
          <h2 class="section-title">代表项目</h2>
          <p class="section-subtitle">从需求分析到架构设计、开发交付的全链路实践</p>
        </ScrollReveal>

        <ScrollReveal :delay="200">
          <div class="featured-card glass-card">
            <div class="featured-header">
              <div class="featured-icon">◈</div>
              <div>
                <h3 class="featured-title">轮胎企业产品生命周期管理系统（PLM）</h3>
                <p class="featured-role">前端架构主导 · 全栈开发 · IT部门主管</p>
              </div>
            </div>

            <div class="featured-body">
              <div class="featured-highlights">
                <div class="highlight-item" v-for="item in highlights" :key="item.title">
                  <div class="highlight-icon">{{ item.icon }}</div>
                  <div>
                    <h4>{{ item.title }}</h4>
                    <p>{{ item.desc }}</p>
                  </div>
                </div>
              </div>

              <div class="featured-techs">
                <div class="tech-line">
                  <span class="tech-label">前端</span>
                  <span class="tag" v-for="t in ['Vue 2/3', 'TypeScript', 'Vuex/Pinia', 'Element UI', 'Webpack/Vite']" :key="t">{{ t }}</span>
                </div>
                <div class="tech-line">
                  <span class="tech-label">后端</span>
                  <span class="tag" v-for="t in ['Java', 'Spring Boot', 'MyBatis', 'MySQL', 'Redis']" :key="t">{{ t }}</span>
                </div>
              </div>
            </div>

            <router-link to="/projects" class="featured-more">
              查看详情
              <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2"><path d="M5 12h14M12 5l7 7-7 7"/></svg>
            </router-link>
          </div>
        </ScrollReveal>
      </div>
    </section>

    <!-- CTA -->
    <section class="section cta-section">
      <div class="container">
        <ScrollReveal>
          <div class="cta-card">
            <h2>寻找可靠的全栈工程师？</h2>
            <p>前端架构、Java后端、团队管理——我可以为您的团队带来经过验证的交付能力</p>
            <div class="cta-actions">
              <a href="mailto:anidenen@outlook.com" class="btn btn-primary">联系我</a>
              <router-link to="/about" class="btn btn-outline">了解更多</router-link>
            </div>
          </div>
        </ScrollReveal>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import ParticleBackground from '@/components/ParticleBackground.vue'
import TypeWriter from '@/components/TypeWriter.vue'
import ScrollReveal from '@/components/ScrollReveal.vue'

const titles = [
  '前端架构师',
  'Java 后端开发',
  'IT 团队负责人',
  '企业级系统专家',
]

const stats = [
  { value: '8+', label: '年经验' },
  { value: '10+', label: '人团队' },
  { value: 'PLM', label: '核心领域' },
  { value: '全栈', label: '技术覆盖' },
]

const techGroups = [
  {
    icon: '🎨',
    title: '前端开发',
    techs: ['Vue 2/3', 'React', 'TypeScript', 'Webpack', 'Vite', 'Pinia', 'Element Plus', 'Ant Design', 'SCSS', 'Tailwind CSS'],
  },
  {
    icon: '⚙️',
    title: '后端开发',
    techs: ['Java', 'Spring Boot', 'Spring MVC', 'MyBatis', 'MySQL', 'Redis', 'RESTful API', 'Maven'],
  },
  {
    icon: '🛠',
    title: '工程与协作',
    techs: ['Git', 'Jenkins', 'Docker', 'Nginx', 'Linux', 'ESLint', 'Prettier', 'Swagger'],
  },
  {
    icon: '📐',
    title: '架构与管理',
    techs: ['前端工程化', '组件库设计', '权限体系', '需求分析', '团队管理', '技术选型'],
  },
]

const highlights = [
  { icon: '🏗', title: '前端架构主导', desc: '从零搭建整体前端架构，制定技术规范和组件体系' },
  { icon: '🔄', title: '全生命周期覆盖', desc: '支撑产品从需求、设计、变更到版本管理的完整流程' },
  { icon: '👥', title: '跨部门协作', desc: '作为IT主管直接与业务人员沟通需求，确保技术方案贴合实际' },
  { icon: '🚀', title: '持续迭代维护', desc: '负责系统后期维护与持续优化，保障生产环境稳定运行' },
]
</script>

<style scoped>
/* Hero */
.hero {
  position: relative;
  min-height: 100vh;
  display: flex;
  align-items: center;
  overflow: hidden;
}

.hero::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 200px;
  background: linear-gradient(transparent, var(--bg-primary));
  z-index: 1;
}

.hero-content {
  position: relative;
  z-index: 2;
  padding-top: 80px;
}

.hero-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 8px 20px;
  border-radius: 30px;
  background: rgba(0, 228, 143, 0.08);
  border: 1px solid rgba(0, 228, 143, 0.2);
  color: var(--accent-green);
  font-size: 0.85rem;
  font-weight: 500;
  margin-bottom: 32px;
  animation: fadeInUp 0.8s ease both;
}

.badge-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: var(--accent-green);
  animation: glow 2s ease infinite;
}

.hero-title {
  font-size: clamp(2.5rem, 6vw, 4.5rem);
  font-weight: 800;
  line-height: 1.2;
  margin-bottom: 24px;
  color: var(--text-primary);
  animation: fadeInUp 0.8s ease 0.1s both;
}

.hero-desc {
  font-size: 1.15rem;
  color: var(--text-secondary);
  max-width: 640px;
  line-height: 1.8;
  margin-bottom: 40px;
  animation: fadeInUp 0.8s ease 0.2s both;
}

.hero-actions {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  animation: fadeInUp 0.8s ease 0.3s both;
}

.hero-stats {
  display: flex;
  gap: 48px;
  margin-top: 72px;
  padding-top: 40px;
  border-top: 1px solid var(--border-glass);
  animation: fadeInUp 0.8s ease 0.5s both;
}

.stat {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-number {
  font-size: 2rem;
  font-weight: 800;
  font-family: var(--font-mono);
  background: var(--gradient-main);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.stat-label {
  font-size: 0.85rem;
  color: var(--text-muted);
}

.hero-scroll-hint {
  position: absolute;
  bottom: 40px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
}

.scroll-line {
  width: 1px;
  height: 60px;
  background: linear-gradient(var(--accent-cyan), transparent);
  animation: float 2s ease infinite;
}

/* Tech Section */
.tech-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 24px;
}

.tech-group {
  padding: 32px;
}

.tech-group-icon {
  font-size: 2rem;
  margin-bottom: 16px;
}

.tech-group-title {
  font-size: 1.15rem;
  font-weight: 700;
  margin-bottom: 16px;
  color: var(--text-primary);
}

.tech-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

/* Featured */
.featured-card {
  padding: 40px;
  border: 1px solid rgba(123, 47, 247, 0.15);
}

.featured-header {
  display: flex;
  align-items: flex-start;
  gap: 20px;
  margin-bottom: 32px;
}

.featured-icon {
  width: 56px;
  height: 56px;
  border-radius: var(--radius-md);
  background: var(--gradient-subtle);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: var(--accent-cyan);
  flex-shrink: 0;
}

.featured-title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 6px;
}

.featured-role {
  color: var(--accent-cyan);
  font-size: 0.9rem;
  font-family: var(--font-mono);
}

.featured-highlights {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 24px;
  margin-bottom: 32px;
}

.highlight-item {
  display: flex;
  gap: 14px;
  align-items: flex-start;
}

.highlight-icon {
  font-size: 1.3rem;
  flex-shrink: 0;
  margin-top: 2px;
}

.highlight-item h4 {
  font-size: 0.95rem;
  font-weight: 600;
  margin-bottom: 4px;
}

.highlight-item p {
  font-size: 0.85rem;
  color: var(--text-secondary);
  line-height: 1.6;
}

.featured-techs {
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding: 24px;
  background: var(--bg-glass);
  border-radius: var(--radius-sm);
  margin-bottom: 24px;
}

.tech-line {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}

.tech-label {
  font-size: 0.8rem;
  font-family: var(--font-mono);
  color: var(--text-muted);
  min-width: 40px;
}

.featured-more {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  font-size: 0.95rem;
  color: var(--accent-cyan);
}

.featured-more:hover {
  gap: 14px;
}

/* CTA */
.cta-card {
  text-align: center;
  padding: 80px 40px;
  border-radius: var(--radius-lg);
  background: var(--gradient-subtle);
  border: 1px solid var(--border-glass);
}

.cta-card h2 {
  font-size: 2.2rem;
  font-weight: 800;
  margin-bottom: 16px;
}

.cta-card p {
  font-size: 1.1rem;
  color: var(--text-secondary);
  margin-bottom: 40px;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.cta-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
  flex-wrap: wrap;
}

/* Responsive */
@media (max-width: 768px) {
  .hero-stats {
    gap: 24px;
    flex-wrap: wrap;
  }

  .featured-card {
    padding: 24px;
  }

  .featured-header {
    flex-direction: column;
  }

  .cta-card {
    padding: 48px 24px;
  }

  .cta-card h2 {
    font-size: 1.6rem;
  }
}
</style>
