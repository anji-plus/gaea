<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item :label="$t('userManage.roleName')" prop="roleName">
                <el-input v-model="searchForm.roleName" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="角色说明" prop="remark">
                <el-input v-model="searchForm.remark" />
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
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="openMenu">设定菜单</el-button>
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="setOrg">设定组织</el-button>
    <delete-btn :disabled="selectedList.length != 1" @handleDelete="handleDelete" />
    <el-table :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column :label="$t('userManage.roleName')" align="center" min-width="180">
        <template slot-scope="scope">
          <span class="view" @click="editDetail('view', scope.row)">{{ scope.row.roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="remark" :label="$t('userManage.described')" align="center" min-width="160" />
      <el-table-column prop="enabled" label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.enabled ? '启用' : '禁用' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" :label="$t('userManage.creationTime')" align="center" min-width="160" />
      <el-table-column prop="createBy" :label="$t('userManage.creator')" align="center" min-width="160" />
      <el-table-column prop="updateTime" :label="$t('userManage.modifyTime')" align="center" min-width="180" />
      <el-table-column prop="updateBy" :label="$t('userManage.modifyUser')" align="center" min-width="140" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.pageNumber" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="80%" :close-on-click-modal="false" center :visible.sync="userDialog" @close="closeDialog">
      <el-form ref="roleForm" :model="dialogForm" :rules="userFormRules" label-width="110px" style="padding-right: 15px" :disabled="dialogTittle == 'view'">
        <el-row class="form_table">
          <el-col :span="8">
            <el-form-item label="角色编号" prop="roleCode">
              <el-input v-model="dialogForm.roleCode" :disabled="dialogTittle != 'add'" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item :label="$t('userManage.roleName')" prop="roleName">
              <el-input v-model="dialogForm.roleName" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="角色说明" prop="remark">
              <el-input v-model="dialogForm.remark" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="启用状态" prop="enabled">
              <el-select v-model="dialogForm.enabled" :placeholder="$t('placeholder.select')">
                <el-option key="1" label="启用" :value="1" />
                <el-option key="0" label="禁用" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button v-if="dialogTittle != 'view'" type="primary" plain @click="UserConfirm">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="userDialog = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
    <role-org :id="id" :visib="orgDialog" @handleClose="orgDialog = false" />
    <role-menu :id="id" :visib="menuDialog" @handleClose="menuDialog = false" />
  </div>
</template>
<script>
import { getRoleList, addRole, editRole, deleteRole } from '@/api/authority'
export default {
  components: {
    RoleMenu: require('./components/RoleMenu').default,
    RoleOrg: require('./components/RoleOrg').default,
  },
  data() {
    return {
      orgDialog: false,
      menuDialog: false,
      id: null,
      selectedList: [],
      searchForm: {
        // roleCode: '',
        roleName: '',
        status: '', // normal: 已激活 invalid: 未激活
        pageSize: 10,
        pageNumber: 1,
      },
      tableList: [],
      total: 0,
      dialogTittle: 'view',
      userDialog: false,
      dialogForm: {
        remark: '', // 描述
        menuIds: [], // 菜单id
        roleCode: '', // 角色代码
        // roleCode: 'ROLE_', // 角色代码
        roleName: '', // 角色名称
        enabled: 1, // 状态
        // permissionIds: [], // 权限id
      },
      userFormRules: {
        roleCode: [
          { required: true, message: this.$t('placeholder.input'), trigger: 'blur' },
          // { pattern: /^ROLE_([0-9A-Z_-]){1,20}$/, message: this.$t('userManage.roleRule'), trigger: 'blur' },
        ],
        roleName: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
      },
    }
  },
  methods: {
    // 分配组织
    setOrg() {
      this.id = this.selectedList[0].roleCode
      this.orgDialog = true
    },
    openMenu() {
      this.id = this.selectedList[0].roleCode
      this.menuDialog = true
    },
    // 提交
    UserConfirm() {
      this.$refs.roleForm.validate(async(valid, obj) => {
        if (valid) {
          if (this.dialogTittle == 'add') {
            const { code } = await addRole(this.dialogForm)
            if (code != '200') return
            this.closeDialog(true)
          } else {
            const { code } = await editRole(this.dialogForm)
            if (code != '200') return
            this.closeDialog(true)
          }
        } else {
          return
        }
      })
    },
    closeDialog(bool) {
      bool && this.getData()
      this.checkedKeys = []
      // this.$refs.roleTree.setCheckedKeys([])
      this.$refs['roleForm'].resetFields()
      this.userDialog = false
    },
    // 删除操作 做 提示
    async handleDelete() {
      const { code } = await deleteRole(this.selectedList[0].id)
      if (code != '200') return
      this.getData()
    },
    // 新建操作
    openCreateUser() {
      this.dialogTittle = 'add' // 新建
      this.userDialog = true
    },

    // 编辑和查看操作
    editDetail(title, row) {
      this.dialogTittle = title

      this.userDialog = true
      this.$nextTick(() => {
        this.dialogForm = JSON.parse(JSON.stringify(row || this.selectedList[0]))
        // 赋值到权限列表绑定的list上
        // 将菜单非叶子节点的menuId过滤出来
        // this.deleteParentMenuIds(this.getCheckedId())
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
      const { data, code } = await getRoleList(this.searchForm)
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
