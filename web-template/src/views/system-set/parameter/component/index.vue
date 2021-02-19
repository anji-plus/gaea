<template>
  <div>
    <el-form ref="form" :rules="rules" :model="form" label-width="100px">
      {{ form }}
      <el-row class="form_table">
        <el-col :span="12">
          <el-form-item label="参数名称" prop="settingName">
            <el-input v-model.trim="form.settingName" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数描述" prop="settingLabel">
            <el-input v-model.trim="form.settingLabel" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数类型" prop="settingType">
            <el-select v-model="form.settingType" :placeholder="$t('placeholder.select')">
              <el-option v-for="(item, i) in settingTYpeList" :key="i" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="remark">
            <el-input v-model.trim="form.remark" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <form-type :form-data="form" />
        </el-col>
        <!--<el-col :span="12">-->
        <!--<el-form-item label="排序" prop="sort" class="dataDictionary">-->
        <!--<el-input-number v-model="form.sort" :min="1" />-->
        <!--</el-form-item>-->
        <!--</el-col>-->
      </el-row>
      <!--编辑的时候才会显示-->
      <el-row v-if="clickType != 'add'">
        <el-col :span="12">
          <el-form-item label="创建人">
            <el-input :disabled="true" :value="form.createdBy" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创建时间">
            <el-input :disabled="true" :value="form.createdTime" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改人">
            <el-input :disabled="true" :value="form.updatedBy" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改时间">
            <el-input :disabled="true" :value="form.updatedTime" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" style="text-align: center">
      <el-button type="primary" plain @click="onSubmit">{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain @click="cancel">{{ $t('btn.close') }}</el-button>
    </div>
  </div>
</template>

<script>
import FormType from './forms'
export default {
  components: {
    FormType,
  },
  props: {
    clickType: {
      type: String,
      default: function() {
        return ''
      },
    },
    form: {
      type: Object,
      default: function() {
        return {
          settingName: '',
          settingLabel: '',
          settingType: '',
          settingValue: '',
          settingForm: '',
          remark: '',
        }
      },
    },
  },
  data() {
    return {
      // form: {
      //   id: null,
      //   dictName: '',
      //   dictDesc: '',
      //   itemName: '',
      //   itemValue: '',
      //   itemDesc: '',
      //   itemExtend: '',
      //   enableFlag: null,
      //   deleteFlag: null,
      //   sort: null,
      //   remark: '',
      //   createdBy: '',
      //   createdTime: null,
      //   updatedBy: '',
      //   updatedTime: null
      // },
      settingTYpeList: [
        { extend: '', label: '字符串', labelEng: 'input', value: 'input' },
        { extend: '', label: '数字', labelEng: 'input-number', value: 'input-number' },
        { extend: '', label: '文本区域', labelEng: 'textarea', value: 'textarea' },
        { extend: '', label: '数据字典', labelEng: 'code-select', value: 'code-select' },
        { extend: '', label: '下拉列表', labelEng: 'select', value: 'select' },
        { extend: '', label: '单选按钮', labelEng: 'radio-group', value: 'radio-group' },
        { extend: '', label: '多选按钮', labelEng: 'checkbox-group', value: 'checkbox-group' },
        { extend: '', label: '自定义表单', labelEng: 'custom-form', value: 'custom-form' },
      ],
      rules: {
        settingName: [{ required: true, message: '请输入参数名称', trigger: 'blur' }],
        settingLabel: [{ required: true, message: '请输入参数描述', trigger: 'blur' }],
        settingType: [{ required: true, message: '请选择参数类型', trigger: 'change' }],
        // remark: [{ required: true, message: '请输入备注', trigger: 'blur' }],
        // sort: [
        //   { required: true, message: '请输入排序', trigger: 'blur' },
        //   { type: 'number', message: '排序必须为数字值' },
        // ],
      },
      isFind: true,
    }
  },
  computed: {},
  created() {
    var id = this.$route.query.id
    this.isFind = this.$route.query.val
    if (id != null) {
      this.form.dictId = id
      this.queryDetail(id)
    }
  },
  methods: {
    cancel(formName) {
      this.$emit('cancel')
      // this.$refs[formName].resetFields();
    },
    queryDetail(id) {
      // queryById({ dictId: id }).then((response) => {
      //   if (response.repCode == '0000') {
      //     this.form = response.repData
      //   }
      // })
    },
    onSubmit() {
      this.$refs['form'].validate((valid) => {
        // if (!valid) {
        //   return
        // }
        // if (this.updateModel) {
        //   reqUpdate(this.form).then(response => {
        //     if (response.repCode == '0000') {
        //       this.$message({ message: '更新成功', type: 'success', duration: 2 * 1000 })
        //       this.goBack()
        //     }
        //   })
        // }else {
        //   reqCreate(this.form).then(response => {
        //     if (response.repCode == '0000') {
        //       this.$message({ message: '新增成功', type: 'success', duration: 2 * 1000 })
        //       this.goBack()
        //     }
        //   })
        // }
      })
    },
  },
}
</script>

<style>
.dataDictionary .el-input-number--mini {
  width: 100%;
}
</style>
