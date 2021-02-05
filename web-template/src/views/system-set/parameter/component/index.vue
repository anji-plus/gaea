<template>
  <div>
    <el-form ref="form" :rules="rules" :model="form" label-width="100px">
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
        <!--<el-col :span="12">-->
        <!--<el-form-item label="字典代码" prop="itemName">-->
        <!--<el-input v-model.trim="form.itemName"/>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :span="12">-->
        <!--<el-form-item label="代码值" prop="itemValue">-->
        <!--<el-input v-model.trim="form.itemValue"/>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :span="12">-->
        <!--<el-form-item label="代码描述" prop="itemDesc">-->
        <!--<el-input v-model.trim="form.itemDesc"/>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :span="12">
          <el-form-item label="参数类型" prop="settingType">
            <el-select v-model="form.settingType" :placeholder="$t('placeholder.select')">
              <el-option key="1" label="启用" :value="1" />
              <el-option key="0" label="禁用" :value="0" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="remark">
            <el-input v-model.trim="form.remark" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序" prop="sort" class="dataDictionary">
            <el-input-number v-model="form.sort" :min="1" />
          </el-form-item>
        </el-col>
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
export default {
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
          settingId: null,
          settingName: '',
          settingLabel: '',
          settingType: '',
          settingValue: '',
          settingForm: '',
          remark: '',
          createdBy: '',
          createdTime: null,
          updatedBy: '',
          updatedTime: null,
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
      rules: {
        settingName: [{ required: true, message: '请输入参数名称', trigger: 'blur' }],
        settingLabel: [{ required: true, message: '请输入参数描述', trigger: 'blur' }],
        settingType: [{ required: true, message: '请选择参数类型', trigger: 'change' }],
        remark: [{ required: true, message: '请输入备注', trigger: 'blur' }],
        sort: [
          { required: true, message: '请输入排序', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字值' },
        ],
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
