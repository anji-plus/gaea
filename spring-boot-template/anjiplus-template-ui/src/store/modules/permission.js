import { constantRoutes } from '@/router'
import Layout from '@/layout'
import RenderView from '@/layout/RenderView'

// 加载页面组件
export function importMethod(file) {
  if (!file) return ''
  try {
    return require('@/views/' + file + '.vue').default
  } catch (e) {
    console.log(e)
  }
}
// 处理动态路由的component
export function changeAsyncRoutes(routes) {
  const res = []
  routes.forEach((route) => {
    const tmp = { ...route }
    if (tmp.children && tmp.children.length !== 0) {
      // // 有子路由的情况 动态创建router-view
      // tmp.component = {
      //   render: (c) => c('router-view'),
      // }

      // 没有三级路由或者不用keep-alive组件的时候可以用上边的方法

      // 解决三级及以上级别路由无法被keep-alive组件缓存的问题
      tmp.component = RenderView

      tmp.children = changeAsyncRoutes(tmp.children)
    } else {
      // 没有子路由，直接处理当前路由的component组件
      tmp.meta.permission = tmp.permission || []
      tmp.component = importMethod(tmp.component)
    }
    res.push(tmp)
  })
  return res
}
const state = {
  routes: [],
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.routes = constantRoutes.concat(routes)
  },
}

const actions = {
  generateRoutes({ commit }, routeList) {
    return new Promise((resolve) => {
      routeList.forEach((items) => {
        // 将动态的路由数据 的第一层 的component全部设置未layout组件
        items.component = Layout
        // 处理动态路由的component内容
        items.children = changeAsyncRoutes(items.children)
      })
      commit('SET_ROUTES', routeList)
      resolve(routeList)
    })
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
}
