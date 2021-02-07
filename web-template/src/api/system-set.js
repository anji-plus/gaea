/*
 * @Author: zyk
 * @Date: 2020-07-13 15:13:34
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-07 13:18:25
 */

import request from '@/utils/request'
// 系统设置模块内所有页面相关接口

// 字典类型下拉接口
export function dictionaryType() {
  return request({
    url: '/v1/dict/man/types',
    method: 'get',
  })
}
// 字典列表接口
export function dictionaryCodesQuery(params) {
  return request({
    url: '/v1/dict/man/codes',
    method: 'get',
    params,
  })
}
// 字典删除接口
export function dictionaryDelete(pkId) {
  return request({
    url: `/v1/dict/man/codes/${pkId}`,
    method: 'delete',
  })
}
// 字典编辑接口
export function dictionaryEdit(data) {
  return request({
    url: `/v1/dict/man/codes/${data.pkId}`,
    method: 'put',
    data,
  })
}
// 字典新增接口
export function dictionaryAdd(data) {
  return request({
    url: `/v1/dict/man/codes`,
    method: 'post',
    data,
  })
}

// 修改用户密码
export function changePassword(data) {
  return request({
    url: '/auth/user/updatePassword',
    method: 'POST',
    data,
  })
}

// 操作日志
export function log(params) {
  return request({
    url: `/v1/login/log`,
    method: 'get',
    params,
  })
}
