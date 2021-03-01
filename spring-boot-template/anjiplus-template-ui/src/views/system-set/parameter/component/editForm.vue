<template>
  <span>
    <!-- json-array类型 -->
    <span v-if="formValue.settingType != 'custom-form'">
      <EachForm v-model="formItemData" :item="formItem" @eachChange="eachChange" />
    </span>
    <span>
      <ConfigForm v-model="formValue.settingValue" :form-items="formItems" @myChanged="handlerChange" />
    </span>
  </span>
</template>

<script>
import ConfigForm from './configForm'
import EachForm from './eachForm'
// import {deepClone} from "@/utils"

export default {
  name: 'EditForm',
  components: {
    ConfigForm,
    EachForm,
  },
  props: {
    value: {
      type: [Object, String],
    },
    formValue: {
      type: [Object, String],
    },
  },
  data() {
    return {
      formItem: {},
      formItemData: {},
    }
  },
  computed: {
    formItems() {
      return this.formValue.settingType == 'custom-form' ? JSON.parse(this.formValue.settingForm || '[]') : []
    },
  },
  watch: {
    formValue(val) {
      console.log(val)
      ;(this.formItem = {
        type: val.settingType,
        label: val.settingLabel,
        name: val.settingName,
        required: true,
        placeholder: '',
      }),
      (this.formItemData = {
        [val.settingName]: val.settingValue,
      })
    },
  },
  methods: {
    deepClone(source) {
      if (!source && typeof source !== 'object') {
        throw new Error('error arguments', 'shallowClone')
      }
      const targetObj = source.constructor === Array ? [] : {}
      Object.keys(source).forEach((keys) => {
        if (source[keys] && typeof source[keys] === 'object') {
          targetObj[keys] = this.deepClone(source[keys])
        } else {
          targetObj[keys] = source[keys]
        }
      })
      return targetObj
    },
    handlerChange(val) {
      var data = this.deepClone(this.formValue)
      data.settingValue = typeof val == 'object' ? JSON.stringify(val) : val
      this.$emit('input', data)
    },
    eachChange(val) {
      const data = this.deepClone(this.formValue)
      data.settingValue = val[this.formValue.settingName]
      this.$emit('input', data)
    },
  },
}
</script>

<style lang="scss" scoped></style>
