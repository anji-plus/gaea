/*
 * @Author: zyk
 * @Date: 2020-07-13 10:19:54
 * @Last Modified by: zyk
 * @Last Modified time: 2020-12-22 10:57:15
 */
module.exports = {
  title: '盖亚',

  /**
   * @type {boolean} true | false
   * @description 是否在右面板显示设置
   */
  showSettings: false,

  /**
   * @type {boolean} true | false
   * @description 是否需要 tagsView
   */
  tagsView: true,

  /**
   * @type {boolean} true | false
   * @description 是否固定nav-bar
   */
  fixedHeader: true,

  /**
   * @type {boolean} true | false
   * @description 侧边栏上是否显示logo
   */
  sidebarLogo: true,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production',
}
