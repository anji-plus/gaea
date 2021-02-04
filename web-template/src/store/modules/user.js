import { login, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import Cookies from 'js-cookie'
import router, { resetRouter } from '@/router'
var md5 = require('js-md5')

const state = {
  token: getToken(),
  name: '', // 名称，备用
  avatar: '', // 头像，备用
  introduction: '', // 介绍信息，备用
  roles: [], // 如果不需要前端通过role来判断显示哪些菜单，可无视此变量
  hasMenu: false, // 是否已经获取过菜单
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
    setToken(token)
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_HASMENU: (state, hasMenu) => {
    state.hasMenu = hasMenu
  },
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, verifyCode } = userInfo
    var passwordMd5 = md5(password + 'gaea')
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: passwordMd5, verifyCode: verifyCode })
        .then((response) => {
          if (response.code != '200') {
            resolve(response.data) // 将 captcha 传递出去
          } else {
            // 设置token
            commit('SET_TOKEN', response.data)
            // // 将用户名存储到cookie
            // Cookies.set('displayName', response.data.userdetail.displayName)
            // // 将用户id存储到cookie
            // Cookies.set('pkId', response.data.userdetail.userId)
            // resolve(response.data.userdetail)
            resolve()
          }
        })
        .catch((error) => {
          reject(error)
        })
    }).catch((e) => {
      console.log(e)
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token)
        .then((response) => {
          // 此处需要将菜单返回出去
          if (response.code != '200') {
            reject()
          } else {
            commit('SET_HASMENU', true) // 是否获取过菜单标识
            Cookies.set('displayName', response.data.nickname)
            // 将用户id存储到cookie
            // Cookies.set('pkId', response.data.userdetail.userId)
            resolve(response.data.userdetail)
            resolve()
          }
        })
        .catch((error) => {
          reject(error)
        })
    }).catch((e) => {
      console.log(e)
    })
  },

  // user logout
  logout({ commit }) {
    return new Promise((resolve) => {
      commit('SET_TOKEN', '')
      commit('SET_HASMENU', false)
      Cookies.remove('pkId')
      removeToken()
      resetRouter()
      resolve()
    }).catch((e) => {
      console.log(e)
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise((resolve) => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  changeRoles({ commit, dispatch }, role) {
    return new Promise(async(resolve) => {
      const token = role + '-token'

      commit('SET_TOKEN', token)
      setToken(token)

      const { roles } = await dispatch('getInfo')

      resetRouter()

      // generate accessible routes map based on roles
      const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })

      // dynamically add accessible routes
      router.addRoutes(accessRoutes)

      // reset visited views and cached views
      dispatch('tagsView/delAllViews', null, { root: true })

      resolve()
    })
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
}
