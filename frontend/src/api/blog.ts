import request from '@/utils/request'

export interface BlogPost {
  id?: number
  title: string
  excerpt: string
  content: string
  category: string
  tags: string
  readTime: number
  published: boolean
  createdAt?: string
  updatedAt?: string
}

export function getPublishedPosts() {
  return request.get('/blog')
}

export function getPostDetail(id: number) {
  return request.get(`/blog/${id}`)
}

// Admin APIs
export function adminGetAllPosts() {
  return request.get('/admin/blog')
}

export function adminGetPost(id: number) {
  return request.get(`/admin/blog/${id}`)
}

export function adminCreatePost(post: BlogPost) {
  return request.post('/admin/blog', post)
}

export function adminUpdatePost(id: number, post: BlogPost) {
  return request.put(`/admin/blog/${id}`, post)
}

export function adminTogglePublish(id: number) {
  return request.put(`/admin/blog/${id}/publish`)
}

export function adminDeletePost(id: number) {
  return request.delete(`/admin/blog/${id}`)
}
