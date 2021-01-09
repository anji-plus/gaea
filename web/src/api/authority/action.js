import request from '@/api/axios'

export function create(params) {
  return request({
    url: '/auth-service/action/create',
    method: 'post',
    data: params
  })
}


export function updateById(params) {
  return request({
    url: '/auth-service/action/updateById',
    method: 'post',
    data: params
  })
}

export function createOrUpdate(params) {
  if(params.actionId != null){
    return request({
      url: '/auth-service/action/updateById',
      method: 'post',
      data: params
    })
  }else{
    return request({
      url: '/auth-service/action/create',
      method: 'post',
      data: params
    })
  }
}

export function deleteById(actionId) {
  return request({
    url: '/auth-service/action/deleteById',
    method: 'post',
    data: { 'actionId': actionId }
  })
}

export function queryByPage(params) {
  return request({
    url: '/auth-service/action/queryByPage',
    method: 'post',
    data: params
  })
}

export function queryById(actionId) {
  return request({
    url: '/auth-service/action/queryById',
    method: 'post',
    data: { 'actionId': actionId }
  })
}
