<template>
  <div class="about-page">
    <section class="section page-hero">
      <div class="container">
        <ScrollReveal>
          <h1 class="section-title">关于我</h1>
          <p class="section-subtitle">{{ config.profile_subtitle || '全栈工程师 · IT团队负责人 · 持续学习者' }}</p>
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
              <h2>{{ config.profile_title || '你好，我是一名全栈工程师' }}</h2>
              <p v-if="config.profile_intro_1" v-html="config.profile_intro_1"></p>
              <p v-if="config.profile_intro_2" v-html="config.profile_intro_2"></p>
              <p v-if="config.profile_intro_3" v-html="config.profile_intro_3"></p>
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
              <p>{{ v.description }}</p>
            </div>
          </ScrollReveal>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import ScrollReveal from '@/components/ScrollReveal.vue'
import { getTimeline, getSkills, getSiteConfig, getValues } from '@/api/blog'

const config = ref<Record<string, string>>({})
const timeline = ref<Array<{ year: string; title: string; desc: string; techs: string[] }>>([])
const skills = ref<Array<{ name: string; level: number; desc: string }>>([])
const values = ref<Array<{ icon: string; title: string; description: string }>>([])

const profileTags = computed(() => {
  const tags = config.value.profile_tags
  if (tags) return tags.split(',').map(t => t.trim()).filter(Boolean)
  return ['前端架构', 'Vue 2/3', 'React', 'Java', 'Spring Boot', '团队管理', '需求分析', '企业级B端']
})

onMounted(async () => {
  try {
    const [timelineRes, skillsRes, configRes, valuesRes] = await Promise.all([
      getTimeline(), getSkills(), getSiteConfig(), getValues(),
    ])
    timeline.value = (timelineRes.data || []).map((item: any) => ({
      year: item.year,
      title: item.title,
      desc: item.description,
      techs: item.techs ? item.techs.split(',') : [],
    }))
    skills.value = (skillsRes.data || []).map((item: any) => ({
      name: item.name,
      level: item.level,
      desc: item.description,
    }))
    config.value = configRes.data || {}
    values.value = (valuesRes.data || []).map((item: any) => ({
      icon: item.icon,
      title: item.title,
      description: item.description,
    }))
  } catch {
    timeline.value = []
    skills.value = []
    config.value = {}
    values.value = []
  }
})
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
