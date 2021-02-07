module.exports = {
  base: '/',
  title: '复用性底盘',
  description: '使用[盖亚]快速构建业务系统',
  dest: 'dist',
  lastUpdated: 'Last Updated',
  theme: '',
  themeConfig: {
    logo: '/logo.png',
    smoothScroll: true,
    sidebarDepth: 2,
    nav: [
      { text: '首页', link: '/' },
      { text: '文档', link: '/guide/' },
      { text: '插件', link: '/plugin/' },
      { text: 'GitHub', link: 'https://github.com/anji-plus/gaea' },
      { text: 'Gitee', link: 'https://gitee.com/anji-plus/gaea' },
    ],
    sidebar:{
      '/guide/': ['','directory-structure'],
      '/plugin/': ['archiver','behaviorAudit','push','export','security']
    },
  },
  plugins: [
    ['@vuepress/back-to-top', true],
  ]
}