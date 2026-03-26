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

// Categories
export interface Category {
  id?: number
  name: string
  sortOrder: number
  createdAt?: string
}

export function getCategories() {
  return request.get('/categories')
}

export function adminGetCategories() {
  return request.get('/admin/categories')
}

export function adminCreateCategory(category: Category) {
  return request.post('/admin/categories', category)
}

export function adminUpdateCategory(id: number, category: Category) {
  return request.put(`/admin/categories/${id}`, category)
}

export function adminDeleteCategory(id: number) {
  return request.delete(`/admin/categories/${id}`)
}

// Timeline
export interface TimelineItem {
  id?: number
  year: string
  title: string
  description: string
  techs: string
  sortOrder: number
  createdAt?: string
}

export function getTimeline() {
  return request.get('/timeline')
}

export function adminGetTimeline() {
  return request.get('/admin/timeline')
}

export function adminCreateTimeline(item: TimelineItem) {
  return request.post('/admin/timeline', item)
}

export function adminUpdateTimeline(id: number, item: TimelineItem) {
  return request.put(`/admin/timeline/${id}`, item)
}

export function adminDeleteTimeline(id: number) {
  return request.delete(`/admin/timeline/${id}`)
}

// Skills
export interface SkillItem {
  id?: number
  name: string
  level: number
  description: string
  sortOrder: number
  createdAt?: string
}

export function getSkills() {
  return request.get('/skills')
}

export function adminGetSkills() {
  return request.get('/admin/skills')
}

export function adminCreateSkill(item: SkillItem) {
  return request.post('/admin/skills', item)
}

export function adminUpdateSkill(id: number, item: SkillItem) {
  return request.put(`/admin/skills/${id}`, item)
}

export function adminDeleteSkill(id: number) {
  return request.delete(`/admin/skills/${id}`)
}

// Site Config
export function getSiteConfig() {
  return request.get('/site-config')
}

export function adminGetSiteConfig() {
  return request.get('/admin/site-config')
}

export function adminSaveSiteConfig(configs: Record<string, string>) {
  return request.put('/admin/site-config', configs)
}

// Value Items (工程理念)
export interface ValueItemData {
  id?: number
  icon: string
  title: string
  description: string
  sortOrder: number
  createdAt?: string
}

export function getValues() {
  return request.get('/values')
}

export function adminGetValues() {
  return request.get('/admin/values')
}

export function adminCreateValue(item: ValueItemData) {
  return request.post('/admin/values', item)
}

export function adminUpdateValue(id: number, item: ValueItemData) {
  return request.put(`/admin/values/${id}`, item)
}

export function adminDeleteValue(id: number) {
  return request.delete(`/admin/values/${id}`)
}

// Projects
export interface ProjectData {
  id?: number
  icon: string
  title: string
  description: string
  techs: string
  github: string
  demo: string
  sortOrder: number
  createdAt?: string
}

export interface ChallengeData {
  id?: number
  title: string
  problem: string
  solution: string
  result: string
  sortOrder: number
  createdAt?: string
}

export function getProjects() {
  return request.get('/projects')
}

export function getProjectChallenges() {
  return request.get('/projects/challenges')
}

export function getFeaturedProject() {
  return request.get('/projects/featured')
}

export function adminGetProjects() {
  return request.get('/admin/projects')
}

export function adminCreateProject(item: ProjectData) {
  return request.post('/admin/projects', item)
}

export function adminUpdateProject(id: number, item: ProjectData) {
  return request.put(`/admin/projects/${id}`, item)
}

export function adminDeleteProject(id: number) {
  return request.delete(`/admin/projects/${id}`)
}

export function adminGetChallenges() {
  return request.get('/admin/projects/challenges')
}

export function adminCreateChallenge(item: ChallengeData) {
  return request.post('/admin/projects/challenges', item)
}

export function adminUpdateChallenge(id: number, item: ChallengeData) {
  return request.put(`/admin/projects/challenges/${id}`, item)
}

export function adminDeleteChallenge(id: number) {
  return request.delete(`/admin/projects/challenges/${id}`)
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
