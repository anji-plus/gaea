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
              <el-form-item label="启用状态" prop="enableFlag">
                <el-select v-model="searchForm.enableFlag" :placeholder="$t('placeholder.select')">
                  <el-option key="1" label="启用" :value="1" />
                  <el-option key="0" label="禁用" :value="0" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="上级组织" prop="orgParentCode">
                <el-select v-model="searchForm.orgParentCode" :placeholder="$t('placeholder.select')">
                  <el-option v-for="item in orgParentList" :key="item.bizCode" :label="item.bizText" :value="item.bizCode" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-button
            type="primary"
            @click="
              searchForm.currentPage = 1
              getData()
            "
          >{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="resetForm('formSearch')">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <permission-btn label="add" icon="el-icon-plus" disabled type="primary" />
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="editDetail('edit', null)">{{ $t('btn.edit') }}</el-button>
    <el-button type="warning" icon="el-icon-load" :disabled="selectedList.length != 1" @click="resetPassword">{{ '重置密码' }}</el-button>
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
      <el-table-column prop="enableFlag" label="启用状态" min-width="90" align="center" />
      <el-table-column prop="remark" label="备注" :show-overflow-tooltip="true" max-width="220" align="center" />
      <el-table-column prop="createdTime" :label="$t('userManage.creationTime')" align="center" min-width="160" />
      <el-table-column prop="createdBy" :label="$t('userManage.creator')" align="center" min-width="160" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.currentPage" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="60%" :close-on-click-modal="false" center :visible.sync="userDialog" @close="closeDialog">
      <el-card>
        <div slot="header" class="clearfix">
          <span>{{ $t('userManage.baseInfo') }}</span>
        </div>
        <el-form ref="userForm" :model="dialogForm" :rules="userFormRules" label-width="120px" style="padding-right: 15px" :disabled="dialogTittle == 'view'">
          <el-row class="form_table">
            <el-col :span="12">
              <el-form-item :label="$t('userManage.userId')" prop="account">
                <el-input v-model="dialogForm.account" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="$t('userManage.userName')" prop="name">
                <el-input v-model="dialogForm.name" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="$t('userManage.mobile')" prop="mobile">
                <el-input v-model="dialogForm.mobile" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="$t('userManage.email')" prop="email">
                <el-input v-model="dialogForm.email" />
              </el-form-item>
            </el-col>
            <!-- <el-col :span="12">
              <el-form-item :label="$t('userManage.idCard')" prop="idCard">
                <el-input v-model="dialogForm.idCard" />
              </el-form-item>
            </el-col> -->
            <el-col v-if="dialogTittle != 'add'" :span="12">
              <el-form-item :label="$t('userManage.status')" prop="isDel">
                <el-select v-model="dialogForm.isDel" :placeholder="$t('placeholder.select')">
                  <el-option key="1" :label="$t('userManage.isDelete')" :value="1" />
                  <el-option key="0" :label="$t('userManage.noDelete')" :value="0" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="$t('userManage.tel')" prop="tel">
                <el-input v-model="dialogForm.tel" />
              </el-form-item>
            </el-col>
            <!-- <el-col :span="12">
              <el-form-item :label="$t('userManage.password')" prop="password">
                <el-input v-model="dialogForm.password" />
              </el-form-item>
            </el-col> -->
            <el-col :span="24">
              <el-form-item :label="$t('userManage.remark')" prop="remark">
                <el-input v-model="dialogForm.remark" type="textarea" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
      <!-- 角色信息关联 -->
      <!-- <user-role v-if="userDialog" ref="userRole" :roles="dialogForm.roles" :disabled="dialogTittle" /> -->
      <!-- <el-card v-if="roleList.length" style="margin-top:10px">
        <div slot="header" class="clearfix">
          <span>{{ $t('userManage.roleInfo') }}</span>
        </div>
        <el-checkbox v-model="checkAll" :disabled="dialogTittle=='view'" style="margin-bottom:10px" :indeterminate="isIndeterminate" @change="handleCheckAllChange">全选</el-checkbox>
        <el-checkbox-group v-model="checked" :disabled="dialogTittle=='view'" @change="handleCheckedChange">
          <el-checkbox v-for="item in roleList" :key="item.pkId" style="margin-bottom:5px" :label="item.pkId">{{ item.roleName }}</el-checkbox>
        </el-checkbox-group>
      </el-card> -->
      <div slot="footer" style="text-align: center">
        <el-button v-if="dialogTittle != 'view'" type="primary" plain @click="UserConfirm">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="userDialog = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { userAdd, userEdit, resetPassword, userDelete, userQuery } from '@/api/user-management'
export default {
  name: 'Organization',
  data() {
    return {
      selectedList: [],
      orgParentList: [], // 上级 组织下拉列表
      searchForm: {
        pageSize: 10,
        orgName: null,
        outOrgCode: null,
        outOrgParentCode: null,
        orgType: null,
        linkman: null,
        enableFlag: null,
        currentPage: 1,
      },
      tableList: [],
      total: 0,
      dialogTittle: 'view',
      userDialog: false,
      dialogForm: {
        account: '', // 用户id,
        name: '', //  用户名称',
        mobile: '', //  手机',
        email: '', //  用户邮箱',
        roles: [], //  所属角色',
        isDel: 0, //  状态',
        tel: '', //  电话',
        remark: '', //  备注',
      },
      userFormRules: {
        account: [
          { required: true, message: this.$t('placeholder.input'), trigger: 'blur' },
          { pattern: /[0-9a-zA-Z_]$/, message: this.$t('userManage.vilad') },
        ],
        name: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        email: [{ pattern: /^([a-zA-Z\d])(\w|\-)+@\S+\.[a-zA-Z]{2,4}$/, message: this.$t('userManage.viladEmail') }],
        isDel: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        mobile: [
          { required: true, message: this.$t('placeholder.input'), trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: this.$t('userManage.mobileVilid') },
        ],
      },
    }
  },
  methods: {
    UserConfirm() {
      this.$refs.userForm.validate((valid, obj) => {
        if (valid) {
          this.dialogForm.roles = this.$refs.userRole.selectedList.map((item) => {
            return item.pkId
          })
          if (this.dialogTittle == 'add') {
            userAdd(this.dialogForm).then((res) => {
              if (res.code === '2000') {
                this.closeDialog(true)
                return
              }
            })
          } else {
            userEdit(this.dialogForm).then((res) => {
              if (res.code === '2000') {
                this.closeDialog(true)
                return
              }
            })
          }
        } else {
          return
        }
      })
    },
    closeDialog(bool) {
      bool && this.getData()
      this.$refs['userForm'].resetFields()
      this.dialogForm.roles = []
      this.userDialog = false
    },
    resetPassword() {
      this.$confirm(this.$t('userManage.resetpsw'), this.$t('promptMessage.deleteTipTitle'), {
        type: 'warning',
      })
        .then(() => {
          resetPassword(this.selectedList[0].pkId).then((res) => {
            if (res.code === '2000') {
              this.getData()
              return
            }
          })
        })
        .catch(() => {})
    },
    // 删除操作
    handleDelete() {
      userDelete(this.selectedList[0].pkId).then((res) => {
        if (res.code === '2000') {
          this.getData()
          return
        }
      })
    },
    // 新建操作
    openCreateUser() {
      // this.getRoles()
      this.dialogTittle = 'add' // 新建
      this.userDialog = true
    },
    // 编辑和查看操作
    editDetail(title, row) {
      this.dialogTittle = title
      this.userDialog = true
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
    getData() {
      userQuery(this.searchForm).then((res) => {
        if (res.code == '2000') {
          this.tableList = res.data.list
          this.total = res.data.total
          return
        }
      })
    },
    // 选择项改变时
    handleSelectionChange(val) {
      this.selectedList = val
    },
    // 页码改变
    handleCurrentChange(currentPage) {
      this.searchForm.currentPage = currentPage
      this.getData()
    },
    // 每页size改变时
    handleSizeChange(val) {
      this.searchForm.currentPage = 1
      this.searchForm.pageSize = val
      this.getData()
    },
  },
}
</script>
