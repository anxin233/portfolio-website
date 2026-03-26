import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import request from '@/utils/request'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('admin_token') || '')
  const nickname = ref(localStorage.getItem('admin_nickname') || '')

  const isLoggedIn = computed(() => !!token.value)

  async function login(username: string, password: string) {
    const res: any = await request.post('/auth/login', { username, password })
    if (res.code === 200) {
      token.value = res.data.token
      nickname.value = res.data.nickname || username
      localStorage.setItem('admin_token', res.data.token)
      localStorage.setItem('admin_nickname', nickname.value)
      return true
    }
    throw new Error(res.message || '登录失败')
  }

  async function logout() {
    try {
      await request.post('/auth/logout')
    } catch (_) { /* ignore */ }
    token.value = ''
    nickname.value = ''
    localStorage.removeItem('admin_token')
    localStorage.removeItem('admin_nickname')
  }

  async function checkAuth() {
    if (!token.value) return false
    try {
      const res: any = await request.get('/auth/check')
      return res.code === 200
    } catch (_) {
      logout()
      return false
    }
  }

  return { token, nickname, isLoggedIn, login, logout, checkAuth }
})
