<template>
  <div class="login-page">
    <div class="login-bg"></div>
    <div class="login-card glass-card">
      <div class="login-header">
        <div class="login-logo">&lt;/&gt;</div>
        <h1>后台管理</h1>
        <p>DevPortfolio Admin</p>
      </div>

      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label>用户名</label>
          <input
            v-model="username"
            type="text"
            placeholder="请输入用户名"
            autocomplete="username"
            :disabled="loading"
          />
        </div>

        <div class="form-group">
          <label>密码</label>
          <input
            v-model="password"
            type="password"
            placeholder="请输入密码"
            autocomplete="current-password"
            :disabled="loading"
            @keyup.enter="handleLogin"
          />
        </div>

        <p v-if="error" class="error-msg">{{ error }}</p>

        <button type="submit" class="btn btn-primary login-btn" :disabled="loading">
          {{ loading ? '登录中...' : '登 录' }}
        </button>
      </form>

      <div class="login-footer">
        <router-link to="/">← 返回网站首页</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const username = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')

async function handleLogin() {
  if (!username.value || !password.value) {
    error.value = '请输入用户名和密码'
    return
  }

  loading.value = true
  error.value = ''

  try {
    await authStore.login(username.value, password.value)
    router.push('/admin')
  } catch (e: any) {
    error.value = e.message || '登录失败，请重试'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  position: relative;
}

.login-bg {
  position: fixed;
  inset: 0;
  background:
    radial-gradient(ellipse at 20% 50%, rgba(0, 212, 255, 0.08) 0%, transparent 50%),
    radial-gradient(ellipse at 80% 50%, rgba(123, 47, 247, 0.08) 0%, transparent 50%),
    var(--bg-primary);
}

.login-card {
  position: relative;
  width: 100%;
  max-width: 420px;
  padding: 48px 40px;
  border: 1px solid rgba(0, 212, 255, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.login-logo {
  font-family: var(--font-mono);
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--accent-cyan);
  margin-bottom: 16px;
  animation: glow 3s ease infinite;
}

.login-header h1 {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 6px;
}

.login-header p {
  font-size: 0.85rem;
  color: var(--text-muted);
  font-family: var(--font-mono);
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 0.85rem;
  color: var(--text-secondary);
  margin-bottom: 8px;
  font-weight: 500;
}

.form-group input {
  width: 100%;
  padding: 12px 16px;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid var(--border-glass);
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  font-size: 0.95rem;
  font-family: var(--font-body);
  outline: none;
  transition: border-color 0.3s;
}

.form-group input:focus {
  border-color: var(--accent-cyan);
  box-shadow: 0 0 0 3px rgba(0, 212, 255, 0.1);
}

.form-group input::placeholder {
  color: var(--text-muted);
}

.error-msg {
  color: var(--accent-pink);
  font-size: 0.85rem;
  margin-bottom: 16px;
  padding: 8px 12px;
  background: rgba(255, 45, 149, 0.08);
  border-radius: var(--radius-sm);
}

.login-btn {
  width: 100%;
  padding: 14px;
  font-size: 1rem;
  margin-top: 8px;
  justify-content: center;
}

.login-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.login-footer {
  text-align: center;
  margin-top: 24px;
}

.login-footer a {
  font-size: 0.85rem;
  color: var(--text-muted);
}

.login-footer a:hover {
  color: var(--accent-cyan);
}
</style>
