<template>
  <el-dialog title="设定菜单" width="60%" :close-on-click-modal="false" center :visible.sync="visib" :before-close="closeDialog">
    <el-tree ref="roleTree" :data="menuData" show-checkbox node-key="id" default-expand-all :default-checked-keys="checkedKeys" :props="defaultProps" />
    <div slot="footer" style="text-align: center">
      <el-button type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain @click="closeDialog">{{ $t('btn.close') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { allMenu, actionTree } from '@/api/authority'
export default {
  props: {
    visib: {
      required: true,
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      checkedKeys: [], // 当前选中的keys
      menuData: [], // 所有的菜单
      parentMenuIds: [], // 非叶子节点的菜单id
      defaultProps: {
        label: (data, node) => {
          // 按钮权限
          var index = data.label.indexOf(':')
          if (index >= 0) {
            return this.$t(`btn.${data.label.substr(index + 1)}`)
          }
          return this.$t(`router.${data.label}`)
        },
        disabled: () => {
          return this.dialogTittle == 'view'
        },
      },
    }
  },
  watch: {
    visib(val) {
      if (val) {
        // 弹窗弹出时需要执行的逻辑
        this.getCheckedId()
      }
    },
  },
  created() {
    this.getAllMenus()
  },

  methods: {
    confirm() {
      console.log(this.$refs.roleTree.getCheckedKeys(true))
    },
    // 获取所有的菜单树形结构
    async getAllMenus() {
      const { code, data } = await allMenu()
      if (code != '200') return
      this.menuData = data
    },
    async getCheckedId() {
      const { code, data } = await actionTree()
      if (code != '200') return
      this.checkedKeys = data
    },
    // // 获取非叶子节点的菜单id
    // getParentMenuIds(list) {
    //   list.forEach((item) => {
    //     if (item.children && item.children.length != 0) {
    //       this.parentMenuIds.push(item.id)
    //       this.getParentMenuIds(item.children)
    //     }
    //   })
    // },
    //  只留下叶子节点的id（去除父节点的id）
    // menuIds是包含父节点的已选中id的list
    // deleteParentMenuIds(menuIds) {
    //   this.checkedKeys = menuIds
    // },
    // 获取选中菜单的id
    getMenuIds() {
      // return this.$refs.roleTree.getCheckedKeys().concat(...this.$refs.roleTree.getHalfCheckedKeys())
      return this.$refs.roleTree.getCheckedKeys()
    },

    // 弹窗关闭之前需要执行的逻辑
    closeDialog() {
      this.$emit('handleClose')
    },
  },
}
</script>
