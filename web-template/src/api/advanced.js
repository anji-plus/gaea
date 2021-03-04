import request from '@/utils/request'

// 自定义列列头查询 {menuCode,tableCode}
export function queryTableColumn(params) {
  return request({
    url: '/auth/userextension/userMenuExtensions',
    method: 'get',
    params,
  })
}

// 高级搜索查询
export function queryconditionList(data) {
  return request({
    url: `/auth/querycondition/list`,
    method: 'post',
    data,
  })
}

// 数据列表页
export function queryAdvanceExport(data) {
  return request({
    url: `/business/export/queryAdvanceExport`,
    method: 'post',
    data,
  })
}

// 用户自定义列保存
export function saveUserMenuExtensionsBatchSave(data) {
  return request({
    url: `/auth/userextension/userMenuExtensionsBatchSave`,
    method: 'post',
    data,
  })
}

// 高级搜索字典查询
export function queryDictSelect({ url, params }) {
  return request({
    url: url,
    method: 'get',
    params,
  })
}

// 常用查询id
export function queryCommonCondition(data) {
  return request({
    url: `/auth/commonCondition/queryByCondition`,
    method: 'post',
    data,
  })
}
// 常用查询保存
export function saveCommonCondition(data) {
  return request({
    url: `/auth/commonCondition/saveCondition`,
    method: 'post',
    data,
  })
}
// 常用删除
export function deleteCommonCondition(id) {
  return request({
    url: `/auth/commonCondition/${id}`,
    method: 'delete',
  })
}
    
