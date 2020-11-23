import {RouteConfig} from 'vue-router'
import Layout from '@/layout/index.vue'

const duration: RouteConfig = {
  path: '/duration',
  component: Layout,
  redirect: 'noredirect',
  meta: {
    title: '期间表',
    icon: 'component'
  },
  children: [
    {
      path: 'loantable',
      component: () => import(/* webpackChunkName: "Loantable" */ '@/views/DurationTable/Loantable.vue'),
      meta: { title: '放款表' },
      name: 'loantable'
    },
    {
      path: 'duetable',
      component: () => import(/* webpackChunkName: "Maturity" */ '@/views/DurationTable/Maturity.vue'),
      name: 'duetable',
      meta: { title: '到期表' }
    },
    {
      path: 'actualrepayment',
      component: () => import(/* webpackChunkName: "actualrepayment" */ '@/views/DurationTable/Actualrepayment.vue'),
      name: 'actualrepayment',
      meta: { title: '实还表' }
    },
    {
      path: 'deduction',
      name: 'deduction',
      component: () => import(/* webpackChunkName: "deduction" */ '@/views/DurationTable/Deduction.vue'),
      meta: { title: '减免表' }
    },
    {
      path: 'compensatory',
      name: 'compensatory',
      component: () => import(/* webpackChunkName: "compensatory" */ '@/views/DurationTable/Compensatory.vue'),
      meta: { title: '代偿表' }
    },
    {
      path: 'buyout',
      name: 'buyout',
      component: () => import(/* webpackChunkName: "buyout" */ '@/views/DurationTable/Buyout.vue'),
      meta: { title: '买断表' }
    }
  ]
}

// @ts-ignore
export default duration
