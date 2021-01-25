/*
 * @Author: zyk
 * @Date: 2020-07-13 15:34:51
 * @Last Modified by: zyk
 * @Last Modified time: 2020-12-15 15:48:59
 */

/** 脚手架基础页面路由 **/
export const baseList = [
  {
    path: '/user-management',
    redirect: '/user-management/role',
    name: 'UserManagement',
    alwaysShow: true,
    meta: {
      title: 'userManagement',
      icon: 'UserManagement',
    },
    children: [
      {
        path: 'role',
        component: 'user-management/role',
        name: 'Role',
        meta: {
          title: 'role',
          icon: '',
        },
      },
      {
        path: 'user',
        component: 'user-management/user',
        name: 'User',
        meta: {
          title: 'user',
          icon: '',
        },
      },
      {
        path: 'permission',
        component: 'user-management/permission',
        name: 'Permission',
        meta: {
          title: 'permission',
          icon: '',
        },
      },
      {
        path: 'login-log',
        component: 'user-management/login-log',
        name: 'LoginLog',
        meta: {
          title: 'loginLog',
          icon: '',
        },
      },
    ],
  },
  {
    path: '/system-conf',
    redirect: '/system-conf/data-dictionary',
    name: 'SystemConf',
    alwaysShow: 1,
    meta: {
      title: 'systemConf',
      icon: 'SystemConf',
    },
    children: [
      {
        path: 'data-dictionary',
        component: 'system-conf/data-dictionary',
        name: 'DataDictionary',
        meta: {
          title: 'dataDictionary',
          icon: '',
        },
      },
    ],
  },
]
