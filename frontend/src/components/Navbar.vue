<template>
  <header class="navbar" :class="{ scrolled, 'menu-open': menuOpen }">
    <div class="container navbar-inner">
      <router-link to="/" class="logo" @click="menuOpen = false">
        <span class="logo-bracket">&lt;</span>
        <span class="logo-text">Dev</span>
        <span class="logo-bracket">/&gt;</span>
      </router-link>

      <button class="menu-toggle" @click="menuOpen = !menuOpen" :aria-label="menuOpen ? '关闭菜单' : '打开菜单'">
        <span></span>
        <span></span>
        <span></span>
      </button>

      <nav class="nav-links" :class="{ open: menuOpen }">
        <router-link
          v-for="link in links"
          :key="link.path"
          :to="link.path"
          class="nav-link"
          @click="menuOpen = false"
        >
          <span class="nav-link-icon">{{ link.icon }}</span>
          {{ link.name }}
        </router-link>
      </nav>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const scrolled = ref(false)
const menuOpen = ref(false)

const links = [
  { path: '/', name: '首页', icon: '⌂' },
  { path: '/about', name: '关于', icon: '◉' },
  { path: '/projects', name: '项目', icon: '◈' },
  { path: '/blog', name: '博客', icon: '✎' },
]

function handleScroll() {
  scrolled.value = window.scrollY > 50
}

onMounted(() => window.addEventListener('scroll', handleScroll))
onUnmounted(() => window.removeEventListener('scroll', handleScroll))
</script>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: var(--header-height);
  z-index: 1000;
  transition: all 0.4s ease;
  background: transparent;
}

.navbar.scrolled {
  background: rgba(10, 10, 26, 0.85);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid var(--border-glass);
}

.navbar-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.logo {
  font-family: var(--font-mono);
  font-size: 1.5rem;
  font-weight: 700;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 2px;
}

.logo-bracket {
  color: var(--accent-cyan);
}

.logo-text {
  background: var(--gradient-main);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 8px;
}

.nav-link {
  padding: 8px 18px;
  border-radius: var(--radius-sm);
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--text-secondary);
  text-decoration: none;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 6px;
}

.nav-link-icon {
  font-size: 0.85rem;
  opacity: 0.6;
}

.nav-link:hover,
.nav-link.router-link-active {
  color: var(--accent-cyan);
  background: rgba(0, 212, 255, 0.06);
}

.menu-toggle {
  display: none;
  flex-direction: column;
  gap: 5px;
  background: none;
  border: none;
  cursor: pointer;
  padding: 4px;
  z-index: 1001;
}

.menu-toggle span {
  width: 24px;
  height: 2px;
  background: var(--text-primary);
  border-radius: 2px;
  transition: all 0.3s ease;
}

.menu-open .menu-toggle span:nth-child(1) {
  transform: rotate(45deg) translate(5px, 5px);
}

.menu-open .menu-toggle span:nth-child(2) {
  opacity: 0;
}

.menu-open .menu-toggle span:nth-child(3) {
  transform: rotate(-45deg) translate(5px, -5px);
}

@media (max-width: 768px) {
  .menu-toggle {
    display: flex;
  }

  .nav-links {
    position: fixed;
    top: 0;
    right: -100%;
    width: 280px;
    height: 100vh;
    flex-direction: column;
    background: var(--bg-secondary);
    padding: 100px 32px 32px;
    gap: 4px;
    transition: right 0.4s cubic-bezier(0.4, 0, 0.2, 1);
    border-left: 1px solid var(--border-glass);
  }

  .nav-links.open {
    right: 0;
  }

  .nav-link {
    width: 100%;
    padding: 14px 18px;
    font-size: 1rem;
  }
}
</style>
