import request from '@/api/axios'

// 增
export function reqCreate(params) {
  return request({
    url: '/auth-service/dict/create',
    method: 'post',
    data: params
  })
}

// 更新
export function reqUpdate(params) {
  return request({
    url: '/auth-service/dict/updateById',
    method: 'post',
    data: params
  })
}
// 删
export function deleteOne(data) {
  return request({
    url: '/auth-service/dict/deleteById',
    method: 'post',
    data
  })
}
// 查
export function queryById(data) {
  return request({
    url: '/auth-service/dict/queryById',
    method: 'post',
    data
  })
}

// 列表
export function queryByPage(params) {
  return request({
    url: '/auth-service/dict/queryByPage',
    method: 'post',
    data: params
  })
}

export function queryForCodeSelect(params={}) {
  return request({
    url: '/auth-service/dict/queryForCodeSelect',
    method: 'post',
    data: params
  })
}