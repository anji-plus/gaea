/*
 * @Author: zyk
 * @Date: 2020-07-13 15:13:34
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-07 13:18:25
 */

import request from '@/utils/request'

// 数据字典接口
export function dataDictionary(dictName) {
  return request({
    url: `/business/gaeaDict/select/${dictName}`,
    method: 'get',
  })
}

// 图片上传接口
export function uploadImg(data) {
  return request({
    url: '/business/file/upload',
    method: 'POST',
    data,
  })
}
