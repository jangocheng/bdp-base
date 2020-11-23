import router from './router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {Route} from 'vue-router'
import settings from './settings'
import {PermissionModule} from '@/store/modules/permission'
import {DictModule} from '@/store/modules/dict'

import {getToken} from '@/utils/cookies'

NProgress.configure({ showSpinner: false })

const getPageTitle = (key?: string) => {
  return `${settings.title}`
}

router.beforeEach((to: Route, _: Route, next: any) => {
  NProgress.start()
  // 更新字典
  DictModule.getDict()
  console.log('process.env', process.env)
  let TOKEN: any = getToken()
  if (process.env.NODE_ENV === 'development') TOKEN = true
  if (TOKEN) {
    // 检查用户是否获得了他的权限角色
    try {
      PermissionModule.GenerateRoutes(['admin'])
      router.addRoutes(PermissionModule.dynamicRoutes)
      next()
    } catch (err) {
      NProgress.done()
    }
  } else {
    // @ts-ignore
    window.location.href = process.env.VUE_APP_LOGIN_URL
  }
})

router.afterEach((to: Route) => {
  NProgress.done()
  document.title = getPageTitle(to.meta.title)
})
