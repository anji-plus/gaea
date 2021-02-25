/*
 * @Author: zyk
 * @Date: 2021-02-23 15:13:17
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-24 17:04:15
 */
import request from '@/utils/request'
// 导出中心

export function getList(params) {
  return request({
    url: '/business/export/pageList',
    // headers: { noPrompt:true },
    method: 'get',
    params,
  })
}
export function download(fileId) {
  return request({
    url: `/business/file/download/${fileId}`,
    responseType: 'blob',
    method: 'get',
  })
}
