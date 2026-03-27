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
    <section class="section" v-if="featured.featured_title">
      <div class="container">
        <ScrollReveal>
          <div class="featured-project glass-card">
            <div class="featured-badge">⭐ 代表作品</div>
            <h2 class="featured-title">{{ featured.featured_title }}</h2>
            <p class="featured-subtitle">{{ featured.featured_subtitle }}</p>

            <div class="featured-grid">
              <div class="featured-desc">
                <h3>项目背景</h3>
                <p>{{ featured.featured_background }}</p>

                <h3>我的职责</h3>
                <ul>
                  <li v-for="r in responsibilities" :key="r">
                    <strong>{{ r.split('：')[0] }}</strong>：{{ r.split('：').slice(1).join('：') }}
                  </li>
                </ul>
              </div>

              <div class="featured-meta">
                <div class="meta-block">
                  <h4>前端技术栈</h4>
                  <div class="meta-tags">
                    <span class="tag" v-for="t in frontendTechs" :key="t">{{ t }}</span>
                  </div>
                </div>
                <div class="meta-block">
                  <h4>后端技术栈</h4>
                  <div class="meta-tags">
                    <span class="tag" v-for="t in backendTechs" :key="t">{{ t }}</span>
                  </div>
                </div>
                <div class="meta-block">
                  <h4>项目亮点</h4>
                  <div class="highlights-list">
                    <div class="highlight" v-for="h in highlights" :key="h">
                      <span class="highlight-check">✓</span>
                      {{ h }}
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="challenges" v-if="challenges.length">
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
          <ScrollReveal v-for="(p, i) in otherProjects" :key="p.id" :delay="i * 100">
            <div class="project-card glass-card gradient-border">
              <div class="project-card-header">
                <div class="project-icon">{{ p.icon }}</div>
                <div class="project-links">
                  <a v-if="p.github" :href="p.github" target="_blank" class="project-link">GitHub</a>
                  <a v-if="p.demo" :href="p.demo" target="_blank" class="project-link">Demo</a>
                </div>
              </div>
              <h3 class="project-name">{{ p.title }}</h3>
              <p class="project-desc">{{ p.description }}</p>
              <div class="project-tags">
                <span class="tag" v-for="t in (p.techs || '').split(',').map((s: string) => s.trim()).filter(Boolean)" :key="t">{{ t }}</span>
              </div>
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
import { getProjects, getProjectChallenges, getFeaturedProject } from '@/api/blog'

const featured = ref<Record<string, string>>({})
const challenges = ref<Array<{ title: string; problem: string; solution: string; result: string }>>([])
const otherProjects = ref<Array<any>>([])

const responsibilities = computed(() => {
  const raw = featured.value.featured_responsibilities || ''
  return raw.split('\n').filter(Boolean)
})

const highlights = computed(() => {
  const raw = featured.value.featured_highlights || ''
  return raw.split('\n').filter(Boolean)
})

const frontendTechs = computed(() => {
  const raw = featured.value.featured_frontend_techs || ''
  return raw.split(',').map(s => s.trim()).filter(Boolean)
})

const backendTechs = computed(() => {
  const raw = featured.value.featured_backend_techs || ''
  return raw.split(',').map(s => s.trim()).filter(Boolean)
})

onMounted(async () => {
  try {
    const [featuredRes, challengesRes, projectsRes] = await Promise.all([
      getFeaturedProject(), getProjectChallenges(), getProjects(),
    ])
    featured.value = featuredRes.data || {}
    challenges.value = (challengesRes.data || []).map((c: any) => ({
      title: c.title,
      problem: c.problem,
      solution: c.solution,
      result: c.result,
    }))
    otherProjects.value = projectsRes.data || []
  } catch {
    featured.value = {}
    challenges.value = []
    otherProjects.value = []
  }
})
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
