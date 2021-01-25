/*
 * @Author: zyk
 * @Date: 2020-07-13 15:14:13
 * @Last Modified by: zyk
 * @Last Modified time: 2020-12-15 15:33:10
 */
import request from '@/utils/request'
// 用户管理模块所有页面相关接口

// 用户列表接口
export function userQuery(params) {
  return request({
    url: '/v1/user',
    method: 'get',
    params,
  })
}
// 用户删除接口
export function userDelete(pkId) {
  return request({
    url: `/v1/user/${pkId}`,
    method: 'delete',
  })
}
// 用户编辑
export function userEdit(data) {
  return request({
    url: `/v1/user/${data.pkId}`,
    method: 'put',
    data,
  })
}
// 用户新增接口
export function userAdd(data) {
  return request({
    url: '/v1/user',
    method: 'post',
    data,
  })
}
// 重置密码接口
export function resetPassword(pkId) {
  return request({
    url: `/v1/user/${pkId}/password/reset`,
    method: 'put',
  })
}

// 修改用户密码
export function changePassword(data) {
  return request({
    url: `/v1/user/password`,
    // url: `/v1/user/${data.pkId}/password`,
    method: 'put',
    data,
  })
}

// 权限管理

// 权限列表接口
export function getPermissionList(params) {
  return request({
    url: '/v1/permission',
    method: 'get',
    params,
  })
}
// 删除接口
export function permissionDelete(pkId) {
  return request({
    url: `/v1/permission/${pkId}`,
    method: 'delete',
    params: { noTip: false },
  })
}
// 编辑接口
export function permissionEdit(data) {
  return request({
    url: `/v1/permission/${data.pkId}`,
    method: 'put',
    data,
  })
}
// 新增接口
export function permissionAdd(data) {
  return request({
    url: `/v1/permission`,
    method: 'post',
    data,
  })
}

// 角色管理

// 列表接口
export function roleList(params) {
  return request({
    url: '/v1/role',
    method: 'get',
    params,
  })
}
// 删除接口
export function roleDelete(pkId) {
  return request({
    url: `/v1/role/${pkId}`,
    method: 'delete',
    params: { noTip: false },
  })
}
// 编辑接口
export function roleEdit(data) {
  return request({
    url: `/v1/role/${data.pkId}`,
    method: 'put',
    data,
  })
}
// 新增接口
export function roleAdd(data) {
  return request({
    url: `/v1/role`,
    method: 'post',
    data,
  })
}
// 获取全部菜单
export function allMenu() {
  return request({
    url: `/v1/menu/all`,
    method: 'get',
  })
}

// 登录日志
export function loginLog(params) {
  return request({
    url: `/v1/login/log`,
    method: 'get',
    params,
  })
}
