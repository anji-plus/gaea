/*
 * @Author: zyk
 * @Date: 2020-07-13 11:00:35
 * @Last Modified by: zyk
 * @Last Modified time: 2020-07-22 13:34:09
 *
 * 后期做前端错误监控再完善该模块内容，目前预留
 */
import Vue from 'vue'
import store from '@/store'
import { isString, isArray } from '@/utils/validate'
import settings from '@/settings'

// 可以在settings.js中设置
// errorLog:'production' | ['production', 'development']
const { errorLog: needErrorLog } = settings

function checkNeed() {
  const env = process.env.NODE_ENV
  if (isString(needErrorLog)) {
    return env === needErrorLog
  }
  if (isArray(needErrorLog)) {
    return needErrorLog.includes(env)
  }
  return false
}

if (checkNeed()) {
  Vue.config.errorHandler = function(err, vm, info) {
    Vue.nextTick(() => {
      store.dispatch('errorLog/addErrorLog', {
        err,
        vm,
        info,
        url: window.location.href,
      })
      console.error(err, info)
    })
  }
}
