/*
 * @Author: zyk
 * @Date: 2020-07-22 10:57:57
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-24 16:41:26
 */
import axios from 'axios'
import { Message, Loading } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import i18n from '@/lang'
import router from '@/router'

// 防止重复请求(能解决极端情况)
let pending = [] // 已发出未完成请求队列
const cancelToken = axios.CancelToken
// 移除队列方法
const removePending = (config) => {
  for (const p in pending) {
    const url = config.url + '&' + config.method
    if (url.endsWith(pending[p].u) && JSON.stringify(pending[p].par) == JSON.stringify(config.params) && config.timeParse == pending[p].t) {
      pending[p].f()
      pending.splice(p, 1)
    }
  }
}
// 移除全部请求的方法
const abortPending = () => {
  for (const p in pending) {
    pending[p].f()
  }
  pending = []
}
let n = 0 // 附加标识

// 定义全局loading;
let loadingInstance
// 定义国际化对象
const lang = {
  en: 'en-US;q=0.8',
  zh: 'zh-CN;q=0.8',
}

// token过期失效方法
const tokenLose = () => {
  abortPending()
  Message({
    message: i18n.t(`promptMessage.reload`),
    type: 'error',
  })
  store.dispatch('user/logout').then(() => {
    router.replace(`/login?redirect=${router.currentRoute.fullPath}`)
  })
}

// 创建axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: false, // send cookies when cross-domain requests
  // timeout: 5000 // request timeout
})

// 请求拦截
service.interceptors.request.use(
  (config) => {
    // 请求发送之前做一系列操作
    // 1、给每个请求加标识
    config.timeParse = n++ // 防止两个同样的请求
    removePending(config)
    config.cancelToken = new cancelToken((c) => {
      const url = config.url.replace(config.baseURL, '')
      config.timeParse = n++
      pending.push({
        u: url + '&' + config.method,
        par: config.params,
        f: c,
        t: config.timeParse,
      })
    })
    // 2、给每个请求加上token和语言
    config.headers['Accept-Language'] = lang[i18n.locale]
    store.getters.token && (config.headers['Authorization'] = getToken())
    // 3、loading
    if (!((config.data && config.data.loading == false) || (config.params && config.params.loading == false))) {
      loadingInstance = Loading.service({
        // 全局loading;
        lock: true,
        text: 'loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
        fullscreen: true,
      })
    }
    return config
  },
  (error) => {
    // do something with request error
    loadingInstance && (loadingInstance.close(), (loadingInstance = null))
    return Promise.reject(error)
  }
)

// response 拦截
service.interceptors.response.use(
  (response) => {
    loadingInstance && (loadingInstance.close(), (loadingInstance = null))
    // (loadingInstance && !pending.length) ? (loadingInstance.close(), loadingInstance = null) : null;
    removePending(response.config)
    const res = response.data
    // http状态码
    if (response.status == 200) {
      response.headers.authorization && store.commit('user/SET_TOKEN', response.headers.authorization)
      // 处理流文件类型
      console.log(response)
      if (response.config.responseType === 'blob') {
        // application/vnd.ms-excel
        // application/octet-stream
        if (res.type.indexOf('application') != -1) {
          return response
        } else {
          Message({
            message: i18n.t(`promptMessage.failed`),
            type: 'error',
          })
          return false
        }
      }
      // token过期/失效
      if (res.code == '500-02-0004' || res.code == 'User.credentials.expired') {
        tokenLose()
        return res
      }
      // 不需要统一提示的情况判断
      if (response.config.headers.noPrompt) {
        return res
      }
      // 统一提示
      if (res.code != '200') {
        Message({
          message: res.message || i18n.t(`promptMessage.reqFailed`),
          type: 'error',
        })
      } else if (res.code == '200' && res.message && response.config.method != 'get') {
        Message({
          message: res.message == 'success' ? i18n.t(`promptMessage.success`) : res.message,
          type: 'success',
        })
      }
      return res
    }
  },
  (error) => {
    loadingInstance && (loadingInstance.close(), (loadingInstance = null))

    // 错误信息类型为Error时统一提示，防止请求被取消后 一直弹出提示
    if (Object.prototype.toString.call(error).slice(8, -1) === 'Error') {
      abortPending()
      // if (error.response && error.response.status === 500) {
      //   tokenLose()
      //   return
      // }
      Message({
        message: i18n.t(`promptMessage.netErr`),
        type: 'error',
      })
    }
    return Promise.reject(error)
  }
)

export default service
