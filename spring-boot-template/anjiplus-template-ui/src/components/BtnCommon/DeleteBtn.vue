<!--
 * @Author: zyk 
 * @Date: 2020-07-13 16:10:40 
 * @Last Modified by:   zyk 
 * @Last Modified time: 2020-07-13 16:10:40 
 */
!-->
<template>
  <permission-btn v-if="hasPermission" label="delete" icon="el-icon-delete" type="danger" />
  <el-button v-else type="danger" icon="el-icon-delete" :disabled="disabled" @click="deleteTips">
    {{ $t('btn.delete') }}
  </el-button>
</template>
<script>
export default {
  name: 'DeleteBtn',
  props: {
    // 是否禁用
    disabled: {
      default: true,
      type: Boolean,
    },
    // 是否存在按钮权限
    hasPermission: {
      default: false,
      type: Boolean,
    },
  },
  methods: {
    deleteTips() {
      this.$confirm(this.$t('promptMessage.deleteTip'), this.$t('promptMessage.deleteTipTitle'), {
        type: 'warning',
        confirmButtonClass: 'delete_sure',
        cancelButtonClass: 'el-button--danger is-plain',
      })
        .then(() => {
          this.$emit('handleDelete')
        })
        .catch(() => {})
    },
  },
}
</script>
<style lang="scss" scoped>
.delete_sure.el-button--primary {
  color: #1890ff;
  background: #e8f4ff;
  border-color: #a3d3ff;
  &:hover {
    color: #ffffff;
    background-color: #1890ff;
    border-color: #1890ff;
  }
}
</style>
