/*
 * @Author: zyk
 * @Date: 2020-07-13 15:13:37
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-04 14:48:29
 */
import request from '@/utils/request'
// 登录、登出、获取菜单信息模块 独立存在于其他模块的请求之外

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    // headers: {
    //   'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    // },
    data,
    // transformRequest: [
    //   function(data) {
    //     data = Qs.stringify(data)
    //     return data
    //   }
    // ]
  })
}

export function getInfo(token) {
  return request({
    url: `/auth/menu/userInfo`,
    method: 'get',
  })
}

// export function logout() {
//   return request({
//     url: '/vue-element-admin/user/logout',
//     method: 'post'
//   })
// }
