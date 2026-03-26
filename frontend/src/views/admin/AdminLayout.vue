<template>
  <div class="admin-layout">
    <!-- Sidebar -->
    <aside class="sidebar" :class="{ collapsed: sidebarCollapsed }">
      <div class="sidebar-header">
        <router-link to="/admin" class="sidebar-logo">
          <span class="logo-icon">&lt;/&gt;</span>
          <span class="logo-text" v-show="!sidebarCollapsed">后台管理</span>
        </router-link>
      </div>

      <nav class="sidebar-nav">
        <router-link
          v-for="item in menuItems"
          :key="item.path"
          :to="item.path"
          class="nav-item"
          :class="{ active: $route.path === item.path }"
        >
          <span class="nav-icon">{{ item.icon }}</span>
          <span class="nav-label" v-show="!sidebarCollapsed">{{ item.label }}</span>
        </router-link>
      </nav>

      <div class="sidebar-footer">
        <router-link to="/" class="nav-item" target="_blank">
          <span class="nav-icon">🌐</span>
          <span class="nav-label" v-show="!sidebarCollapsed">访问网站</span>
        </router-link>
      </div>
    </aside>

    <!-- Main Area -->
    <div class="main-area">
      <!-- Top Bar -->
      <header class="topbar">
        <button class="toggle-btn" @click="sidebarCollapsed = !sidebarCollapsed">
          <span>{{ sidebarCollapsed ? '☰' : '✕' }}</span>
        </button>

        <div class="topbar-right">
          <span class="user-name">{{ authStore.nickname || 'Admin' }}</span>
          <button class="logout-btn" @click="handleLogout">退出</button>
        </div>
      </header>

      <!-- Content -->
      <main class="content">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const sidebarCollapsed = ref(false)

const menuItems = [
  { path: '/admin', icon: '📊', label: '仪表盘' },
  { path: '/admin/posts', icon: '📝', label: '文章管理' },
  { path: '/admin/post/new', icon: '✏️', label: '写文章' },
  { path: '/admin/timeline', icon: '🗓️', label: '职业历程' },
  { path: '/admin/skills', icon: '💡', label: '技能图谱' },
  { path: '/admin/profile', icon: '👤', label: '个人简介' },
  { path: '/admin/values', icon: '🎯', label: '工程理念' },
  { path: '/admin/featured-project', icon: '⭐', label: '代表作品' },
  { path: '/admin/projects-manage', icon: '📁', label: '项目管理' },
]

async function handleLogout() {
  await authStore.logout()
  router.push('/admin/login')
}
</script>

<style scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background: var(--bg-primary);
}

/* Sidebar */
.sidebar {
  width: 240px;
  background: var(--bg-secondary);
  border-right: 1px solid var(--border-glass);
  display: flex;
  flex-direction: column;
  transition: width 0.3s ease;
  flex-shrink: 0;
}

.sidebar.collapsed {
  width: 68px;
}

.sidebar-header {
  padding: 20px 16px;
  border-bottom: 1px solid var(--border-glass);
}

.sidebar-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  text-decoration: none;
  color: var(--text-primary);
  font-weight: 700;
  font-size: 1.05rem;
}

.logo-icon {
  font-family: var(--font-mono);
  color: var(--accent-cyan);
  font-size: 1.2rem;
  flex-shrink: 0;
}

.sidebar-nav {
  flex: 1;
  padding: 12px 8px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 14px;
  border-radius: var(--radius-sm);
  color: var(--text-secondary);
  text-decoration: none;
  font-size: 0.9rem;
  transition: all 0.2s;
  white-space: nowrap;
}

.nav-item:hover {
  background: rgba(0, 212, 255, 0.06);
  color: var(--text-primary);
}

.nav-item.active,
.nav-item.router-link-exact-active {
  background: rgba(0, 212, 255, 0.1);
  color: var(--accent-cyan);
}

.nav-icon {
  font-size: 1.1rem;
  flex-shrink: 0;
  width: 24px;
  text-align: center;
}

.sidebar-footer {
  padding: 12px 8px;
  border-top: 1px solid var(--border-glass);
}

/* Main */
.main-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.topbar {
  height: 60px;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid var(--border-glass);
  background: var(--bg-secondary);
  flex-shrink: 0;
}

.toggle-btn {
  background: none;
  border: 1px solid var(--border-glass);
  color: var(--text-secondary);
  width: 36px;
  height: 36px;
  border-radius: var(--radius-sm);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  transition: all 0.2s;
}

.toggle-btn:hover {
  border-color: var(--accent-cyan);
  color: var(--accent-cyan);
}

.topbar-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-name {
  font-size: 0.9rem;
  color: var(--text-secondary);
}

.logout-btn {
  padding: 6px 16px;
  background: transparent;
  border: 1px solid rgba(255, 45, 149, 0.3);
  color: var(--accent-pink);
  border-radius: var(--radius-sm);
  font-size: 0.85rem;
  cursor: pointer;
  transition: all 0.2s;
  font-family: var(--font-body);
}

.logout-btn:hover {
  background: rgba(255, 45, 149, 0.1);
}

.content {
  flex: 1;
  padding: 32px;
  overflow-y: auto;
}

@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    left: 0;
    top: 0;
    bottom: 0;
    z-index: 100;
    transform: translateX(-100%);
  }

  .sidebar:not(.collapsed) {
    transform: translateX(0);
    box-shadow: 20px 0 60px rgba(0, 0, 0, 0.5);
  }

  .content {
    padding: 16px;
  }
}
</style>
