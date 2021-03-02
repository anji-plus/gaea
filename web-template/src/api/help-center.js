import request from '@/utils/request'
// 查询字典
export function queryForCodeSelect(params = {}) {
  return request({
    url: '/business/gaeaDict/select/HELP_CATEGORY',
    method: 'GET',
    data: params,
  })
}

// 查询标题
export function querytitleByCategory(params) {
  return request({
    url: '/business/gaeaHelp/list',
    method: 'GET',
    params,
  })
}

// 关键词查询
export function searchKeyWord(params) {
  return request({
    url: '/business/gaeaHelp/pageList',
    method: 'GET',
    params,
  })
}

export function queryById(params) {
  return request({
    url: `/business/gaeaHelp/${params.id}`,
    method: 'GET',
    params,
  })
}
