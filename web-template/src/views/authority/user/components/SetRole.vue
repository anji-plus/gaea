<template>
  <el-dialog title="设定角色" width="60%" :close-on-click-modal="false" center :visible.sync="visib" :before-close="closeDialog">
    <el-tree ref="roleTree" :data="menuData" show-checkbox node-key="id" default-expand-all :default-checked-keys="checkedKeys" />
    <div slot="footer" style="text-align: center">
      <el-button type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain @click="closeDialog">{{ $t('btn.close') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getRoleTree, saveRoleTree } from '@/api/authority'
export default {
  props: {
    visib: {
      required: true,
      type: Boolean,
      default: false,
    },
    id: {
      required: true,
      type: String,
      default: () => {
        return ''
      },
    },
  },
  data() {
    return {
      setForm: {},
      checkedKeys: [], // 当前选中的keys
      menuData: [], // 所有的菜单
      parentMenuIds: [], // 非叶子节点的菜单id
    }
  },
  watch: {
    visib(val) {
      if (val) {
        // 弹窗弹出时需要执行的逻辑
        this.getAllMenus()
      }
    },
  },
  created() {},

  methods: {
    async confirm() {
      this.setForm = {
        username: this.id,
        roleOrgCodes: this.$refs.roleTree.getCheckedKeys(true),
      }
      const { code } = await saveRoleTree(this.setForm)
      if (code != '200') return
      this.closeDialog()
    },
    // 获取所有的菜单树形结构
    async getAllMenus() {
      const { code, data } = await getRoleTree(this.id)
      if (code != '200') return
      this.menuData = data.treeDatas
      this.checkedKeys = data.checkedCodes
    },
    // async getCheckedId() {
    //   const { code, data } = await actionTree()
    //   if (code != '200') return
    //   this.checkedKeys = data
    // },
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
