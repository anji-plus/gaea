<template>
  <el-dialog class="tree_dialog" title="设定组织" width="60%" :close-on-click-modal="false" center :visible.sync="visib" :before-close="closeDialog">
    <el-tree ref="roleTree" :data="menuData" show-checkbox node-key="id" default-expand-all :default-checked-keys="checkedKeys" />
    <div slot="footer" style="text-align: center">
      <el-button type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain @click="closeDialog">{{ $t('btn.close') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getOrgTree, saveOrgTree } from '@/api/authority'
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
        roleCode: this.id,
        orgCodes: this.$refs.roleTree.getCheckedKeys(true),
      }
      const { code } = await saveOrgTree(this.setForm)
      if (code != '200') return
      this.closeDialog()
    },
    // 获取所有的树形结构数据
    async getAllMenus() {
      const { code, data } = await getOrgTree(this.id)
      if (code != '200') return
      this.menuData = data.treeDatas
      this.checkedKeys = data.checkedCodes
    },

    // 弹窗关闭之前需要执行的逻辑
    closeDialog() {
      this.$emit('handleClose')
    },
  },
}
</script>
