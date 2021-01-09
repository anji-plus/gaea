import request from '@/api/axios'
import uploadFile from './../upload'

// 增
export function reqCreate(params) {
  return request({
    url: '/auth-service/help/create',
    method: 'post',
    data: params
  })
}

// 更新
export function reqUpdate(params) {
  return request({
    url: '/auth-service/help/updateById',
    method: 'post',
    data: params
  })
}
// 删
export function deleteOne(data) {
  return request({
    url: '/auth-service/help/deleteById',
    method: 'post',
    data
  })
}

// 查
export function queryById(data) {
  return request({
    url: '/auth-service/help/queryById',
    method: 'post',
    data
  })
}

// 列表
export function queryByPage(data) {
  return request({
    url: '/auth-service/help/queryByPage',
    method: 'post',
    data
  })
}

// 上传
export function upload(data) {
  return uploadFile({
    url: '/auth-service/file/upload',
    method: 'post',
    data
  })
}

// 标题检测
export function titleCheck(data) {
  return request({
    url: '/auth-service/help/titleCheck',
    method: 'post',
    data
  })
}