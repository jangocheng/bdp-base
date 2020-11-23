import {Action, getModule, Module, Mutation, VuexModule} from 'vuex-module-decorators'
import {RouteConfig} from 'vue-router'
import {asyncRoutes, constantRoutes} from '@/router'
import store from '@/store'

const hasPermission = (roles: string[], route: RouteConfig) => {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 *
 * @param routes
 * @param roles
 */
export const filterAsyncRoutes = (routes: RouteConfig[], roles: string[]) => {
  const res: RouteConfig[] = []
  routes.forEach(route => {
    const r = { ...route }
    if (hasPermission(roles, r)) {
      if (r.children) {
        r.children = filterAsyncRoutes(r.children, roles)
      }
      res.push(r)
    }
  })
  return res
}

export interface IPermissionState {
  routes: RouteConfig[]
  dynamicRoutes: RouteConfig[]
}

@Module({ dynamic: true, store, name: 'permission' })
class Permission extends VuexModule implements IPermissionState {
  public routes: RouteConfig[] = []
  public dynamicRoutes: RouteConfig[] = []

  /**
   * 生成路由
   * @param roles
   * @constructor
   */
  @Action
  public GenerateRoutes(roles: string[]) {
    let accessedRoutes
    // 判断当前用户角色是否是admin
    if (roles.includes('admin')) {
      accessedRoutes = asyncRoutes
    } else {
      accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
    }
    this.SET_ROUTES(accessedRoutes)
  }

  @Mutation
  private SET_ROUTES(routes: RouteConfig[]) {
    this.routes = constantRoutes.concat(routes)
    this.dynamicRoutes = routes
  }
}

export const PermissionModule = getModule(Permission)
