import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import loginPage from "@/views/login.vue";
import indexPage from "@/views/index.vue";
import article from "@/views/article/index.vue";
import articleCategoryPage from "@/views/article/articleCategory/index.vue"
import layouts from "@/views/layouts/index.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'login',
    meta: {
      title: '后台登录'
    },
    component: loginPage
  },
  {
    path: '',
    component: layouts,
    redirect: 'index',
    children: [
      {
        // path: 'index',
        path: "/1",
        component: indexPage,
        name: 'index',
        meta: { title: '首页' },
      },
      {
        path: 'articleCategory',
        component: articleCategoryPage,
        name: 'index',
        meta: { title: '文章分类' },
      },
      {
        path: 'article',
        component: article,
        name: 'index',
        meta: { title: '文章分类' },
      },

    ]
  },
  {
    path: '/:pathMatch(.*)',
    name: '404',
    meta: {
      title: '404'
    },
    component: () => import('@/views/error/404.vue')
  }
]






const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router;
