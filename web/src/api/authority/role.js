import request from '@/api/axios'

export function create(params) {
  return request({
    url: '/auth-service/role/create',
    method: 'post',
    data: params
  })
}

export function updateById(params) {
  return request({
    url: '/auth-service/role/updateById',
    method: 'post',
    data: params
  })
}

export function createOrUpdate(params) {
  if(params.roleId){
    return request({
      url: '/auth-service/role/updateById',
      method: 'post',
      data: params
    })
  }else{
    return request({
      url: '/auth-service/role/create',
      method: 'post',
      data: params
    })
  }
}

export function deleteById(roleId) {
  return request({
    url: '/auth-service/role/deleteById',
    method: 'post',
    data: { 'roleId': roleId }
  })
}

export function queryByPage(params) {
  return request({
    url: '/auth-service/role/queryByPage',
    method: 'post',
    data: params
  })
}

export function queryById(roleId) {
  return request({
    url: '/auth-service/role/queryById',
    method: 'post',
    data: { 'roleId': roleId }
  })
}

export function queryOrgTreeForRole(roleId) {
  return request({
    url: '/auth-service/role/queryOrgTreeForRole',
    method: 'post',
    data: { 'roleId': roleId }
  })
}

// 设定角色组织用户
export function queryUserOrgTreeForRole(roleId) {
  return request({
    url: '/auth-service/role/queryUserOrgTreeForRole',
    method: 'post',
    data: { 'roleId': roleId }
  })
}

export function saveOrgTreeForRole(params) {
  return request({
    url: '/auth-service/role/saveOrgTreeForRole',
    method: 'post',
    data: params
  })
}

export function queryMenuActionTreeForRole(roleId) {
  return request({
    url: '/auth-service/role/queryMenuActionTreeForRole',
    method: 'post',
    data: { 'roleId': roleId }
  })
}

export function saveMenuActionTreeForRole(params) {
  return request({
    url: '/auth-service/role/saveMenuActionTreeForRole',
    method: 'post',
    data: params
  })
}

// 设定角色用户组织保存
export function saveOrgTreeForUserRole(params) {
  return request({
    url: '/auth-service/role/saveOrgTreeForUserRole',
    method: 'post',
    data: params
  })
}
