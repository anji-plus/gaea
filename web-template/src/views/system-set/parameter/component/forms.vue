<template>
  <!--<div>-->
  <!-- input 输入框-->
  <el-form-item>
    <el-input v-if="formData.settingType == 'input'" v-model="formData.settingValue" clearable />
    <!-- input-number 数字输入框-->
    <el-input-number v-if="formData.settingType == 'input-number'" v-model="formData.settingValue" :min="1" />
    <!-- textarea 文本框-->
    <el-input v-if="formData.settingType == 'textarea'" v-model="formData.settingValue" type="textarea" clearable autosize />
    <!-- 数据字典-->
    <el-select v-if="formData.settingType == 'code-select'" v-model="formData.settingValue">
      <el-option label="区域一" value="shanghai" />
      <el-option label="区域二" value="beijing" />
    </el-select>
  </el-form-item>
  <!--</div>-->
</template>

<script>
export default {
  name: 'Forms',
  props: {
    formData: {
      type: Object,
    },
  },
  data() {
    return {}
  },
  watch: {
    'formData.settingType'(val) {
      this.formData.settingValue = ''
    },
  },
}
</script>

<style scoped></style>
