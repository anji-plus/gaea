/*
 * @Author: zyk
 * @Date: 2020-07-13 15:34:51
 * @Last Modified by: zyk
 * @Last Modified time: 2021-02-04 15:17:34
 */

/** 脚手架基础页面路由 **/
export const baseList = [
  // 组织机构
  {
    menuId: '10',
    parentMenuId: '',
    path: '/organization',
    redirect: '/organization/index',
    alwaysShow: 0,
    children: [
      {
        menuId: '1010',
        parentMenuId: '10',
        path: 'index',
        component: 'organization/index',
        name: 'Organization',
        meta: {
          title: 'organization',
          icon: 'organization',
        },
        permission: [
          {
            btnId: '10-add',
            btnName: 'add',
          },
          {
            btnId: '10-edit',
            btnName: 'edit',
          },
        ],
      },
    ],
  },
  // 权限管理
  {
    menuId: '20',
    parentMenuId: '',
    path: '/authority',
    redirect: '/authority/role',
    name: 'Authority',
    alwaysShow: true,
    meta: {
      title: 'authority',
      icon: 'authority',
    },
    children: [
      {
        menuId: '2010',
        parentMenuId: '20',
        path: 'btn-config',
        component: 'authority/btn-config',
        name: 'BtnConfig',
        meta: {
          title: 'btnConfig',
          icon: '',
        },
      },
      {
        menuId: '2020',
        parentMenuId: '20',
        path: 'menu-config',
        component: 'authority/menu-config',
        name: 'MenuConfig',
        meta: {
          title: 'menuConfig',
          icon: '',
        },
      },
      {
        menuId: '2030',
        parentMenuId: '20',
        path: 'role',
        component: 'authority/role',
        name: 'Role',
        meta: {
          title: 'role',
          icon: '',
        },
      },
      {
        menuId: '2040',
        parentMenuId: '20',
        path: 'user',
        component: 'authority/user',
        name: 'User',
        meta: {
          title: 'user',
          icon: '',
        },
      },
      // {
      //   menuId: '2060',
      //   parentMenuId: '20',
      //   path: 'permission',
      //   component: 'authority/permission',
      //   name: 'Permission',
      //   meta: {
      //     title: 'permission',
      //     icon: '',
      //   },
      // },
    ],
  },
  // 系统设置
  {
    menuId: '30',
    parentMenuId: '',
    path: '/system-set',
    redirect: '/system-set/data-dictionary',
    name: 'SystemSet',
    alwaysShow: 1,
    meta: {
      title: 'systemSet',
      icon: 'systemSet',
    },
    children: [
      // 字典管理
      {
        menuId: '3010',
        parentMenuId: '30',
        path: 'data-dictionary',
        component: 'system-set/data-dictionary',
        name: 'DataDictionary',
        meta: {
          title: 'dataDictionary',
          icon: '',
        },
      },
      // 参数管理
      {
        menuId: '3020',
        parentMenuId: '30',
        path: 'parameter',
        component: 'system-set/parameter',
        name: 'Parameter',
        meta: {
          title: 'parameter',
          icon: '',
        },
      },
      // 帮助中心
      {
        menuId: '3030',
        parentMenuId: '30',
        path: 'support',
        component: 'system-set/support',
        name: 'Support',
        meta: {
          title: 'support',
          icon: '',
        },
      },
      // 操作日志
      {
        menuId: '3040',
        parentMenuId: '30',
        path: 'operation-log',
        component: 'system-set/operation-log',
        name: 'OperationLog',
        meta: {
          title: 'operationLog',
          icon: '',
        },
      },
    ],
  },
  // 消息管理
  {
    menuId: '40',
    parentMenuId: '',
    path: '/push-notify',
    redirect: '/push-notify/history',
    name: 'PushNotify',
    alwaysShow: 1,
    meta: {
      title: 'pushNotify',
      icon: 'pushNotify',
    },
    children: [
      // 首发概况
      {
        menuId: '4010',
        parentMenuId: '40',
        path: 'situation',
        component: 'push-notify/situation',
        name: 'Situation',
        meta: {
          title: 'situation',
          icon: '',
        },
      },
      // 推送模板
      {
        menuId: '4020',
        parentMenuId: '40',
        path: 'template',
        component: 'push-notify/template',
        name: 'Template',
        meta: {
          title: 'template',
          icon: '',
        },
      },
      // 推送历史
      {
        menuId: '4030',
        parentMenuId: '40',
        path: 'history',
        component: 'push-notify/history',
        name: 'History',
        meta: {
          title: 'history',
          icon: '',
        },
      },
    ],
  },
  // 权限管理
  {
    menuId: '50',
    parentMenuId: '',
    path: '/component-center',
    redirect: '/component-center/advanced-list',
    meta: {
      title: 'componentCenter',
      icon: 'componentCenter',
    },
    alwaysShow: true,
    children: [
      {
        menuId: '5010',
        parentMenuId: '50',
        path: 'advanced-list',
        component: 'component-center/advanced-list',
        name: 'AdvancedList',
        meta: {
          title: 'advancedList',
          icon: '',
        },
      },
      {
        menuId: '5020',
        parentMenuId: '50',
        path: 'demo',
        component: 'component-center/demo',
        name: 'Demo',
        meta: {
          title: 'demo',
          icon: '',
        },
      },
    ],
  },
  // 导出中心
  {
    menuId: '60',
    parentMenuId: '',
    path: '/download',
    redirect: '/download/index',
    alwaysShow: 0,
    children: [
      {
        menuId: '6010',
        parentMenuId: '60',
        path: 'index',
        component: 'download/index',
        name: 'Download',
        meta: {
          title: 'download',
          icon: 'download',
        },
      },
    ],
  },
]
