<template>
  <el-dialog class="tree_dialog" title="设定菜单" width="40%" :close-on-click-modal="false" center :visible.sync="visib" :before-close="closeDialog">
    <el-tree ref="roleTree" :data="menuData" show-checkbox node-key="id" default-expand-all :default-checked-keys="checkedKeys" :props="defaultProps" />
    <div slot="footer" style="text-align: center">
      <el-button type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain @click="closeDialog">{{ $t('btn.close') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { allMenu, actionTree, saveMenuTree } from '@/api/authority'
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
      defaultProps: {
        label: (data) => {
          // 按钮权限
          return data.label
          // return this.$t(`route.${data.id}`)
        },
        // disabled: () => {
        //   return this.dialogTittle == 'view'
        // },
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
    async confirm() {
      this.setForm = {
        roleCode: this.id,
        codes: this.$refs.roleTree.getCheckedKeys(true),
      }
      const { code } = await saveMenuTree(this.setForm)
      if (code != '200') return
      this.closeDialog()
    },
    // 获取所有的菜单树形结构
    async getAllMenus() {
      const { code, data } = await allMenu()
      if (code != '200') return
      // 数据结构
      /**
       [
         id:'菜单配置中的菜单代码',
         label:'菜单配置中的菜单名称'
       ]
       */
      const hasChildren = data
      // 将一级菜单就是页面的树过滤出来，直接展示
      hasChildren.forEach((item, index) => {
        if (item.children && item.children.length == 1) {
          hasChildren.splice(index, 1, item.children[0])
        }
      })
      this.menuData = hasChildren
    },
    async getCheckedId() {
      const { code, data } = await actionTree(this.id)
      if (code != '200') return
      this.checkedKeys = data
    },
    // 弹窗关闭之前需要执行的逻辑
    closeDialog() {
      this.$emit('handleClose')
    },
  },
}
</script>
