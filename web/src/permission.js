/*
 * @Author: zyk
 * @Date: 2020-07-13 15:34:51
 * @Last Modified by: zyk
 * @Last Modified time: 2021-01-26 16:36:55
 */
import router from './router'
import store from './store'
import { routerList } from '@/router/asyncRouters.js'
import NProgress from 'nprogress' // 进度条
import 'nprogress/nprogress.css' // 进度条 style
import { getToken } from '@/utils/auth' // get token from cookie
import { cloneDeep } from 'loadsh'
// import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // 进度条 Configuration

const whiteList = ['/login'] // 白名单

router.beforeEach(async(to, from, next) => {
  NProgress.start()

  // 根据页面名称设置title名称
  // document.title = getPageTitle(to.meta.title)

  // 用户是否已经登录
  const hasToken = getToken()

  if (hasToken) {
    // 已经登录
    if (to.path === '/login') {
      // 访问/login页面时  直接进主页
      next({ path: '/' })
      NProgress.done()
    } else {
      // 访问其他页面时 先判断是否已经获取过菜单信息
      if (store.getters.hasMenu) {
        // 已获取过菜单信息
        next()
      } else {
        try {
          // 此处相当于拿token获取菜单列表
          // const routerList = await store.dispatch('user/getInfo')
          await store.dispatch('user/getInfo')
          // 处理获取到的菜单数据
          // 解决从登录页重定向到其他页面时再次处理component的值的问题，堆污染（真实场景中从接口中拿数据不会出现此情况）
          // const accessRoutes = await store.dispatch('permission/generateRoutes', JSON.parse(JSON.stringify(routerList)))
          const accessRoutes = await store.dispatch('permission/generateRoutes', cloneDeep(routerList))
          // 把404页面放到所有路由的最后一个防止路由提前被匹配
          accessRoutes.push({ path: '*', redirect: '/404' })
          router.addRoutes(accessRoutes)
          next({ ...to, replace: true })
        } catch (error) {
          // 删除 token 信息 去/login页面
          await store.dispatch('user/logout')
          // Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    // 未登录 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 是否在白名单中
      next()
    } else {
      // 不在白名单直接重定向到/login页面
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // 进度条完成
  NProgress.done()
})
