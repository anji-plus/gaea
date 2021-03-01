/*
 * @Author: zyk
 * @Date: 2020-07-13 15:13:37
 * @Last Modified by: zyk
 * @Last Modified time: 2020-12-15 15:43:30
 */
import request from '@/utils/request'
// 列表模块相关接口 此接口为 mock 数据

export function listData(params) {
  return request({
    url: '/v1/list',
    method: 'get',
    params,
  })
}
