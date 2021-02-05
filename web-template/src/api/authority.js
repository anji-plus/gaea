/*
 * @Author: zyk
 * @Date: 2020-07-13 15:14:13
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-04 15:32:50
 */
import request from '@/utils/request'
// 权限菜单中几个页面的接口

// 列表查询接口
export function getBtnList(params) {
  return request({
    url: '/auth/action/pageList',
    method: 'get',
    params,
  })
}
// 新增接口
export function addBtn(data) {
  return request({
    url: '/auth/action',
    method: 'post',
    data,
  })
}

// 编辑接口
export function editBtn(data) {
  return request({
    url: '/auth/action',
    method: 'PUT',
    data,
  })
}
// 删除接口
export function deleteBtn(id) {
  return request({
    url: `/auth/action/${id}`,
    method: 'delete',
  })
}

// 菜单配置页面接口

// 列表查询接口
export function getMenuList(params) {
  return request({
    url: '/auth/menu/pageList',
    method: 'get',
    params,
  })
}
// 新增接口
export function addMenu(data) {
  return request({
    url: '/auth/menu',
    method: 'post',
    data,
  })
}
// 编辑接口
export function editMenu(data) {
  return request({
    url: '/auth/menu',
    method: 'PUT',
    data,
  })
}
// 删除接口
export function deleteMenu(id) {
  return request({
    url: `/auth/menu/${id}`,
    method: 'delete',
  })
}

// 菜单按钮关联情况查询
export function queryActionTreeForMenu(code) {
  return request({
    url: `/auth/menu/queryActionTreeForMenu/${code}`,
    method: 'get',
  })
}

// 菜单按钮关联情况查询
export function saveActionTreeForMenu(data) {
  return request({
    url: '/auth/menu/saveActionTreeForMenu',
    method: 'POST',
    data,
  })
}

// // 用户列表接口
// export function userQuery(params) {
//   return request({
//     url: '/v1/user',
//     method: 'get',
//     params,
//   })
// }
// // 用户删除接口
// export function userDelete(pkId) {
//   return request({
//     url: `/v1/user/${pkId}`,
//     method: 'delete',
//   })
// }
// // 用户编辑
// export function userEdit(data) {
//   return request({
//     url: `/v1/user/${data.pkId}`,
//     method: 'put',
//     data,
//   })
// }
// // 用户新增接口
// export function userAdd(data) {
//   return request({
//     url: '/v1/user',
//     method: 'post',
//     data,
//   })
// }
// // 重置密码接口
// export function resetPassword(pkId) {
//   return request({
//     url: `/v1/user/${pkId}/password/reset`,
//     method: 'put',
//   })
// }

// // 权限管理

// // 权限列表接口
// export function getPermissionList(params) {
//   return request({
//     url: '/v1/permission',
//     method: 'get',
//     params,
//   })
// }
// // 删除接口
// export function permissionDelete(pkId) {
//   return request({
//     url: `/v1/permission/${pkId}`,
//     method: 'delete',
//     params: { noTip: false },
//   })
// }
// // 编辑接口
// export function permissionEdit(data) {
//   return request({
//     url: `/v1/permission/${data.pkId}`,
//     method: 'put',
//     data,
//   })
// }
// // 新增接口
// export function permissionAdd(data) {
//   return request({
//     url: `/v1/permission`,
//     method: 'post',
//     data,
//   })
// }

// // 角色管理

export function getRoleList(params) {
  return request({
    url: '/auth/role/pageList',
    method: 'get',
    params,
  })
}
// 新增接口
export function addRole(data) {
  return request({
    url: '/auth/role',
    method: 'post',
    data,
  })
}

// 编辑接口
export function editRole(data) {
  return request({
    url: '/auth/role',
    method: 'PUT',
    data,
  })
}
// 删除接口
export function deleteRole(id) {
  return request({
    url: `/auth/role/${id}`,
    method: 'delete',
  })
}

// 获取全部菜单
export function allMenu() {
  return request({
    url: `/auth/menu/tree`,
    method: 'get',
  })
}
// 获取选中的菜单
export function actionTree() {
  return request({
    url: `/auth/menu/tree/actions`,
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
