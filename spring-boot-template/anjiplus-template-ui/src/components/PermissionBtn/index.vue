<template>
  <el-button v-if="permission" :disabled="disabled" :icon="icon" :type="type">{{ $t(`btn.${label}`) }}</el-button>
</template>
<script>
export default {
  props: {
    // 按钮名称
    label: {
      required: true,
      type: String,
      default: null,
    },
    // 按钮图标
    icon: {
      type: String,
      default: null,
    },
    // 按钮禁用状态
    disabled: {
      type: Boolean,
      default: false,
    },
    // 按钮类型
    type: {
      type: String,
      default: 'primary',
    },
  },
  data() {
    return {
      permission: true, // 显示的权限
    }
  },
  created() {
    // 判断是否存在该按钮的 权限
    this.permission = this.$route.meta.permission.some((item) => {
      return item == this.label
    })
  },
}
</script>
