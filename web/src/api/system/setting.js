import request from '@/api/axios'

export function reqCreate(params) {
  return request({
    url: '/auth-service/setting/create',
    method: 'post',
    data: params
  })
}

export function reqUpdate(params) {
  return request({
    url: '/auth-service/setting/updateById',
    method: 'post',
    data: params
  })
}

export function reqDelete(params) {
  return request({
    url: '/auth-service/setting/deleteById',
    method: 'post',
    data: params
  })
}

export function queryById(data) {
  return request({
    url: '/auth-service/setting/queryById',
    method: 'post',
    data
  })
}

export function queryByPage(params) {
  return request({
    url: '/auth-service/setting/queryByPage',
    method: 'post',
    data: params
  })
}

export function queryByParamName(params) {
  return request({
    url: '/auth-service/setting/queryByParamName',
    method: 'post',
    data: params
  })
}

export function reqSwitchEnableById(data) {
  return request({
    url: '/auth-service/setting/switchEnableById',
    method: 'post',
    data
  })
}
