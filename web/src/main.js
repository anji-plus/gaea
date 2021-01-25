/*
 * @Author: zyk
 * @Date: 2020-07-13 15:21:58
 * @Last Modified by: zyk
 * @Last Modified time: 2020-08-20 20:27:41
 */
import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // 各浏览器样式统一化

import Element from 'element-ui'
import './styles/element-variables.scss'

import '@/styles/index.scss' // 全局 css

import App from './App'
import store from './store'
import router from './router'

import i18n from './lang' // 国际化
import './icons' // icon
import './permission' // 权限控制

/**
 * 如果不使用模拟服务器
 * 可用MockJs来模拟api
 * 可以执行:mockXHR()
 *
 * 请在上线前删除!!!
 */
// if (process.env.NODE_ENV === 'development') {
const { mockXHR } = require('../mock')
mockXHR()
// }
/**
 * 后续自行添加的模块
 */
Vue.prototype.$pageSizeAll = [10, 50, 100, 200, 500] // 分页的全局size配置;
// 删除按钮组件
import DeleteBtn from '@/components/BtnCommon/DeleteBtn'
Vue.component('DeleteBtn', DeleteBtn)

import UploadExcel from '@/components/UploadExcel'
Vue.component('UploadExcel', UploadExcel)

Vue.use(Element, {
  size: Cookies.get('size') || 'mini', // 设置 element-ui的默认size为mini
  i18n: (key, value) => i18n.t(key, value),
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: (h) => h(App),
})
