<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item label="登录名" prop="username">
                <el-input v-model="searchForm.username" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="真实姓名" prop="nickname">
                <el-input v-model="searchForm.nickname" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="手机号" prop="phone">
                <el-input v-model="searchForm.phone" />
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
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="setRole">设定角色</el-button>
    <delete-btn :disabled="selectedList.length != 1" @handleDelete="handleDelete" />
    <el-table :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column label="登录名" min-width="110" align="center">
        <template slot-scope="scope">
          <span class="view" @click="editDetail('view', scope.row)">{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="真实姓名" min-width="110" align="center" />
      <el-table-column prop="phone" label="手机号码" min-width="110" align="center" />
      <el-table-column prop="email" label="用户邮箱" min-width="180" align="center" />
      <el-table-column prop="enabled" label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.enabled ? '启用' : '禁用' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" :label="$t('userManage.creationTime')" align="center" min-width="120" />
      <el-table-column prop="createBy" :label="$t('userManage.creator')" align="center" min-width="100" />
      <el-table-column prop="updateTime" :label="$t('userManage.modifyTime')" align="center" min-width="120" />
      <el-table-column prop="updateBy" :label="$t('userManage.modifyUser')" align="center" min-width="100" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.pageNumber" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="50%" :close-on-click-modal="false" center :visible.sync="basicDialog" @close="closeDialog">
      <el-form ref="userForm" :model="dialogForm" :rules="formRules" label-width="100px" :disabled="dialogTittle == 'view'">
        <el-row class="form_table">
          <el-col :span="12">
            <el-form-item label="登录名" prop="username">
              <el-input v-model.trim="dialogForm.username" :disabled="dialogTittle != 'add'" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="真实姓名" prop="nickname">
              <el-input v-model.trim="dialogForm.nickname" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('userManage.tel')" prop="phone">
              <el-input v-model="dialogForm.phone" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('userManage.email')" prop="email">
              <el-input v-model="dialogForm.email" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('userManage.password')" prop="password">
              <el-input v-model="dialogForm.password" />
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
        </el-row>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button v-if="dialogTittle != 'view'" type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="basicDialog = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
    <set-role :id="id" :visib="treeDialog" @handleClose="treeDialog = false" />
  </div>
</template>
<script>
import { getUserList, addUser, editUser, deleteUser } from '@/api/authority'
export default {
  name: 'User',
  components: {
    SetRole: require('./components/SetRole').default,
  },
  data() {
    return {
      id: null,
      treeDialog: false,
      selectedList: [],
      searchForm: {
        username: null,
        nickname: null,
        phone: null,
        enabled: null,
        pageNumber: 1,
        pageSize: 10,
      },
      tableList: [],
      total: 0,
      dialogTittle: 'view',
      basicDialog: false,
      dialogForm: {
        email: null,
        username: null,
        nickname: null,
        password: null,
        phone: null,
        enabled: 1,
      },
      formRules: {
        username: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        nickname: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        phone: [
          { required: false, message: this.$t('placeholder.input'), trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: this.$t('userManage.mobileVilid'), trigger: 'blur' },
        ],
        email: [{ pattern: /^([a-zA-Z\d])(\w|\-)+@\S+\.[a-zA-Z]{2,4}$/, message: this.$t('userManage.viladEmail'), trigger: 'blur' }],
      },
    }
  },
  methods: {
    // 分配角色
    setRole() {
      this.id = this.selectedList[0].username
      this.treeDialog = true
    },
    // 提交按钮
    confirm() {
      this.$refs.userForm.validate(async(valid, obj) => {
        if (valid) {
          if (this.dialogTittle == 'add') {
            const { code } = await addUser(this.dialogForm)
            if (code != '200') return
            this.closeDialog(true)
          } else {
            const { code } = await editUser(this.dialogForm)
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
      const { code } = await deleteUser(this.selectedList[0].id)
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
      const { data, code } = await getUserList(this.searchForm)
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
