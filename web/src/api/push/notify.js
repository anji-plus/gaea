import request from '@/api/axios'

//模板历史根据 邮件 短信 钉钉查询分页接口
export function queryPushHistoryByPage(data) {
  return request({
    url: '/push-service/pushHistory/queryByPage',
    method: 'post',
    data
  })
}
//模板历史根据 邮件 短信 钉钉ID查询接口
export function queryPushHistoryById(data) {
  return request({
    url: '/push-service/pushHistory/queryById',
    method: 'post',
    data
  })
}
//获取短信模板分页查询
export function queryTemplateList(data) {
  return request({
    url: '/push-service/template/queryByPage',
    method: 'post',
    data
  })
}
//短信历史table接口
export function querySmsByPage(data) {
  return request({
    url: '/push-service/smsHistory/queryByPage',
    method: 'post',
    data
  })
} 

// 短信邮件钉钉 图表展示
export function getPushStatistics(data){
  return request({
    url: '/push-service/statistics/getPushStatistics',
    method: 'post',
    data
  })
}
//测试短信发送接口
export function testSendSms(data) {
  return request({
    url: '/push-service/template/testSendSms',
    method: 'post',
    data
  })
}
//邮件历史根据id查询
export function queryMailById(data) {
  return request({
    url: '/push-service/mailHistory/queryById',
    method: 'post',
    data
  })
}
//删除模板
export function deleteTemplate(data) {
  return request({
    url: '/push-service/template/deleteById',
    method: 'post',
    data
  })
}
//更新模板
export function updateTemplate(data) {
  return request({
    url: '/push-service/template/updateById',
    method: 'post',
    data
  })
}
//预览
export function preview(data) {
  return request({
    url: '/push-service/template/preview',
    method: 'post',
    data
  })
}
//保存模板
export function saveTemplate(data) {
  return request({
    url: '/push-service/template/create',
    method: 'post',
    data
  })
}
//根据模板id，获取模板信息
export function showTemplateById(data) {
  return request({
    url: '/push-service/template/queryById',
    method: 'post',
    data
  })
}
//短信模板id详情
export function templateQueryById(data) {
  return request({
    url: '/push-service/template/queryById',
    method: 'post',
    data
  })
}
//邮件发送测试
export function testSendMail(data) {
  return request({
    url:'/push-service/template/testSendPush',
    method:'post',
    data
  })
}
