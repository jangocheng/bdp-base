import {RouteConfig} from 'vue-router'
import Layout from '@/layout/index.vue'

const balancestableRouter: RouteConfig = {
  path: '/balancestable',
  component: Layout,
  redirect: 'noredirect',
  meta: {
    title: '余额表',
    icon: 'component'
  },
  children: [
    {
      path: 'overduetable',
      component: () => import(/* webpackChunkName: "overdue" */ '@/views/Balance/Overdue.vue'),
      name: 'overduetable',
      meta: { title: '逾期表' }
    },
    {
      path: 'Undue',
      component: () => import(/* webpackChunkName: "undue" */ '@/views/Balance/Undue.vue'),
      name: 'Undue',
      meta: { title: '未到期表' }
    },
    {
      path: 'distribution',
      component: () => import(/* webpackChunkName: "distribution" */ '@/views/Balance/Distribution.vue'),
      name: 'distribution',
      meta: { title: '剩余本金分布表' }
    },
    {
      path: 'fivelevel',
      component: () => import(/* webpackChunkName: "fivelevel" */ '@/views/Balance/Fivelevel.vue'),
      name: 'fivelevel',
      meta: { title: '剩余本金五级分类表' }
    }
  ]
}

// @ts-ignore
export default balancestableRouter
