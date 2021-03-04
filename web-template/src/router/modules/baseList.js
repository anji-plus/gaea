/*
 * @Author: zyk
 * @Date: 2020-07-13 15:34:51
 * @Last Modified by: zyk
 * @Last Modified time: 2021-03-03 14:44:47
 */

/** 脚手架基础页面路由 **/
export const baseList = [
  // 组织机构
  {
    name: 'org', // 菜单代码 && 路由名称              （必填）
    sort: '10',  // 菜单排序              
    parentCode: '', // 父级菜单代码               （有父级时必填）
    path: '/organization', // 路由                    （必填）
    component: '', // 组件地址从相对view文件夹的路径  (页面级时必填)
    redirect: '/organization/index', // 默认的重定向路径   
    alwaysShow: 0,  // 默认情况下只有一个子级菜单的时候不展示父级菜单
    meta: {                             
      title: '组织机构', // 菜单名 
      icon: 'organization',       // svg图标
    },
    children: [
      {
        sort: '1010',
        path: 'index',
        component: 'organization/index', 
        name: 'organization',
        meta: {                         
          title: '组织机构',
          icon: 'organization',
        }
      },
    ],
  },
  // 权限管理
  {
    sort: '20',
    path: '/authority',
    redirect: '/authority/role',
    name: 'authority',
    alwaysShow: true,
    meta: {
      title: '权限管理',
      icon: 'authority',
    },
    children: [
      // {
      //   sort: '2010',
      //   path: 'btn-config',
      //   component: 'authority/btn-config',
      //   name: 'btnConfig',
      //   meta: {
      //     title: 'btnConfig',
      //     icon: '',
      //   },
      // },
      {
        sort: '2010',
        path: 'permission',
        component: 'authority/permission',
        name: 'permission',
        meta: {
          title: '权限列表',
          icon: '',
        },
      },
      {
        sort: '2020',
        path: 'menu-config',
        component: 'authority/menu-config',
        name: 'menuConfig',
        meta: {
          title: '菜单配置',
          icon: '',
        },
      },
      {
        sort: '2030',
        path: 'menu-detail',
        hidden: 1,
        component: 'authority/menu-detail',
        name: 'menuDetail',
        meta: {
          title: '页面配置',
          icon: '',
        },
      },
      {
        sort: '2040',
        path: 'role',
        component: 'authority/role/index',
        name: 'role',
        meta: {
          title: '角色管理',
          icon: '',
        },
      },
      {
        sort: '2050',
        path: 'user',
        component: 'authority/user/index',
        name: 'user',
        meta: {
          title: '用户管理',
          icon: '',
        },
      },
    ],
  },
  // 系统设置
  {
    sort: '30',
    path: '/system-set',
    redirect: '/system-set/data-dictionary',
    name: 'systemSet',
    alwaysShow: 1,
    meta: {
      title: '系统设置',
      icon: 'systemSet',
    },
    children: [
      // 字典管理
      {
        sort: '3010',
        path: 'data-dictionary',
        component: 'system-set/data-dictionary/index',
        name: 'dataDictionary',
        meta: {
          title: '字典管理',
          icon: '',
        },
      },
      // 参数管理
      {
        sort: '3020',
        path: 'parameter',
        component: 'system-set/parameter/index',
        name: 'parameter',
        meta: {
          title: '参数管理',
          icon: '',
        },
      },
      // 参数管理
      {
        sort: '3030',
        path: '/system-set/parameter/edit',
        // component: () => import('@/views/system-set/parameter/component/edit'),
        component: 'system-set/parameter/component/edit',
        // name: 'parameterEdit',
        hidden: 1,
        meta: { title: 'changePassword', icon: '', noCache: true, breadcrumb: false },
      },
      // 帮助中心
      {
        sort: '3040',
        path: 'support',
        component: 'system-set/support/index',
        name: 'support',
        meta: {
          title: '帮助中心',
          icon: '',
        },
      },
      // 操作日志
      {
        sort: '3050',
        path: 'operation-log',
        component: 'system-set/operation-log',
        name: 'operationLog',
        meta: {
          title: '操作日志',
          icon: '',
        },
      },
    ],
  },
  // 消息管理
  {
    sort: '40',
    path: '/push-notify',
    redirect: '/push-notify/history',
    name: 'pushNotify',
    alwaysShow: 1,
    meta: {
      title: '消息管理',
      icon: 'pushNotify',
    },
    children: [
      // 收发概况
      {
        sort: '4010',
        path: 'situation',
        component: 'push-notify/situation',
        name: 'situation',
        meta: {
          title: '收发概况',
          icon: '',
        },
      },
      // 推送模板
      {
        sort: '4020',
        path: 'template',
        component: 'push-notify/template/index',
        name: 'template',
        meta: {
          title: '推送模板',
          icon: '',
        },
      },
      // 推送历史
      {
        sort: '4030',
        path: 'history',
        component: 'push-notify/history/index',
        name: 'history',
        meta: {
          title: '推送历史',
          icon: '',
        },
      },
    ],
  },
  // 组件中心
  {
    sort: '50',
    path: '/component-center',
    redirect: '/component-center/advanced-list',
    name: 'componentCenter',
    meta: {
      title: '组件中心',
      icon: 'componentCenter',
    },
    alwaysShow: true,
    children: [
      {
        sort: '5010',
        path: 'advanced-list',
        component: 'component-center/advanced-list',
        name: 'advancedList',
        meta: {
          title: '高级查询/动态列',
          icon: '',
        },
      },
      {
        sort: '5020',
        path: 'demo',
        component: 'component-center/demo',
        name: 'demo',
        meta: {
          title: 'CRUD组件化案例',
          icon: '',
        },
      },
    ],
  },
  // 导出中心
  {
    sort: '60',
    path: '/download',
    redirect: '/download/index',
    alwaysShow: 0,
    name:'downloadMenu',
    children: [
      {
        sort: '6010',
        path: 'index',
        component: 'download/index',
        name: 'download',
        meta: {
          title: '导出中心',
          icon: 'download',
        },
      },
    ],
  },
]
