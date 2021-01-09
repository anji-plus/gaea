import request from '@/api/axios'
// 列表
export function queryByPage(params) {
  return request({
    url: '/auth-service/log/queryByPage',
    method: 'post',
    data: params
  })
}
