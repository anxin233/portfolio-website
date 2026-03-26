import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  scrollBehavior(_to, _from, savedPosition) {
    if (savedPosition) return savedPosition
    return { top: 0, behavior: 'smooth' }
  },
  routes: [
    // ========== 前台页面 ==========
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/Home.vue'),
    },
    {
      path: '/about',
      name: 'About',
      component: () => import('@/views/About.vue'),
    },
    {
      path: '/projects',
      name: 'Projects',
      component: () => import('@/views/Projects.vue'),
    },
    {
      path: '/blog',
      name: 'Blog',
      component: () => import('@/views/Blog.vue'),
    },
    {
      path: '/blog/:id',
      name: 'BlogDetail',
      component: () => import('@/views/BlogDetail.vue'),
    },

    // ========== 后台管理 ==========
    {
      path: '/admin/login',
      name: 'AdminLogin',
      component: () => import('@/views/admin/Login.vue'),
      meta: { layout: 'none' },
    },
    {
      path: '/admin',
      component: () => import('@/views/admin/AdminLayout.vue'),
      meta: { requiresAuth: true },
      children: [
        {
          path: '',
          name: 'AdminDashboard',
          component: () => import('@/views/admin/Dashboard.vue'),
        },
        {
          path: 'posts',
          name: 'AdminPosts',
          component: () => import('@/views/admin/PostList.vue'),
        },
        {
          path: 'post/new',
          name: 'AdminPostNew',
          component: () => import('@/views/admin/PostEditor.vue'),
        },
        {
          path: 'post/edit/:id',
          name: 'AdminPostEdit',
          component: () => import('@/views/admin/PostEditor.vue'),
        },
      ],
    },
  ],
})

router.beforeEach((to, _from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    const token = localStorage.getItem('admin_token')
    if (!token) {
      next({ path: '/admin/login', query: { redirect: to.fullPath } })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
