/*
 * @Author: zyk
 * @Date: 2021-02-05 14:01:13
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-05 14:43:07
 */
import request from '@/utils/request'
// 权限菜单中几个页面的接口

// 列表查询接口
export function getOrgList(params) {
  return request({
    url: '/auth/org/pageList',
    method: 'get',
    params,
  })
}
// 新增接口
export function addOrg(data) {
  return request({
    url: '/auth/org',
    method: 'post',
    data,
  })
}

// 编辑接口
export function editOrg(data) {
  return request({
    url: '/auth/org',
    method: 'PUT',
    data,
  })
}
// 删除接口
export function deleteOrg(id) {
  return request({
    url: `/auth/org/${id}`,
    method: 'delete',
  })
}

// 查询上级代码接口
export function getParentOrgList() {
  return request({
    url: '/auth/org/queryAllOrg',
    method: 'get',
  })
}
