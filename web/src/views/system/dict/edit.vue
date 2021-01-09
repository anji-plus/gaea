<template>
  <div class="app-container">
    <el-form ref="form" :rules="rules" :model="form" :inline="true" label-width="120px" label-position="top" size="small">
      <el-form-item label="字典名称" prop="dictName">
        <el-input v-model.trim="form.dictName" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="字典描述" prop="dictDesc">
        <el-input v-model.trim="form.dictDesc" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="字典代码" prop="itemName">
        <el-input v-model.trim="form.itemName" :disabled="updateModel" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="代码值(select option值)" prop="itemValue">
        <el-input v-model.trim="form.itemValue" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="代码描述(select option文字)" prop="itemDesc">
        <el-input v-model.trim="form.itemDesc" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="启用状态" prop="enableFlag">
        <code-select v-model="form.enableFlag" mystyle="width: 100%" dictname="ENABLE_FLAG" prefixicon="el-icon-d-caret"/>
      </el-form-item>
      <el-form-item label="扩展属性" prop="itemExtend" style="width:90%">
        <el-input type="textarea" v-model="form.itemExtend" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number style="width:100%"  :min="1" v-model="form.sort" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remark" prefix-icon="el-icon-info"/>
      </el-form-item>
      <template v-if="updateModel==true">
        <el-form-item label="创建人">
          <el-input :disabled="true" :value="form.createdBy" prefix-icon="el-icon-info"/>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input :disabled="true" :value="form.createdTime | formatTimestamp" prefix-icon="el-icon-info"/>
        </el-form-item>
        <el-form-item label="修改人">
          <el-input :disabled="true" :value="form.updatedBy" prefix-icon="el-icon-info"/>
        </el-form-item>
        <el-form-item label="修改时间">
          <el-input :disabled="true" :value="form.updatedTime | formatTimestamp" prefix-icon="el-icon-info"/>
        </el-form-item>
      </template>
      <el-form-item style="width: 100%;">
        <el-button v-if="!isFind" type="primary" size="medium" @click="onSubmit">保存</el-button>
        <el-button v-else disabled type="primary" size="medium" @click="onSubmit">保存</el-button>
        <el-button size="medium" @click="goBack">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { queryById, reqCreate, reqUpdate } from '@/api/system/dict'
import CodeSelect from '@/components/codeSelect'
export default {
  components: {
    CodeSelect
  },
  data() {
    return {
      form: {
        id: null,
        dictName: '',
        dictDesc: '',
        itemName: '',
        itemValue: '',
        itemDesc: '',
        itemExtend: '',
        enableFlag: null,
        deleteFlag: null,
        sort: null,
        remark: '',
        createdBy: '',
        createdTime: null,
        updatedBy: '',
        updatedTime: null
      },
      rules: {
        dictName: [{ required: true, message: '请输入字典名称', trigger: 'blur' }],
        dictDesc: [{ required: true, message: '请输入字典描述', trigger: 'blur' }],
        itemName: [{ required: true, message: '请输入字典代码', trigger: 'blur' }],
        itemValue: [{ required: true, message: '请输入代码值', trigger: 'blur' }],
        itemDesc: [{ required: true, message: '请输入代码描述', trigger: 'blur' }],
        enableFlag: [{ required: true, message: '请选择启用状态', trigger: 'change' }],
        sort: [
          { required: true, message: '请输入排序', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字值' }
        ]
      },
      isFind: true
    }
  },
  computed: {
    updateModel() {
      return this.$route.query.id != null
    }
  },
  created() {
    var id = this.$route.query.id
    this.isFind = this.$route.query.val
    if (id != null) {
      this.form.dictId = id
      this.queryDetail(id)
    }
  },
  methods: {
    queryDetail(id) {
      queryById({dictId: id}).then(response => {
        if (response.repCode == '0000') {
          this.form = response.repData
        }
      })
    },
    onSubmit() {
      this.$refs['form'].validate((valid) => {
        if (!valid) {
          return
        }
        if (this.updateModel) {
          reqUpdate(this.form).then(response => {
            if (response.repCode == '0000') {
              this.$message({ message: '更新成功', type: 'success', duration: 2 * 1000 })
              this.goBack()
            }
          })
        }else {
          reqCreate(this.form).then(response => {
            if (response.repCode == '0000') {
              this.$message({ message: '新增成功', type: 'success', duration: 2 * 1000 })
              this.goBack()
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

