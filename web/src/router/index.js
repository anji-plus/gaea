/*
 * @Author: zyk
 * @Date: 2020-07-10 14:23:29
 * @Last Modified by: zyk
 * @Last Modified time: 2021-01-25 17:35:34
 */
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router 模块 */

/**
 * 路由配置说明
 *
 * hidden: 0                      如果设置为1 / true，项目将不会显示在侧边栏中(默认为0 / false)
 * alwaysShow: 0                  如果设置为1，将始终显示根菜单(默认为0)
 *                                如果没有设置alwaysShow，当项目有多个子路径时，
 *                                它将变成嵌套模式，否则不会显示根菜单
 * redirect: ''                   重定向路径，不设置则不做重定向
 * name:'router-name'             用<keep-alive>组件的时候必须设置
 * meta : {
    title: 'title'               侧边栏显示的名称（国际化时此值是国际化配置文件中的键）
    icon: 'svg-name'             侧边栏菜单的icon图标的名字（svg格式）
    noCache: true                如果设置为true，页面将不会被缓存(默认为false)
    affix: true                  如果设置为true，标签将固定在tags-view视图中
    breadcrumb: false            如果设置为false，项目将隐藏在面包屑中(默认为true)
    activeMenu: '/example/list'  如果设置路径，侧边栏将突出显示您设置的路径
  }
 */

/**
 * 固定的页面路由
 * 不需要任何权限控制的页面
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true,
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true,
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true,
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: 'dashboard', icon: 'home', affix: true },
      },
    ],
  },
  {
    path: '',
    component: Layout,
    children: [
      {
        path: '/change-password',
        component: () => import('@/views/system-set/change-password'),
        name: 'ChangePassword',
        hidden: 1,
        meta: { title: 'changePassword', icon: '', noCache: true, breadcrumb: false },
      },
    ],
  },
]

const createRouter = () =>
  new Router({
    // mode: 'history', // 需要服务端支持
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes,
  })

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // 重置路由
}

export default router
