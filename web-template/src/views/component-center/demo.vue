<template>
  <PageTemplate :prop-form="searchForm" />
</template>
<script>
import { getBtnList, addBtn, editBtn, deleteBtn } from '@/api/authority'
export default {
  components: {
    PageTemplate: require('@/components/AnjiPlus/PageTemplate').default,
  },
  data() {
    return {
      searchForm: {
        list: [
          {
            formType: 'input',
            label: this.$t('btn.close'),
            field: 'actionName',
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
      },
    }
  },
}
</script>
