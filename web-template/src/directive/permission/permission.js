import store from '@/store'

export default {
  inserted(el, binding, vnode) {
    const { value } = binding // v-permission 绑定的权限值
    const auths = store.getters && store.getters.authorities // 拥有的权限列表
    if (value) {
      alert(1)
      auths.indexOf(value) === -1 && el.parentNode && el.parentNode.removeChild(el)
      // const hasPermission = auths.some((role) => {
      //   return permissions.includes(role)
      // })
      // const hasPermission = auths.indexOf(value) !== -1
      // if (!hasPermission) {
      //   el.parentNode && el.parentNode.removeChild(el)
      // }
    } else {
      throw new Error(`need roles! Like v-permission="gaeaPushTemplateController#deleteBatchIds"`)
    }
  },
}
