import request from '@/api/axios'
//下载
export function queryByPage(params={}) {
  return request({
    url: '/auth-service/export/queryByPage',
    method: 'post',
    data: params
  })
}
export function downloadFileId(file) {
  return request({
    url: '/auth-service/file/download/' +file.fileId,
    method: 'post',
    data:{'fileTitle':file.fileTitle}
  })
}