<template>
  <PageTemplate :prop-form="search" :prop-btn="false" :prop-table="table" :prop-dialog="dialog" :query-api="getBtnList" :add-api="addBtn" :edit-api="editBtn" :delete-api="deleteBtn" />
</template>
<script>
import { getBtnList, addBtn, editBtn, deleteBtn } from '@/api/authority'
export default {
  components: {
    PageTemplate: require('@/components/AnjiPlus/PageTemplate').default,
  },
  data() {
    return {
      getBtnList,
      addBtn,
      editBtn,
      deleteBtn,
      search: {
        // labelWidth:'200px',
        // disabled: true,
        list: [
          {
            formType: 'input',
            label: this.$t('btn.close'),
            field: 'action',
            rules: { required: true, message: this.$t('placeholder.input'), trigger: 'blur' },
          },
          {
            formType: 'select',
            label: '按钮名称',
            field: 'actionName',
            options: [
              { label: '启用', value: '1' },
              { label: '禁用', value: '0' },
            ],
          },
          {
            formType: 'datetimerange',
            label: '时间',
            field: 'actiontime',
            span: 12,
          },
        ],
        rules: {
          action: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
          actionName: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        },
      },
      table: {
        list: [
          {
            label: '按钮代码',
            field: 'actionName',
            minWidth: '100',
          },
        ],
        border: true, //     表格是否带边框        否              boolean            -               true
        // height: 0,                    //     Table 的高度,         否              string/number        -               -
        // maxHeight: 0,               //     Table 的最大高度,         否              string/number        -               -
        hasSelection: true, //    是否展示复选框         否              boolean            -               true
        // hasIndex:true,                //    是否展示索引列         否              boolean            -               false
        hasCreateAndupdate: true,
      },
      dialog: {
        list: [
          {
            formType: 'input',
            label: this.$t('btn.close'),
            field: 'action',
            rules: { required: true, message: this.$t('placeholder.input'), trigger: 'blur' },
          },
          {
            formType: 'select',
            label: '按钮名称',
            field: 'actionName',
            options: [
              { label: '启用', value: '1' },
              { label: '禁用', value: '0' },
            ],
          },
          {
            formType: 'datetimerange',
            label: '时间',
            field: 'actiontime',
            span: 12,
          },
        ],
        rules: {
          action: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
          actionName: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        },
      },
    }
  },
  created() {
    console.log(Object.prototype.toString.call(getBtnList))
  },
}
</script>
