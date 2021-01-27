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
]
