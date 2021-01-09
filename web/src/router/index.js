import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    AuthKey: 'roleManage:find',      该页面进入的权限码
    keepAlive: true,                  该页面需要缓存(注意: name值必须设置 与组件内部name 一致 配合isback属性)
    isback :  false
  }
* AuthKey: 'roleManage:find'      该页面进入的权限码
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login'), hidden: true },
  { path: '/helpCenList', component: () => import('@/views/helpCenList/list'), hidden: true,
    children: [
      {path: 'list', component: () => import('@/views/helpCenList/list-title'), hidden: true},
      {path: 'detail', component: () => import('@/views/helpCenList/list-detail'), hidden: true},
      {path: 'search', component: () => import('@/views/helpCenList/list-search'), hidden: true}
    ]
  },
  {
    path: '/index',
    component: Layout,
    name: '首页',
    meta: { title: '首页', icon: 'iconhome2' },
    children: [
      { path: '', name: 'accessUser',  component: () => import('@/views/index'), meta: { title: '首页', requireAuth: true, icon: 'iconhome2',AuthKey:'overviewManage:find',keepAlive: true ,isBack: true } },
    ]
  },
  {
    path: '/organization',
    component: Layout,
    redirect: '/organization/list',
    name: '组织',
    meta: { title: '组织机构', icon: 'iconquanxian' },
    AuthKey:'orgManage:find',
    children: [
      ///views/organization/listTree是父子级结构
      { path: 'list', name:'organizationList' , component: () => import('@/views/organization/list'), meta: { title: '组织机构', requireAuth: true,  icon: 'iconzuzhijigou', AuthKey:'orgManage:find',keepAlive: true ,isBack: true} },
      { path: 'edit', name:'organizationEdit',component: () => import('@/views/organization/edit'), hidden: true },
    ]
  },{
    path: '/authority',
    component: Layout,
    redirect: '/authority/accessUser',
    name: '权限',
    AuthKey:'actionManage:find | menuManage:find | roleManage:find | userManage:find',
    meta: { title: '权限管理', icon: 'iconquanxian' },
    children: [
      { path: 'action/list',name:'actionList',component: () => import('@/views/authority/action/list'), meta: { title: '按钮管理', requireAuth: true,  icon: 'iconanniu',AuthKey:'actionManage:find', keepAlive: true,isBack: true } },
      { path: 'action/edit',name:'actionEdit',component: () => import('@/views/authority/action/edit'), hidden: true },
      { path: 'menu/list', name:'menuList', component: () => import('@/views/authority/menu/list'), meta: { title: '菜单管理', requireAuth: true,  icon: 'iconcaidan2',AuthKey:'menuManage:find', keepAlive: true,isBack: true }  },
      { path: 'menu/edit', name:'menuEdit', component: () => import('@/views/authority/menu/edit'), hidden: true },
      { path: 'role/list', name:'roleList', component: () => import('@/views/authority/role/list'), meta: { title: '角色管理', requireAuth: true,  icon: 'iconjiaose1',AuthKey:'roleManage:find', keepAlive: true,isBack: true } },
      { path: 'role/edit', name:'roleEdit', component: () => import('@/views/authority/role/edit'), hidden: true },
      { path: 'user/list', name:'userList', component: () => import('@/views/authority/user/list'), meta: { title: '用户管理', requireAuth: true,  icon: 'iconyonghu',AuthKey:'userManage:find', keepAlive: true ,isBack: true}},
      { path: 'user/edit', name:'userEdit', component: () => import('@/views/authority/user/edit'), hidden: true },
    ]
  },{
    path: '/system',
    component: Layout,
    redirect: '/dict/list',
    name: '系统',
    AuthKey:'dictManage:find | settingManage:find | helpManage:find | logManage:find',
    meta: { title: '系统设置', icon: 'iconzidian' },
    children: [
      { path: 'dict/list', name:'dictList',component: () => import('@/views/system/dict/list'), meta: { title: '字典管理', requireAuth: true,  icon: 'iconzidianguanli', AuthKey:'dictManage:find' } },
      { path: 'dict/edit', name:'dictEdit',component: () => import('@/views/system/dict/edit'), hidden: true },
      { path: 'setting/list', name:'settingList',component: () => import('@/views/system/setting/list'), meta: { title: '参数管理', requireAuth: true,  icon: 'iconnavicon-ywcs', AuthKey:'settingManage:find' } },
      { path: 'helpCenter/list', name:'helpCenter',component: () => import('@/views/system/helpCenter/list'), meta: { title: '帮助中心', requireAuth: true,  icon: 'iconnavicon-ywcs', AuthKey:'helpManage:find' } },
      { path: 'helpCenter/edit', name:'helpCenterEdit',component: () => import('@/views/system/helpCenter/edit'), hidden: true },
      { path: 'setting/edit', name:'settingEdit',component: () => import('@/views/system/setting/edit'), hidden: true },
      { path: 'operateLog/list', name:'logList',component: () => import('@/views/system/operateLog/list'), meta: { title: '操作日志', requireAuth: true,  icon: 'iconnavicon-ywcs', AuthKey:'logManage:find' } },
    ]
  },{
    path: '/notify',
    component: Layout,
    redirect: '/push/chart',
    name: '推送',
    AuthKey:'statisticsManage:find | templateManage:find | pushHistoryManage:find',
    meta: { title: '推送管理', icon: 'iconduanxin' },
    children: [
      { path: 'push/chart', name:'notifyChart',component: () => import('@/views/push/chart'), meta: { title: '收发概况', requireAuth: true,  icon: 'icontubiao', AuthKey:'statisticsManage:find'  } },
      { path: 'push/template', name:'templateList',component: () => import('@/views/push/template/list'), meta: { title: '推送模板', requireAuth: true,  icon: 'iconfenxiang2', AuthKey:'templateManage:find' } },
      { path: 'push/history', name:'historyList',component: () => import('@/views/push/history/list'), meta: { title: '推送历史', requireAuth: true,  icon: 'icondirectmailyoujiantuisong',AuthKey:'pushHistoryManage:find' } }, 
      { path: 'push/tutorial', name:'notifyMailTutorial',component: () => import('@/views/push/template/Tutorial'), hidden: true },
    ]
  },{
    path: '/download',
    component: Layout,
    redirect: '/download/list',
    name: '导出中心',
    AuthKey:'exportManage:find',
    meta: { title: '导出中心', icon: 'icon11-04' },
    children: [
      { path: 'list',name:'alarmEvent', component: () => import('@/views/download/list'), meta: { title: '导出中心', requireAuth: true,  icon: 'icon11-04',AuthKey:'exportManage:find', } },
    ]
  },
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  { path: '*', redirect: '/index', hidden: true },
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})