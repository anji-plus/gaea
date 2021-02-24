/*
 * @Author: zyk
 * @Date: 2020-07-13 15:13:34
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-07 13:18:25
 */

import request from '@/utils/request'
// 推送管理模块内所有页面相关接口

// 推送历史查询
export function gaeaPushHistorySearch(params) {
  return request({
    url: '/business/gaeaPushHistory/pageList',
    method: 'get',
    params,
  })
}

// 推送模版查询
export function gaeaPushTemplateSearch(params) {
  return request({
    url: '/business/gaeaPushTemplate/pageList',
    method: 'get',
    params,
  })
}

// 推送模版新增
export function gaeaPushTemplatePreview(data) {
  return request({
    url: '/business/gaeaPushTemplate/preview',
    method: 'POST',
    data,
  })
}

// 推送模版发送测试
export function gaeaPushTemplateTestSendPush(data) {
  return request({
    url: '/business/gaeaPushTemplate/testSendPush',
    method: 'POST',
    data,
  })
}

// 推送模版预览
export function gaeaPushTemplateAdd(data) {
  return request({
    url: '/business/gaeaPushTemplate',
    method: 'POST',
    data,
  })
}

// 推送模版编辑
export function gaeaPushTemplateEdit(data) {
  return request({
    url: '/business/gaeaPushTemplate',
    method: 'PUT',
    data,
  })
}

// 推送模版查看详情
export function gaeaPushTemplateDetail(id) {
  return request({
    url: `/business/gaeaPushTemplate/${id}`,
    method: 'get',
  })
}

// 推送模版删除
export function gaeaPushTemplateDelect(id) {
  return request({
    url: `/business/gaeaPushTemplate/${id}`,
    method: 'delete',
  })
}
