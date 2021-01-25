/*
 * @Author: zyk
 * @Date: 2020-07-13 15:34:51
 * @Last Modified by: zyk
 * @Last Modified time: 2020-12-25 15:35:07
 */
import { baseList } from './modules/baseList'
import { webList } from './modules/webList'
// 模拟从后端获取的动态路由
export const routerList = [...baseList, ...webList]
