import request from '@/api/axios'

export function create(params) {
  return request({
    url: '/auth-service/user/create',
    method: 'post',
    data: params
  })
}


export function updateById(params) {
  return request({
    url: '/auth-service/user/updateById',
    method: 'post',
    data: params
  })
}

export function createOrUpdate(params) {
  if(params.actionId != null){
    return request({
      url: '/auth-service/user/updateById',
      method: 'post',
      data: params
    })
  }else{
    return request({
      url: '/auth-service/user/create',
      method: 'post',
      data: params
    })
  }
}

export function deleteById(userId) {
  return request({
    url: '/auth-service/user/deleteById',
    method: 'post',
    data: { 'userId': userId }
  })
}

export function queryByPage(params) {
  return request({
    url: '/auth-service/user/queryByPage',
    method: 'post',
    data: params
  })
}

export function queryById(userId) {
  return request({
    url: '/auth-service/user/queryById',
    method: 'post',
    data: { 'userId': userId }
  })
}

export function queryRoleTree(params) {
  return request({
    url: '/auth-service/user/queryRoleTree',
    method: 'post',
    data:params
  })
}

export function saveRoleTree(params) {
  return request({
    url: '/auth-service/user/saveRoleTree',
    method: 'post',
    data: params
  })
}
export function selectOption(params){
  return request({
    url:'/auth-service/user/selectOption',
    method: 'post',
    data: params
  })
}