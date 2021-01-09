import request from '@/api/axios'

export function create(params) {
  return request({
    url: '/auth-service/org/create',
    method: 'post',
    data: params
  })
}


export function updateById(params) {
  return request({
    url: '/auth-service/org/updateById',
    method: 'post',
    data: params
  })
}


export function createOrUpdate(params) {
  if(params.orgId != null){
    return request({
      url: '/auth-service/org/updateById',
      method: 'post',
      data: params
    })
  }else{
    return request({
      url: '/auth-service/org/create',
      method: 'post',
      data: params
    })
  }
}


export function deleteById(orgId) {
  return request({
    url: '/auth-service/org/deleteById',
    method: 'post',
    data: { 'orgId': orgId }
  })
}

export function queryByPage(params) {
  return request({
    url: '/auth-service/org/queryByPage',
    method: 'post',
    data: params
  })
}

export function selectOption(params) {
  return request({
    url: '/auth-service/org/selectOption',
    method: 'post',
    data: params
  })
}

export function queryById(orgId) {
  return request({
    url: '/auth-service/org/queryById',
    method: 'post',
    data: { 'orgId': orgId }
  })
}

export function queryRoleTree(orgId) {
  return request({
    url: '/auth-service/org/queryRoleTree',
    method: 'post',
    data: { 'orgId': orgId }
  })
}

export function saveRoleTree(params) {
  return request({
    url: '/auth-service/org/saveRoleTree',
    method: 'post',
    data: params
  })
}
