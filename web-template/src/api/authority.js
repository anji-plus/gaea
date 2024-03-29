/*
 * @Author: zyk
 * @Date: 2020-07-13 15:14:13
 * @Last Modified by: zyk
 * @Last Modified time: 2021-03-02 12:46:07
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

// 菜单自定义列查询
export function queryMenuExtension(menuCode, params) {
  return request({
    url: `auth/menuextension/queryMenuExtension/${menuCode}`,
    method: 'get',
    params,
  })
} 
// 菜单自定义列查询
export function queryconditionList(data) {
  return request({
    url: `/auth/querycondition/list`,
    method: 'post',
    data,
  })
}
// 新增自定义列
export function addMenuextension(data) {
  return request({
    url: `/auth/menuextension`,
    method: 'post',
    data,
  })
}
// 编辑自定义列
export function editMenuextension(data) {
  return request({
    url: `/auth/menuextension`,
    method: 'put',
    data,
  })
}
// 新增高级搜索
export function addCondition(data) {
  return request({
    url: `/auth/querycondition`,
    method: 'post',
    data,
  })
}
// 编辑高级搜索
export function eidtCondition(data) {
  return request({
    url: `/auth/querycondition`,
    method: 'put',
    data,
  })
}
// 高级搜索删除
export function delCondition(id) {
  return request({
    url: `/auth/querycondition/${id}`,
    method: 'delete',
  })
}
// 自定义列删除
export function delMenuextension(id) {
  return request({
    url: `/auth/menuextension/${id}`,
    method: 'delete',
  })
}
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

// 菜单和按钮关联情况查询
export function queryActionTreeForMenu(code) {
  return request({
    url: `/auth/menu/queryActionTreeForMenu/${code}`,
    method: 'get',
  })
}

// 菜单和按钮关联情况保存
export function saveActionTreeForMenu(data) {
  return request({
    url: '/auth/menu/saveActionTreeForMenu',
    method: 'POST',
    data,
  })
}

// // 用户页面接口

export function getUserList(params) {
  return request({
    url: '/auth/user/pageList',
    method: 'get',
    params,
  })
}
// 新增接口
export function addUser(data) {
  return request({
    // url: '/auth/user/insertUser',
    url: '/auth/user',
    method: 'post',
    data,
  })
}

// 编辑接口
export function editUser(data) {
  return request({
    url: '/auth/user',
    method: 'PUT',
    data,
  })
}
// 删除接口
export function deleteUser(id) {
  return request({
    url: `/auth/user/${id}`,
    method: 'delete',
  })
}
// 获取角色树和被选中的节点
export function getRoleTree(username) {
  return request({
    url: `/auth/user/queryRoleTree/${username}`,
    method: 'get',
  })
}
// 提交选中的菜单节点
export function saveRoleTree(data) {
  return request({
    url: '/auth/user/saveRoleTree',
    method: 'post',
    data,
  })
}

// 提交选中的菜单节点
export function resetPwd(data) {
  return request({
    url: '/auth/user/resetPwd',
    method: 'POST',
    data,
  })
}

// // 权限管理

// 权限配置接口
export function getPermissionList(params) {
  return request({
    url: '/auth/gaeaAuthority/pageList',
    method: 'get',
    params,
  })
}
// 删除接口
export function permissionDelete(id) {
  return request({
    url: `/auth/gaeaAuthority/${id}`,
    method: 'delete',
    params: { noTip: false },
  })
}
// 编辑接口
export function permissionEdit(data) {
  return request({
    url: `/auth/gaeaAuthority/${data.id}`,
    method: 'put',
    data,
  })
}
// 新增接口
export function permissionAdd(data) {
  return request({
    url: `/auth/gaeaAuthority/`,
    method: 'post',
    data,
  })
}

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
export function actionTree(code) {
  return request({
    url: `/auth/menu/tree/actions/${code}`,
    method: 'get',
  })
}

// 新增接口
export function saveMenuTree(data) {
  return request({
    url: '/auth/role/saveMenuActionTreeForRole',
    method: 'post',
    data,
  })
}

// 角色关联组织弹窗查询接口
export function getOrgTree(code) {
  return request({
    url: `/auth/role/queryOrgTreeForRole/${code}`,
    method: 'get',
  })
}
// 提交选中的菜单节点
export function saveOrgTree(data) {
  return request({
    url: '/auth/role/saveOrgTreeForRole',
    method: 'post',
    data,
  })
}

// 角色关联组织弹窗查询接口
export function getAuthTree(orgCode, roleCode) {
  return request({
    url: `/auth/gaeaAuthority/authority/tree/${orgCode}/${roleCode}`,
    method: 'get',
  })
}
// 提交选中的菜单节点
export function saveAuthTree(data) {
  return request({
    url: '/auth/gaeaAuthority/role/authority',
    method: 'post',
    data,
  })
}
