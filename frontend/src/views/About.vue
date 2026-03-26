<template>
  <div class="about-page">
    <section class="section page-hero">
      <div class="container">
        <ScrollReveal>
          <h1 class="section-title">关于我</h1>
          <p class="section-subtitle">全栈工程师 · IT团队负责人 · 持续学习者</p>
        </ScrollReveal>
      </div>
    </section>

    <!-- Profile -->
    <section class="section profile-section">
      <div class="container">
        <ScrollReveal>
          <div class="profile-card glass-card">
            <div class="profile-avatar">
              <div class="avatar-placeholder">&lt;/&gt;</div>
            </div>
            <div class="profile-info">
              <h2>你好，我是一名全栈工程师</h2>
              <p>
                2017年起从事互联网研发，长期面向<strong>企业级B端</strong>场景。
                擅长在需求复杂、角色多、流程长的业务里，把前端做成可维护、可扩展、可协作交付的形态；
                同时具备Java后端开发能力，能从方案到上线完成闭环。
              </p>
              <p>
                担任<strong>IT部门主管</strong>，带领10人团队。直接与使用人员沟通业务需求，
                将业务语言转化为技术方案，并负责系统的持续迭代与维护。
              </p>
              <p>
                保持对新技术的敏感度，从Vue 2到Vue 3再到React，持续拓展技术边界。
                相信好的工程师不只是写代码，更是<strong>用技术解决业务问题</strong>的人。
              </p>
              <div class="profile-tags">
                <span class="tag" v-for="t in profileTags" :key="t">{{ t }}</span>
              </div>
            </div>
          </div>
        </ScrollReveal>
      </div>
    </section>

    <!-- Timeline -->
    <section class="section timeline-section">
      <div class="container">
        <ScrollReveal>
          <h2 class="section-title">职业历程</h2>
          <p class="section-subtitle">从一线开发到技术管理的成长之路</p>
        </ScrollReveal>

        <div class="timeline">
          <div class="timeline-line"></div>
          <ScrollReveal
            v-for="(item, i) in timeline"
            :key="i"
            :delay="i * 150"
          >
            <div class="timeline-item" :class="{ right: i % 2 !== 0 }">
              <div class="timeline-dot"></div>
              <div class="timeline-card glass-card">
                <span class="timeline-year">{{ item.year }}</span>
                <h3>{{ item.title }}</h3>
                <p>{{ item.desc }}</p>
                <div class="timeline-techs" v-if="item.techs">
                  <span class="tag" v-for="t in item.techs" :key="t">{{ t }}</span>
                </div>
              </div>
            </div>
          </ScrollReveal>
        </div>
      </div>
    </section>

    <!-- Skills -->
    <section class="section skills-section">
      <div class="container">
        <ScrollReveal>
          <h2 class="section-title">技能图谱</h2>
          <p class="section-subtitle">不是罗列关键词，每项都有实际项目支撑</p>
        </ScrollReveal>

        <div class="skills-grid">
          <ScrollReveal v-for="(skill, i) in skills" :key="skill.name" :delay="i * 80">
            <div class="skill-item">
              <div class="skill-header">
                <span class="skill-name">{{ skill.name }}</span>
                <span class="skill-level">{{ skill.level }}%</span>
              </div>
              <div class="skill-bar">
                <div class="skill-fill" :style="{ width: skill.level + '%' }"></div>
              </div>
              <p class="skill-desc">{{ skill.desc }}</p>
            </div>
          </ScrollReveal>
        </div>
      </div>
    </section>

    <!-- Values -->
    <section class="section values-section">
      <div class="container">
        <ScrollReveal>
          <h2 class="section-title">工程理念</h2>
          <p class="section-subtitle">这些原则指导我的每一次技术决策</p>
        </ScrollReveal>

        <div class="values-grid">
          <ScrollReveal v-for="(v, i) in values" :key="v.title" :delay="i * 100">
            <div class="value-card glass-card gradient-border">
              <div class="value-icon">{{ v.icon }}</div>
              <h3>{{ v.title }}</h3>
              <p>{{ v.desc }}</p>
            </div>
          </ScrollReveal>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import ScrollReveal from '@/components/ScrollReveal.vue'

const profileTags = [
  '前端架构', 'Vue 2/3', 'React', 'Java', 'Spring Boot',
  '团队管理', '需求分析', '企业级B端',
]

const timeline = [
  {
    year: '2017',
    title: '踏入互联网行业',
    desc: '开始前端开发工作，从原生JS、jQuery到现代框架的过渡，打下扎实基础。',
    techs: ['JavaScript', 'HTML/CSS', 'jQuery'],
  },
  {
    year: '2018-2019',
    title: '深入Vue生态，接触全栈',
    desc: '主攻Vue 2.x，同时开始学习Java后端开发，逐步具备全栈交付能力。',
    techs: ['Vue 2', 'Element UI', 'Java', 'Spring Boot'],
  },
  {
    year: '2020-2021',
    title: '主导PLM系统前端架构',
    desc: '在轮胎企业PLM项目中，从零搭建前端架构体系，制定规范，并参与后端开发。',
    techs: ['Vue 2/3', 'TypeScript', 'Spring Boot', 'MySQL'],
  },
  {
    year: '2022-2023',
    title: '晋升IT部门主管',
    desc: '开始管理10人技术团队，兼顾技术决策与业务沟通，负责系统持续迭代。',
    techs: ['团队管理', '需求分析', 'React', 'Pinia'],
  },
  {
    year: '2024-至今',
    title: '技术深耕与持续学习',
    desc: '深入Vue 3 Composition API与React，探索前端工程化最佳实践，拥抱AI辅助开发。',
    techs: ['Vue 3', 'React', 'Vite', 'AI 工具链'],
  },
]

const skills = [
  { name: 'Vue 2 / Vue 3', level: 95, desc: '核心技术栈，从架构搭建到组件设计均有大量实战' },
  { name: 'React', level: 75, desc: '能独立开发React项目，持续深入Hooks与生态' },
  { name: 'TypeScript', level: 85, desc: '类型驱动开发，提升大型项目的可维护性' },
  { name: 'JavaScript / ES6+', level: 90, desc: '扎实的语言基础，理解引擎与运行时机制' },
  { name: 'Java / Spring Boot', level: 78, desc: '参与后端业务开发，熟悉RESTful API设计与数据库操作' },
  { name: 'MySQL / Redis', level: 72, desc: '业务场景中的数据建模与缓存策略' },
  { name: '前端工程化', level: 88, desc: 'Webpack/Vite构建、规范体系、CI/CD流程' },
  { name: '团队管理', level: 80, desc: '10人团队的任务分配、代码评审与技术指导' },
]

const values = [
  {
    icon: '🎯',
    title: '业务驱动技术',
    desc: '技术选型服务于业务目标，不为炫技而引入复杂度。每次决策都考虑团队能力与维护成本。',
  },
  {
    icon: '🧱',
    title: '可维护性优先',
    desc: '好的代码不是写出来让自己佩服的，是让团队任何人都能快速理解和安全修改的。',
  },
  {
    icon: '📏',
    title: '约定大于配置',
    desc: '通过工程化手段（规范、模板、自动化检查）降低协作摩擦，让团队产出一致。',
  },
  {
    icon: '🔄',
    title: '渐进式迭代',
    desc: '不追求一步到位的完美方案，用小步快跑、持续重构的方式让系统健康演进。',
  },
]
</script>

<style scoped>
.page-hero {
  padding-top: calc(var(--header-height) + 60px);
  padding-bottom: 40px;
}

/* Profile */
.profile-card {
  display: flex;
  gap: 48px;
  padding: 48px;
  align-items: flex-start;
}

.avatar-placeholder {
  width: 140px;
  height: 140px;
  border-radius: 50%;
  background: var(--gradient-subtle);
  border: 2px solid var(--border-glass);
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: var(--font-mono);
  font-size: 2.5rem;
  color: var(--accent-cyan);
  flex-shrink: 0;
  animation: glow 3s ease infinite;
}

.profile-info h2 {
  font-size: 1.6rem;
  margin-bottom: 20px;
}

.profile-info p {
  color: var(--text-secondary);
  margin-bottom: 14px;
  line-height: 1.8;
}

.profile-info strong {
  color: var(--accent-cyan);
}

.profile-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 20px;
}

/* Timeline */
.timeline {
  position: relative;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px 0;
}

.timeline-line {
  position: absolute;
  left: 50%;
  top: 0;
  bottom: 0;
  width: 2px;
  background: linear-gradient(var(--accent-cyan), var(--accent-purple), var(--accent-pink));
  transform: translateX(-50%);
}

.timeline-item {
  position: relative;
  width: 45%;
  margin-bottom: 40px;
}

.timeline-item.right {
  margin-left: 55%;
}

.timeline-dot {
  position: absolute;
  top: 24px;
  width: 14px;
  height: 14px;
  border-radius: 50%;
  background: var(--accent-cyan);
  box-shadow: 0 0 20px rgba(0, 212, 255, 0.5);
  z-index: 1;
}

.timeline-item:not(.right) .timeline-dot {
  right: -36px;
}

.timeline-item.right .timeline-dot {
  left: -36px;
}

.timeline-card {
  padding: 24px;
}

.timeline-year {
  font-family: var(--font-mono);
  font-size: 0.85rem;
  color: var(--accent-cyan);
  font-weight: 700;
}

.timeline-card h3 {
  font-size: 1.1rem;
  margin: 8px 0;
}

.timeline-card p {
  font-size: 0.9rem;
  color: var(--text-secondary);
  line-height: 1.6;
}

.timeline-techs {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-top: 12px;
}

/* Skills */
.skills-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 24px;
}

.skill-item {
  padding: 20px 24px;
  border-radius: var(--radius-sm);
  background: var(--bg-glass);
  border: 1px solid var(--border-glass);
}

.skill-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.skill-name {
  font-weight: 600;
  font-size: 0.95rem;
}

.skill-level {
  font-family: var(--font-mono);
  font-size: 0.85rem;
  color: var(--accent-cyan);
}

.skill-bar {
  height: 4px;
  background: rgba(255, 255, 255, 0.06);
  border-radius: 2px;
  overflow: hidden;
  margin-bottom: 10px;
}

.skill-fill {
  height: 100%;
  background: var(--gradient-main);
  border-radius: 2px;
  transition: width 1.5s cubic-bezier(0.4, 0, 0.2, 1);
}

.skill-desc {
  font-size: 0.8rem;
  color: var(--text-muted);
}

/* Values */
.values-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 24px;
}

.value-card {
  padding: 32px;
  text-align: center;
}

.value-icon {
  font-size: 2.5rem;
  margin-bottom: 16px;
}

.value-card h3 {
  font-size: 1.1rem;
  margin-bottom: 12px;
}

.value-card p {
  font-size: 0.88rem;
  color: var(--text-secondary);
  line-height: 1.7;
}

/* Responsive */
@media (max-width: 768px) {
  .profile-card {
    flex-direction: column;
    align-items: center;
    text-align: center;
    padding: 32px 24px;
    gap: 24px;
  }

  .profile-tags {
    justify-content: center;
  }

  .timeline-line {
    left: 20px;
  }

  .timeline-item,
  .timeline-item.right {
    width: calc(100% - 50px);
    margin-left: 50px;
  }

  .timeline-dot {
    left: -36px !important;
    right: auto !important;
  }
}
</style>
