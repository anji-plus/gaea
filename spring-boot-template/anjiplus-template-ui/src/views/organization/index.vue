<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="130px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item label="机构代码" prop="orgCode">
                <el-input v-model="searchForm.orgCode" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="机构名称" prop="orgName">
                <el-input v-model="searchForm.orgName" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="外部机构代码" prop="outOrgCode">
                <el-input v-model="searchForm.outOrgCode" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="外部上级机构代码" prop="outOrgParentCode">
                <el-input v-model="searchForm.outOrgParentCode" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="机构类型" prop="orgType">
                <el-input v-model="searchForm.orgType" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="联系人" prop="linkman">
                <el-input v-model="searchForm.linkman" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="启用状态" prop="enabled">
                <el-select v-model="searchForm.enabled" :placeholder="$t('placeholder.select')">
                  <el-option key="1" label="启用" :value="1" />
                  <el-option key="0" label="禁用" :value="0" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="上级机构名称" prop="orgParentCode">
                <el-select v-model="searchForm.orgParentCode" :placeholder="$t('placeholder.select')">
                  <el-option v-for="item in orgParentList" :key="item.orgCode" :label="item.orgName" :value="item.orgCode" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-button
            type="primary"
            @click="
              searchForm.pageNumber = 1
              getData()
            "
          >{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="resetForm('formSearch')">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-button type="primary" icon="el-icon-plus" @click="openCreateUser">{{ $t('btn.add') }}</el-button>
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="editDetail('edit', null)">{{ $t('btn.edit') }}</el-button>
    <delete-btn :disabled="selectedList.length != 1" @handleDelete="handleDelete" />
    <el-table :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column label="机构代码" align="center" min-width="180">
        <template slot-scope="scope">
          <span class="view" @click="editDetail('view', scope.row)">{{ scope.row.orgCode }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="orgName" label="机构名称" min-width="140" align="center" />
      <el-table-column prop="orgParentCode" label="上级机构代码" min-width="200" align="center" />
      <el-table-column prop="orgParentName" label="上级机构名称" min-width="200" align="center" />
      <el-table-column prop="enabled" label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.enabled ? '启用' : '禁用' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" :show-overflow-tooltip="true" max-width="220" align="center" />
      <el-table-column prop="createTime" :label="$t('userManage.creationTime')" align="center" min-width="160" />
      <el-table-column prop="createBy" :label="$t('userManage.creator')" align="center" min-width="160" />
      <el-table-column prop="updateTime" :label="$t('userManage.modifyTime')" align="center" min-width="180" />
      <el-table-column prop="updateBy" :label="$t('userManage.modifyUser')" align="center" min-width="140" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.pageNumber" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="50%" :close-on-click-modal="false" center :visible.sync="basicDialog" @close="closeDialog">
      <el-form ref="userForm" :model="dialogForm" :rules="formRules" label-width="130px" :disabled="dialogTittle == 'view'">
        <el-row class="form_table">
          <el-col :span="12">
            <el-form-item label="机构代码" prop="orgCode">
              <el-input v-model.trim="dialogForm.orgCode" :disabled="dialogTittle != 'add'" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机构名称" prop="orgName">
              <el-input v-model.trim="dialogForm.orgName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级机构名称" prop="orgParentCode">
              <el-select v-model="searchForm.orgParentCode" :placeholder="$t('placeholder.select')">
                <el-option v-for="item in orgParentList" :key="item.orgCode" :label="item.orgName" :value="item.orgCode" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部机构代码" prop="outOrgCode">
              <el-input v-model.trim="dialogForm.outOrgCode" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部上级机构代码" prop="outOrgParentCode">
              <el-input v-model.trim="dialogForm.outOrgParentCode" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机构级别" prop="orgLevel">
              <el-input v-model.trim="dialogForm.orgLevel" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机构类型" prop="orgType">
              <el-input v-model.trim="dialogForm.orgType" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="linkman">
              <el-input v-model.trim="dialogForm.linkman" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="mobilePhone">
              <el-input v-model.trim="dialogForm.mobilePhone" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="固定电话" prop="telephone">
              <el-input v-model.trim="dialogForm.telephone" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="启用状态" prop="enabled">
              <el-select v-model="dialogForm.enabled" :placeholder="$t('placeholder.select')">
                <el-option key="1" label="启用" :value="1" />
                <el-option key="0" label="禁用" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model.trim="dialogForm.remark" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button v-if="dialogTittle != 'view'" type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="basicDialog = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getOrgList, addOrg, editOrg, deleteOrg, getParentOrgList } from '@/api/organization'
export default {
  data() {
    return {
      selectedList: [],
      orgParentList: [], // 上级 组织下拉列表
      searchForm: {
        orgCode: null,
        orgName: null,
        outOrgCode: null,
        outOrgParentCode: null,
        orgType: null,
        linkman: null,
        enableFlag: null,
        pageNumber: 1,
        pageSize: 10,
      },
      tableList: [],
      total: 0,
      dialogTittle: 'view',
      basicDialog: false,
      dialogForm: {
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
        enabled: 1,
        remark: null,
      },
      formRules: {
        orgCode: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        orgName: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        mobilePhone: [
          { required: false, message: this.$t('placeholder.input'), trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: this.$t('userManage.mobileVilid'), trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    this.getParentOrg()
  },
  methods: {
    async getParentOrg() {
      const { code, data } = await getParentOrgList(this.dialogForm)
      if (code != '200') return
      this.orgParentList = data
    },
    // 提交按钮
    confirm() {
      this.$refs.userForm.validate(async(valid, obj) => {
        if (valid) {
          if (this.dialogTittle == 'add') {
            const { code } = await addOrg(this.dialogForm)
            if (code != '200') return
            this.closeDialog(true)
          } else {
            const { code } = await editOrg(this.dialogForm)
            if (code != '200') return
            this.closeDialog(true)
          }
        } else {
          return
        }
      })
    },
    // 关闭弹窗
    closeDialog(bool) {
      bool && this.getData() // 点确定关闭弹窗的时候才会刷新列表
      this.$refs['userForm'].resetFields()
      this.basicDialog = false
    },
    // 删除操作
    async handleDelete() {
      const { code } = await deleteOrg(this.selectedList[0].id)
      if (code != '200') return
      this.getData()
    },
    // 新建操作
    openCreateUser() {
      this.dialogTittle = 'add' // 新建
      this.basicDialog = true
    },
    // 编辑和查看操作
    editDetail(title, row) {
      this.dialogTittle = title
      this.basicDialog = true
      this.$nextTick(() => {
        this.dialogForm = JSON.parse(JSON.stringify(row || this.selectedList[0]))
      })
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.tableList = []
      this.total = 0
    },
    // 查询
    async getData() {
      const { data, code } = await getOrgList(this.searchForm)
      if (code != '200') return
      this.tableList = data.records
      this.total = data.total
    },
    // 选择项改变时
    handleSelectionChange(val) {
      this.selectedList = val
    },
    // 页码改变
    handleCurrentChange(pageNumber) {
      this.searchForm.pageNumber = pageNumber
      this.getData()
    },
    // 每页size改变时
    handleSizeChange(val) {
      this.searchForm.pageNumber = 1
      this.searchForm.pageSize = val
      this.getData()
    },
  },
}
</script>
