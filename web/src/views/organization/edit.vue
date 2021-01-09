<template>
  <div class="app-container">
    <el-form ref="form" :rules="rules" :model="form" :inline="true" label-width="120px" label-position="top" size="small">
      <el-form-item label="机构代码" prop="orgCode">
        <el-input v-model="form.orgCode" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="机构名称" prop="orgName">
        <el-input v-model="form.orgName" prefix-icon="el-icon-info"/>
      </el-form-item>
<!--       <el-form-item label="上级机构代码">-->
<!--        <el-input v-model="form.orgParentCode" prefix-icon="el-icon-info"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="上级机构名称">-->
<!--        <el-input v-model="form.orgParentName" prefix-icon="el-icon-info"/>-->
<!--      </el-form-item>-->
      <el-form-item label="上级机构名称" prop="orgCode">
        <!-- <el-input v-model.trim="addForm.orgCode" placeholder="请输入"></el-input> -->
        <el-select v-model.trim="form.orgParentName"
          @change="handlerOrgchange" style="width: 100%;" placeholder="请选择">
          <el-option
            v-for="item in tableData"
            :key="item.label"
            :label="item.label"
            :value="{value:item.value,label:item.label}">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="外部机构代码">
        <el-input v-model="form.outOrgCode" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="外部上级机构代码">
        <el-input v-model="form.outOrgParentCode" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="机构级别">
        <el-input v-model="form.orgLevel" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="机构类型">
        <el-input v-model="form.orgType" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="联系人">
        <el-input v-model="form.linkman" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.mobilePhone" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="固定电话">
        <el-input v-model="form.telephone" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="启用状态" prop="enableFlag">
        <code-select v-model="form.enableFlag" mystyle="width: 100%" dictname="ENABLE_FLAG" prefixicon="el-icon-d-caret"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remark" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item/>
      <div>
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
      </div>
      <el-form-item style="width: 100%;">
        <el-button v-if="!isFind" type="primary" size="medium" @click="Submit">保存</el-button>
        <el-button v-else disabled type="primary" size="medium" @click="Submit">保存</el-button>
        <el-button size="medium" @click="goBack">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { createOrUpdate, queryById, selectOption } from '@/api/authority/org'
import CodeSelect from '@/components/codeSelect'

export default {
  components: {
    CodeSelect
  },
  data() {
    return {
      form: {
        orgId: null,
        orgCode: null,
        orgName: null,
        orgParentCode: null,
        orgParentName: null,
        outOrgCode: null,
        outOrgParentCode: null,
        orgLevel: null,
        orgType: null,
        linkman: null,
        mobilePhone: null,
        telephone: null,
        enableFlag: null,
        deleteFlag: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
      },
      orgRemoteParams:{
        currentPage: 1,
        pageSize: 9999,
      },
      tableData:[],
      rules: {
        orgCode: [{ required: true, message: '请输入机构代码', trigger: 'blur' }],
        orgName: [{ required: true, message: '请输入机构名称', trigger: 'blur' }],
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
    var orgId = this.$route.query.id
    this.isFind = this.$route.query.val
    if (orgId != null) {
      this.form.orgId = orgId
      this.queryById(orgId)
    }
    this.selectOption();
  },
  methods: {
    handlerOrgchange(params){
      const { value, label } = params;
      this.form.orgParentName = label;
      this.form.orgParentCode = value;
    },
    selectOption() {
      selectOption(this.orgRemoteParams).then(response => {
        if (response.repCode == '0000') {
          this.tableData = response.repData
          this.tableData.unshift({label:"无上级",value:"0"})
        }
      })
    },
    queryById(orgId) {
      queryById(orgId).then(response => {
        if (response.repCode == '0000') {
          this.form = response.repData
        }
      })
    },
    Submit() {
      this.$refs['form'].validate((valid) => {
        if (!valid) {
          return
        }
        createOrUpdate(this.form).then(response => {
          if (response.repCode == '0000') {
            this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
            this.goBack()
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

