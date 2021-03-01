/*
 * @Author: zyk
 * @Date: 2020-07-13 15:13:37
 * @Last Modified by: zyk
 * @Last Modified time: 2021-03-01 10:22:59
 */
import request from '@/utils/request'
// 登录、登出、获取菜单信息模块 独立存在于其他模块的请求之外

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data,
  })
}

export function getInfo(orgCode) {
  return request({
    url: `/auth/menu/menuUserInfoByOrg`,
    // url: `/auth/menu/userInfo`,
    headers: { noPrompt: true }, // 不要提示
    method: 'POST',
    data: { orgCode: '' },
  })
}

// export function logout() {
//   return request({
//     url: '',
//     method: 'post'
//   })
// }
