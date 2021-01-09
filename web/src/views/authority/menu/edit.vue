<template>
  <div class="app-container">
    <el-form ref="form" :rules="rules" :model="form" :inline="true" label-width="120px" label-position="top" size="small">
      <el-form-item label="菜单代码" prop="menuCode">
        <el-input v-model.trim="form.menuCode" :disabled="updateModel" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="菜单名称" prop="menuName">
        <el-input v-model.trim="form.menuName" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="系统终端">
        <code-select v-model="form.sysCode" mystyle="width: 100%" dictname="SYSTEM_CODE" prefixicon="el-icon-d-caret"/>
      </el-form-item>
      <el-form-item label="父级id">
        <el-input-number :min='0' style="width:100%" v-model="form.parentId" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="菜单路径">
        <el-input v-model.trim="form.menuUrl" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="图标">
        <el-input v-model.trim="form.menuIcon" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number :min='0' style="width:100%" v-model="form.sort" prefix-icon="el-icon-info"/>
      </el-form-item>
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
import { createOrUpdate, queryById } from '@/api/authority/menu'
import CodeSelect from '@/components/codeSelect'
export default {
  components: {
    CodeSelect
  },
  data() {
    return {
      form: {
        menuId: null,
        menuCode: null,
        menuName: null,
        sysCode: null,
        parentId: null,
        menuUrl: null,
        menuIcon: null,
        sort: null,
        enableFlag: null,
        deleteFlag: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
      },
      rules: {
        menuCode: [{ required: true, message: '请输入菜单代码', trigger: 'blur' }],
        menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
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
    var menuId = this.$route.query.id
    this.isFind = this.$route.query.val
    if (menuId != null) {
      this.form.menuId = menuId
      this.queryById(menuId)
    }
  },
  methods: {
    queryById(menuId) {
      queryById(menuId).then(response => {
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

