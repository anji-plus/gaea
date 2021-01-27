/*
 * @Author: zyk
 * @Date: 2020-07-22 10:55:42
 * @Last Modified by:   zyk
 * @Last Modified time: 2020-07-22 10:55:42
 */
import defaultSettings from '@/settings'
import i18n from '@/lang'

const title = defaultSettings.title || 'Vue Element Admin'

export default function getPageTitle(key) {
  const hasKey = i18n.te(`route.${key}`)
  if (hasKey) {
    const pageName = i18n.t(`route.${key}`)
    return `${pageName} - ${title}`
  }
  return `${title}`
}
