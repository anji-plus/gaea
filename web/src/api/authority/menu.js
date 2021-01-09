import request from '@/api/axios'

export function create(params) {
  return request({
    url: '/auth-service/menu/create',
    method: 'post',
    data: params
  })
}


export function updateById(params) {
  return request({
    url: '/auth-service/menu/updateById',
    method: 'post',
    data: params
  })
}

export function createOrUpdate(params) {
  if(params.menuId){
    return request({
      url: '/auth-service/menu/updateById',
      method: 'post',
      data: params
    })
  }else{
    return request({
      url: '/auth-service/menu/create',
      method: 'post',
      data: params
    })
  }
}

export function deleteById(menuId) {
  return request({
    url: '/auth-service/menu/deleteById',
    method: 'post',
    data: { 'menuId': menuId }
  })
}

export function queryByPage(params) {
  return request({
    url: '/auth-service/menu/queryByPage',
    method: 'post',
    data: params
  })
}

export function queryById(menuId) {
  return request({
    url: '/auth-service/menu/queryById',
    method: 'post',
    data: { 'menuId': menuId }
  })
}

export function queryActionTreeForMenu(menuId) {
  return request({
    url: '/auth-service/menu/queryActionTreeForMenu',
    method: 'post',
    data: { 'menuId': menuId }
  })
}

export function saveActionTreeForMenu(params) {
  return request({
    url: '/auth-service/menu/saveActionTreeForMenu',
    method: 'post',
    data: params
  })
}
