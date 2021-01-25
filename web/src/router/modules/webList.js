/** When your routing table is too long, you can split it into small modules **/
export const webList = [
  {
    path: '/list',
    redirect: '/list/index',
    alwaysShow: 1,
    meta: {
      title: 'list',
      icon: 'list',
    },
    children: [
      {
        path: 'index',
        component: 'list/index',
        name: 'List',
        meta: {
          title: 'normal',
        },
      },
    ],
  },
  {
    path: '/error',
    redirect: '/error/404',
    meta: {
      title: 'error',
      icon: 'error',
    },
    children: [
      {
        path: '401',
        component: 'error-page/401',
        name: 'Error401',
        meta: {
          title: '401',
          icon: '',
        },
      },
      {
        path: '404',
        component: 'error-page/404',
        name: 'Error404',
        meta: {
          title: '404',
          icon: '',
        },
      },
    ],
  },
]
