<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="8">
              <el-form-item :label="$t('userManage.type')" prop="type">
                <el-select v-model="searchForm.type" :placeholder="$t('placeholder.select')">
                  <el-option v-for="item in permissionTypes" :key="item.bizCode" :label="item.bizCode" :value="item.bizCode" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item :label="$t('userManage.typeName')" prop="type">
                <el-select v-model="searchForm.type" :placeholder="$t('placeholder.select')" disabled>
                  <el-option v-for="item in permissionTypes" :key="item.bizCode" :label="item.bizText" :value="item.bizCode" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item :label="$t('businessGlossary.isValid')" prop="isValid">
                <el-select v-model="searchForm.isValid" :placeholder="$t('placeholder.select')">
                  <el-option :key="0" :label="$t('businessGlossary.no')" :value="0" />
                  <el-option :key="1" :label="$t('businessGlossary.yes')" :value="1" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-button
            type="primary"
            @click="
              searchForm.pageNum = 1
              getData()
            "
          >{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="resetForm('formSearch')">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-button type="primary" icon="el-icon-plus" @click="openCreateUser">{{ $t('btn.add') }}</el-button>
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="editDetail('edit', null)">{{ $t('btn.edit') }}</el-button>
    <delete-btn :disabled="selectedList.length == 0" @handleDelete="handleDelete" />
    <el-table :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column :label="$t('userManage.type')" align="center" min-width="180">
        <template slot-scope="scope">
          <span class="view" @click="editDetail('view', scope.row)">{{ scope.row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="typeName" :label="$t('userManage.typeName')" align="center" min-width="180" />
      <el-table-column prop="name" :label="$t('userManage.name')" align="center" min-width="180" />
      <el-table-column prop="value" :label="$t('userManage.value')" align="center" min-width="180" />
      <el-table-column prop="attribute" :label="$t('userManage.attribute')" align="center" min-width="180" />
      <el-table-column prop="isValid" :label="$t('businessGlossary.isValid')" align="center" min-width="160">
        <template slot-scope="scope">
          {{ $t(`businessGlossary.${scope.row.isValid ? 'yes' : 'no'}`) }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" :label="$t('userManage.creationTime')" align="center" min-width="180" />
      <el-table-column prop="createUser" :label="$t('userManage.creator')" align="center" min-width="150" />
      <el-table-column prop="modifyTime" :label="$t('userManage.modifyTime')" align="center" min-width="140" />
      <el-table-column prop="modifyUser" :label="$t('userManage.modifyUser')" align="center" min-width="180" />
    </el-table>
    <el-pagination v-show="total > 0" small background :current-page.sync="searchForm.pageNum" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="40%" :close-on-click-modal="false" center :visible.sync="userDialog" @close="closeDialog">
      <el-form ref="premissionForm" :model="dialogForm" :rules="userFormRules" label-width="120px" style="padding-right: 15px" :disabled="dialogTittle == 'view'">
        <el-row class="form_table">
          <el-col :span="22">
            <el-form-item :label="$t('userManage.type')" prop="type">
              <!-- <el-input v-model="dialogForm.type" /> -->
              <el-select v-model="dialogForm.type" :placeholder="$t('placeholder.select')" :disabled="dialogTittle != 'add'">
                <el-option v-for="item in permissionTypes" :key="item.bizCode" :label="item.bizCode" :value="item.bizCode" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('userManage.typeName')">
              <!-- <el-input v-model="dialogForm.type" /> -->
              <el-select v-model="dialogForm.type" :placeholder="$t('placeholder.select')" disabled>
                <el-option v-for="item in permissionTypes" :key="item.bizCode" :label="item.bizText" :value="item.bizCode" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('userManage.name')" prop="name">
              <!-- <el-select v-model="dialogForm.type" :placeholder="$t('placeholder.select')" disabled>
                    <el-option v-for="item in permissionTypes" :key="item.bizCode" :label="item.bizText" :value="item.bizCode" />
                  </el-select> -->
              <el-input v-model="dialogForm.name" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('userManage.value')" prop="value">
              <el-input v-model="dialogForm.value" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('userManage.attribute')" prop="attribute">
              <el-input v-model="dialogForm.attribute" />
            </el-form-item>
          </el-col>

          <el-col :span="22">
            <el-form-item :label="$t('businessGlossary.isValid')" prop="isValid">
              <el-select v-model="dialogForm.isValid" :placeholder="$t('placeholder.select')">
                <el-option :key="0" :label="$t('businessGlossary.no')" :value="0" />
                <el-option :key="1" :label="$t('businessGlossary.yes')" :value="1" />
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
  </div>
</template>
<script>
import { getDictList } from '@/api/dict-data'
import { permissionAdd, permissionEdit, permissionDelete, getPermissionList } from '@/api/user-management'
export default {
  data() {
    return {
      permissionTypes: [],
      selectedList: [],
      searchForm: {
        isValid: null,
        type: '',
        pageSize: 10,
        pageNum: 1,
      },
      tableList: [],
      total: 0,

      dialogTittle: 'view',
      userDialog: false,
      dialogForm: {
        type: '', // '权限类型',
        name: '', // '权限名称',
        attribute: '', // 权限属性
        value: '', // '权限值',
        isValid: 1, // '状态'
      },
      userFormRules: {
        type: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        value: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        // attribute: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        name: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        isValid: [{ required: true, message: this.$t('placeholder.input'), trigger: 'change' }],
      },
    }
  },
  created() {
    this.getTypes()
  },
  methods: {
    getTypes() {
      getDictList('permission_type').then((res) => {
        if (res.code == '2000') {
          this.permissionTypes = res.data
          return
        }
      })
    },
    UserConfirm() {
      this.$refs.premissionForm.validate((valid, obj) => {
        if (valid) {
          if (this.dialogTittle == 'add') {
            permissionAdd(this.dialogForm).then((res) => {
              if (res.code == '2000') {
                this.getTypes()
                this.closeDialog(true)
                return
              }
            })
          } else {
            permissionEdit(this.dialogForm).then((res) => {
              if (res.code == '2000') {
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
      ;(this.dialogForm = {
        type: '', // '权限类型',
        name: '', // '权限名称',
        attribute: '', // 权限属性
        value: '', // '权限值',
        isValid: 1, // '状态'
      }),
      this.$refs.premissionForm.resetFields()
      this.userDialog = false
    },
    // 删除操作 做 提示
    handleDelete() {
      permissionDelete(this.selectedList[0].pkId).then((res) => {
        if (res.code == '2000') {
          this.getData()
          return
        }
      })
    },
    // 新建操作
    openCreateUser() {
      this.dialogTittle = 'add' // 新建
      this.userDialog = true
    },
    // 编辑和查看操作
    editDetail(title, row) {
      this.dialogTittle = title
      this.dialogForm = JSON.parse(JSON.stringify(row || this.selectedList[0]))
      this.userDialog = true
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.tableList = []
      this.total = 0
    },
    // 查询
    getData() {
      this.$refs.formSearch.validate((valid) => {
        if (valid) {
          getPermissionList(this.searchForm).then((res) => {
            if (res.code == '2000') {
              this.tableList = res.data.list
              this.total = res.data.total
              return
            }
          })
        } else {
          return
        }
      })
    },
    // 选择项改变时
    handleSelectionChange(val) {
      this.selectedList = val
    },
    // 页码改变
    handleCurrentChange(pageNum) {
      this.searchForm.pageNum = pageNum
      this.getData()
    },
    // 每页size改变时
    handleSizeChange(val) {
      this.searchForm.pageNum = 1
      this.searchForm.pageSize = val
      this.getData()
    },
  },
}
</script>
