<template>
  <div class="app-container">
    <el-form ref="form" :rules="rules" :model="form" :inline="true" label-width="120px" label-position="top" size="small">
      <el-form-item label="按钮代码" prop="actionCode">
        <el-input v-model.trim="form.actionCode" :disabled="updateModel" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="按钮名称" prop="actionName">
        <el-input v-model.trim="form.actionName" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model.trim="form.sort" prefix-icon="el-icon-info"/>
      </el-form-item>
      <!-- <el-form-item label="备注" prop="remark">
        <el-input v-model.trim="form.remark" prefix-icon="el-icon-info"/>
      </el-form-item> -->
      <el-form-item label="启用状态" prop="enableFlag">
        <code-select v-model="form.enableFlag" mystyle="width: 100%" dictname="ENABLE_FLAG" prefixicon="el-icon-d-caret"/>
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
import { createOrUpdate, queryById } from '@/api/authority/action'
import CodeSelect from '@/components/codeSelect'
export default {
  components: {
    CodeSelect
  },
  data() {
    return {
      form: {
        actionId: null,
        actionCode: null,
        actionName: null,
        sort: null,
        enableFlag: null,
        deleteFlag: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
      },
      rules: {
        actionCode: [{ required: true, message: '请输入权限代码', trigger: 'blur' }],
        actionName: [{ required: true, message: '请输入权限名称', trigger: 'blur' }],
        sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
        enableFlag: [{ required: true, message: '请选择启用状态', trigger: 'change' }]
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
    var actionId = this.$route.query.id
    this.isFind = this.$route.query.val
    if (actionId != null) {
      this.form.actionId = actionId
      this.queryById(actionId)
    }
  },
  methods: {
    queryById(actionId) {
      queryById(actionId).then(response => {
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
        createOrUpdate(this.form).then(response => {
          if (response.repCode == '0000') {
            this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
            this.goBack()
            // this.$router.push('/access/accessUser')
          }
        })
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

