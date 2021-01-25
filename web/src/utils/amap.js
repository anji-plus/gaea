/*
 * @Author: zyk
 * @Date: 2020-07-22 10:55:01
 * @Last Modified by: zyk
 * @Last Modified time: 2020-07-22 13:28:10
 */

/**
 * 高德地图引入
 * 需要应用的页面内引入amap.js
 * import amap from '@/utils/amap'
 * amap.then(()=>{
 *   new AMap.Map('container', {})
 * })
 */
import AMapLoader from '@amap/amap-jsapi-loader'
export default AMapLoader.load({
  key: '', // 申请好的Web端开发者Key，首次调用 load 时必填
  version: '1.4.15', // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
  plugins: [], // 插件列表
})
