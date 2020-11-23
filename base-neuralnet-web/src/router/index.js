import Vue from 'vue';
import Router from 'vue-router';
/* Layout */
import Layout from '../views/layout/Layout';

Vue.use(Router);

export const constantRouterMap = [
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/complexnetwork/check',
    hidden: true,
    children: [
      {
        path: 'check',
        name: '同一设备登录用户查询',
        component: () => import('@/views/IMEI/index'),
      },
    ],
  },
  {
    path: '/complexnetwork',
    component: Layout,
    name: '神经网络',
    meta: {
      title: '神经网络',
    },
    children: [
      {
        path: 'check',
        name: '同一设备登录用户查询',
        component: () => import('@/views/IMEI/index'),
        meta: { title: '同一设备登录用户查询' },
      },
      {
        path: 'customer',
        name: 'customer',
        component: () => import('@/views/Customer/index.vue'),
        meta: {
          title: '客户一度人脉逾期查询',
        },
      },
    ],
  },
  {
    path: '/business',
    component: Layout,
    name: '业务大盘数据',
    meta: {
      title: '业务大盘数据',
    },
    children: [
      {
        path: 'registration',
        name: '实时注册数据',
        component: () => import('@/views/business/registration/index.vue'),
        meta: {
          title: '实时注册数据',
        },
      },
      {
        path: 'businessApply',
        name: '实时申请数据',
        component: () => import('@/views/business/businessApply/index.vue'),
        meta: {
          title: '实时申请数据',
        },
      },
      {
        path: 'PromotionAndDrainageByChannel',
        name: '推广引流(渠道)',
        component: () =>
          import('@/views/business/Promotion&DrainageByChannel/index.vue'),
        meta: {
          title: '推广引流(渠道)',
        },
      },
      {
        path: 'Promotion&DrainageByTime',
        name: '推广引流(时间)',
        component: () => import('@/views/Promotion&DrainageByTime/index.vue'),
        meta: {
          title: '推广引流(时间)',
        },
      },
      {
        path: 'payAndDraw',
        name: '支付提现',
        component: () => import('@/views/payAndDraw/index.vue'),
        meta: {
          title: '支付提现',
        },
      },
      {
        path: 'StoreAudit',
        name: '门店审核',
        component: () => import('@/views/StoreAudit/index.vue'),
        meta: {
          title: '门店审核',
        },
      },
      {
        path: 'MechantAudit',
        name: '商户审核',
        component: () => import('@/views/MerchantAudit/index.vue'),
        meta: {
          title: '商户审核',
        },
      },
    ],
  },
  {
    path: '/zfb',
    component: Layout,
    name: '支付宝日报',
    meta: {
      title: '支付宝日报',
    },
    children: [
      {
        path: 'applydata',
        name: '进件注册',
        component: () => import('@/views/zfb/applydata/index.vue'),
        meta: {
          title: '进件注册',
        },
      },
      {
        path: 'payAndDraw',
        name: '支付提现',
        component: () => import('@/views/zfb/Pay2Withdrawals/index.vue'),
        meta: {
          title: '支付提现',
        },
      },
      {
        path: 'payWater',
        name: '支付流水',
        component: () => import('@/views/zfb/Pay2Water/index.vue'),
        meta: {
          title: '支付流水',
        },
      },
      {
        path: 'mallPay',
        name: '商城支付',
        component: () => import('@/views/zfb/mallPay/index.vue'),
        meta: {
          title: '商城支付',
        },
      },
      {
        path: 'dailyWage',
        name: '电商收入',
        component: () => import('@/views/zfb/dailyWage/index.vue'),
        meta: {
          title: '电商收入',
        },
      },
    ],
  },
  { path: '*', redirect: '/404', hidden: true },
];

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap,
});
