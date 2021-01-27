/*
 * @Author: zyk
 * @Date: 2020-07-13 15:13:22
 * @Last Modified by: zyk
 * @Last Modified time: 2020-12-15 15:35:25
 */
import request from '@/utils/request'
// excel文件导入导出相关接口

/**
 * 导出文件接口请求后的的公用方法
 * res 请求后返回的response
 * fileName 文件名
 */
function downloadBlob(res, fileName) {
  const blob = new Blob([res.data], {
    type: 'application/vnd.ms-excel;charset=utf-8',
  })
  if (window.navigator && window.navigator.msSaveOrOpenBlob) {
    // ie
    navigator.msSaveBlob(blob, fileName || '文件' + '.xlsx')
  } else {
    const link = document.createElement('a')
    // let blob = new Blob([res.data], {type: 'application/vnd.ms-excel'})
    link.style.display = 'none'
    link.href = URL.createObjectURL(blob)
    fileName && (link.download = fileName + '.xlsx') // 下载的文件名
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  }
}

// 导入导出接口地址
const urls = {
  exportApi: '/v1/order/pickupconfig/excel', // 导出地址
  importApi: '/v1/import/demo', // 导入地址
  tempExportApi: '/v1/export/demo', // 模板导出地址
}
const exportFile = (params, url, fileName) => {
  return request({
    url: urls[url],
    method: 'GET',
    responseType: 'blob',
    params,
  }).then((res) => {
    if (!res) {
      return
    }
    downloadBlob(res, fileName)
  })
}
export default {
  urls,
  exportFile,
}
