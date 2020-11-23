import Vue from 'vue'
import Router, {RouteConfig} from 'vue-router'
/* Layout */
import Layout from '@/layout/index.vue'
/* Router modules */
import duration from './modules/components'
import balancesTable from './modules/balancesTable'

Vue.use(Router)

/**
 ConstantRoutes
 没有权限要求的基本页所有角色都可以访问
 */
export const constantRoutes: RouteConfig[] = [
  // 重定向组件
  {
    path: '/redirect',
    component: Layout,
    meta: { hidden: true },
    children: [
      {
        path: '/redirect/:path*',
        component: () => import(/* webpackChunkName: "redirect" */ '@/views/redirect/index.vue')
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/duration/loantable'
  },
  duration,
  balancesTable
]

/**
 * asyncRoutes
 * 需要根据用户角色动态加载的路由
 */
export const asyncRoutes: RouteConfig[] = []

const createRouter = () => new Router({
  scrollBehavior: (to, from, savedPosition) => {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  },
  base: process.env.BASE_URL,
  routes: constantRoutes
})

const router = createRouter()

export default router
