<template>
  <span>
    <!-- json-array类型 -->
    <span v-if="formValue.settingType !='custom-form'">
      <EachForm :item="formItem" v-model="formItemData" @eachChange='eachChange'></EachForm>
    </span>
    <span>
      <ConfigForm v-model="formValue.settingValue" @myChanged="handlerChange" :formItems="formItems"></ConfigForm>
    </span>
  </span>
</template>

<script>
import ConfigForm from "@/components/configForm";
import EachForm from "@/components/eachForm"
import {deepClone} from "@/utils"

export default {
  name:'edit-form',
  data() {
    return {
      formItem: {},
      formItemData: {},
    }
  },
  props:{
    value:{
      type: [Object, String],
    },
    formValue:{
      type:[Object, String],
    },
  },
  watch: {
    formValue(val){
      console.log(val)
      this.formItem = {
        "type": val.settingType,
        "label": val.settingLabel,
        "name": val.settingName,
        "required": true,
        "placeholder": ""
      },
      this.formItemData = {
        [val.settingName]: val.settingValue
      }
    }
  },
  computed: {
    formItems() {
      return this.formValue.settingType == "custom-form"
        ? JSON.parse(this.formValue.settingForm || "[]")
        : [];
    }
  },
  components:{
    ConfigForm,
    EachForm
  },
  methods: {
    handlerChange(val){
      var data = this.deepClone(this.formValue)
      data.settingValue = typeof val == "object" ? JSON.stringify(val): val
      this.$emit('input', data)
    },
    eachChange(val){
      let data = this.deepClone(this.formValue)
      data.settingValue = val[this.formValue.settingName]
      this.$emit('input', data)
    },
  },
}
</script>

<style lang="scss" scoped>
  
</style>