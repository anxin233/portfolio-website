import axios from 'axios'
import type { AxiosResponse } from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 15000,
})

request.interceptors.request.use(config => {
  const token = localStorage.getItem('admin_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  (response: AxiosResponse) => {
    const data = response.data
    if (data.code === 401) {
      localStorage.removeItem('admin_token')
      window.location.href = '/admin/login'
      return Promise.reject(new Error('未登录'))
    }
    return data
  },
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('admin_token')
      window.location.href = '/admin/login'
    }
    return Promise.reject(error)
  }
)

export default request
