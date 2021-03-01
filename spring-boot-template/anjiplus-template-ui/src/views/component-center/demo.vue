<template>
  <PageTemplate :prop-form="search" :prop-btn="false" :prop-table="table" :prop-dialog="dialog" :prop-api="apis" />
</template>
<script>
import { getBtnList, addBtn, editBtn, deleteBtn } from '@/api/authority'
export default {
  components: {
    PageTemplate: require('@/components/AnjiPlus/PageTemplate').default,
  },
  data() {
    return {
      apis: {
        query: getBtnList,
        add: addBtn,
        edit: editBtn,
        delete: deleteBtn,
      },
      search: {
        // labelWidth:'100px',          //     表单域标签的宽度       否              string            -               '100px'
        // rules:{},                     //     表单验证规则          否              object            -                 -
        // disabled:false,               //     所有表单的禁用         否              boolean           -               false
        //    所有查询条件列表               //     参数说明         必须	   	      类型	          可选值	          默认值
        list: [
          {
            formType: 'input', //     form表单类型       否            string        select/input        'input'
            label: '按钮代码', //   字段名称-支持国际化   是            string             -                 -
            field: 'actionCode', //        字段名           是            string             -                 -
            // fieldValue: null,          //      字段初始值         否               -               -                 -
            // rules: { required: true, message: this.$t('placeholder.input'), trigger: 'blur' },
            // disabled:false,             //     该项表单的禁用       否              boolean           -               false
            // clearable:true,             //  是否显示清除按钮       否              boolean           -               true
            // placeholder: '',          //       placeholder        否               -               -                -
          },
          {
            label: '按钮名称', //   字段名称-支持国际化   是            string             -                 -
            field: 'actionName', //        字段名           是            string             -                 -
          },
          {
            formType: 'select', //     form表单类型       否            string        select/input        'input'
            label: '启用状态', //   字段名称-支持国际化   是            string             -                 -
            field: 'enabled', //        字段名           是            string             -                 -
            // fieldValue: null,          //      字段初始值         否               -               -                 -
            // disabled:false,             //     该项表单的禁用       否              boolean           -               false
            // clearable:true,             //  是否显示清除按钮       否              boolean           -               true
            // placeholder: '',          //       placeholder        否               -               -                -
            rules: { required: true, message: this.$t('placeholder.input'), trigger: 'change' },
            options: [
              //      下拉框的可选值      否              array            -                 []
              { label: '启用', value: 1 },
              { label: '禁用', value: 0 },
            ],
            optionsconfig: {
              //   下拉框options的配置    否             object            -                 {}
              key: 'value', // 下拉框options的key绑定字段  否             string            -    默认用下边label的值若label未传，则默值为'label'
              label: 'label', //  options的label绑定字段   否             string            -               'label'
              value: 'value', //  options的value绑定字段   否             string            -               'value'
            },
          },
        ],
      },
      table: {
        list: [
          {
            // operate: false,
            label: '按钮代码', //         列名称           是            string             -                 -
            field: 'actionCode', //        字段名           是            string             -                 -
            // minWidth: '110'        //       列最小宽度         否            string             -                110
          },
          {
            label: '按钮名称', //         列名称           是            string             -                 -
            field: 'actionName', //        字段名           是            string             -                 -
            // minWidth: '110'        //       列最小宽度         否            string             -                110
          },
          {
            label: '按钮状态', //         列名称           是            string             -                 -
            field: 'enabled', //        字段名           是            string             -                 -
            // minWidth: '110'        //       列最小宽度         否            string             -                110
            custom: true,
            renderer: function(row) {
              return `<span>${row.enabled ? '是' : '否'}</span>`
            },
          },
        ],
        // border: false,                 //     表格是否带边框        否              boolean            -               true
        // stripe: true,                   //     表格是否斑马纹        否              boolean            -              false
        // height: 0,                     //     Table 的高度,         否              string/number        -               -
        // maxHeight: 0,               //     Table 的最大高度,         否              string/number        -               -
        hasSelection: true, //    是否展示复选框         否              boolean            -               true
        hasIndex: true, //    是否展示索引列         否              boolean            -               false
        hasCreateAndupdate: true, // 是否展示创建/更新者和创建时间   否              boolean            -               true
      },
      dialog: {
        list: [
          {
            formType: 'input', //     form表单类型       否            string        select/input        'input'
            label: '按钮代码', //   字段名称-支持国际化   是            string             -                 -
            field: 'actionCode', //        字段名           是            string             -                 -
            // fieldValue: null,          //      字段初始值         否               -               -                 -
            // rules: { required: true, message: this.$t('placeholder.input'), trigger: 'blur' },
            // disabled:false,             //     该项表单的禁用       否              boolean           -               false
            // clearable:true,             //  是否显示清除按钮       否              boolean           -               true
            // placeholder: '',          //       placeholder        否               -               -                -
          },
          {
            label: '按钮名称', //   字段名称-支持国际化   是            string             -                 -
            field: 'actionName', //        字段名           是            string             -                 -
          },
          {
            formType: 'select', //     form表单类型       否            string        select/input        'input'
            label: '启用状态', //   字段名称-支持国际化   是            string             -                 -
            field: 'enabled', //        字段名           是            string             -                 -
            fieldValue: 1, //      字段初始值         否               -               -                 -
            // disabled:false,             //     该项表单的禁用       否              boolean           -               false
            // clearable:true,             //  是否显示清除按钮       否              boolean           -               true
            // placeholder: '',          //       placeholder        否               -               -                -
            rules: { required: true, message: this.$t('placeholder.input'), trigger: 'change' },
            options: [
              //      下拉框的可选值      否              array            -                 []
              { label: '启用', value: 1 },
              { label: '禁用', value: 0 },
            ],
            optionsconfig: {
              //   下拉框options的配置    否             object            -                 {}
              key: 'value', // 下拉框options的key绑定字段  否             string            -    默认用下边label的值若label未传，则默值为'label'
              label: 'label', //  options的label绑定字段   否             string            -               'label'
              value: 'value', //  options的value绑定字段   否             string            -               'value'
            },
          },
        ],
      },
    }
  },
}
</script>
