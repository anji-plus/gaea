<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item label="菜单代码" prop="menuCode">
                <el-input v-model="searchForm.menuCode" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="菜单名称" prop="menuName">
                <el-input v-model="searchForm.menuName" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="路由地址" prop="path">
                <el-input v-model="searchForm.path" />
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
              <el-form-item label="系统终端" prop="sysCode">
                <el-select v-model="searchForm.sysCode" :placeholder="$t('placeholder.select')">
                  <el-option key="1" label="PC端" value="PC" />
                  <el-option key="2" label="APP端" value="APP" />
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
    <el-button type="primary" icon="el-icon-setting" :disabled="selectedList.length != 1" @click="related">按钮关联</el-button>
    <delete-btn :disabled="selectedList.length != 1" @handleDelete="handleDelete" />
    <el-table :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column label="菜单代码" min-width="140" align="center">
        <template slot-scope="scope">
          <span class="view" @click="editDetail('view', scope.row)">{{ scope.row.menuCode }}</span>
        </template>
      </el-table-column>
      <el-table-column label="父级菜单代码" prop="parentCode" min-width="110" align="center" />

      <el-table-column prop="menuName" label="菜单名称" min-width="140" align="center" />
      <el-table-column prop="sysCode" label="系统终端代码" min-width="110" align="center" />
      <el-table-column prop="path" label="路由地址" min-width="110" align="center" />
      <el-table-column prop="createTime" :label="$t('userManage.creationTime')" align="center" min-width="160" />
      <el-table-column prop="createBy" :label="$t('userManage.creator')" align="center" min-width="160" />
      <el-table-column prop="updateTime" :label="$t('userManage.modifyTime')" align="center" min-width="180" />
      <el-table-column prop="updateBy" :label="$t('userManage.modifyUser')" align="center" min-width="140" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.pageNumber" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="40%" :close-on-click-modal="false" center :visible.sync="basicDialog" @close="closeDialog">
      <el-form ref="userForm" :model="dialogForm" :rules="formRules" label-width="100px" style="padding-right: 15px" :disabled="dialogTittle == 'view'">
        <el-row class="form_table">
          <el-col :span="12">
            <el-form-item label="菜单代码" prop="menuCode">
              <el-input v-model="dialogForm.menuCode" :disabled="dialogTittle != 'add'" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input v-model="dialogForm.menuName" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="父菜单代码" prop="parentCode">
              <el-input v-model="dialogForm.parentCode" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="系统终端" prop="sysCode">
              <el-select v-model="dialogForm.sysCode" :placeholder="$t('placeholder.select')">
                <el-option key="1" label="PC端" value="PC" />
                <el-option key="2" label="APP端" value="APP" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="路由地址" prop="path">
              <el-input v-model="dialogForm.path" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图标" prop="menuIcon">
              <el-input v-model="dialogForm.menuIcon" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文件路径" prop="component">
              <el-input v-model="dialogForm.component" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sort">
              <el-input v-model="dialogForm.sort" type="Number" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="重定向地址" prop="redirectUrl">
              <el-input v-model="dialogForm.redirectUrl" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="隐藏菜单" prop="hidden">
              <el-select v-model="dialogForm.hidden" :placeholder="$t('placeholder.select')">
                <el-option key="1" label="是" :value="1" />
                <el-option key="0" label="否" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="始终展示" prop="alwaysShow">
              <el-select v-model="dialogForm.alwaysShow" :placeholder="$t('placeholder.select')">
                <el-option key="1" label="是" :value="1" />
                <el-option key="0" label="否" :value="0" />
              </el-select>
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
          <!-- <el-col :span="12">
            <el-form-item label="删除状态" prop="deleteFlag">
              <el-select v-model="dialogForm.deleteFlag" :placeholder="$t('placeholder.select')">
                <el-option key="0" label="未删除" :value="0" />
                <el-option key="1" label="已删除" :value="1" />
              </el-select>
            </el-form-item>
          </el-col> -->
        </el-row>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button v-if="dialogTittle != 'view'" type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="basicDialog = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="relatedBtnDialog" title="菜单关联操作按钮">
      <el-form :model="form">
        <el-form-item>
          <el-tree ref="actionTree" :data="form.treeData" :default-checked-keys="form.checkedIds" :default-expanded-keys="form.checkedIds" show-checkbox node-key="id" />
        </el-form-item>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button type="primary" plain @click="handleClickRoleGrantRoleSave">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="relatedBtnDialog = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { queryActionTreeForMenu, saveActionTreeForMenu, getMenuList, addMenu, editMenu, deleteMenu } from '@/api/authority'
export default {
  data() {
    return {
      selectedList: [],
      searchForm: {
        menuCode: null,
        menuName: null,
        path: null,
        sysCode: null,
        enabled: null,
        pageNumber: 1,
        pageSize: 10,
      },
      tableList: [],
      total: 0,
      dialogTittle: 'view',
      basicDialog: false,
      dialogForm: {
        menuCode: '', // 菜单代码
        menuName: '', // 菜单名称
        sysCode: '', // 系统代码
        parentCode: '',
        path: '',
        menuIcon: '',
        sort: '',
        enabled: 1, // 0禁用，1启用
        deleteFlag: 0, // 0未删，1已删除
        redirectUrl: '',
        component: '',
        hidden: 0,
        alwaysShow: 1,
      },
      formRules: {
        menuCode: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
      },
      relatedBtnDialog: false,
      form: {
        menuCode: null,
        actionCodes: [],
        treeData: [],
        checkedIds: [],
      },
    }
  },
  methods: {
    // 关联按钮操作
    async related() {
      this.form.menuCode = this.selectedList[0].menuCode
      const { data, code } = await queryActionTreeForMenu(this.form.menuCode)
      if (code != '200') return
      this.form.checkedIds = data.checkedCodes
      this.form.treeData = data.treeDatas
      this.relatedBtnDialog = true
    },
    handleClickRoleGrantRoleSave() {
      this.$confirm('当前操作可能会导致角色已勾选的菜单按钮权限遗失，请谨慎操作', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async() => {
        this.form.actionCodes = this.$refs.actionTree.getCheckedKeys(true)
        const { code } = await saveActionTreeForMenu(this.form)
        if (code != '200') return
        this.relatedBtnDialog = false
      })
    },
    // 提交按钮
    confirm() {
      this.$refs.userForm.validate(async(valid, obj) => {
        if (valid) {
          if (this.dialogTittle == 'add') {
            const { code } = await addMenu(this.dialogForm)
            if (code != '200') return
            this.closeDialog(true)
          } else {
            const { code } = await editMenu(this.dialogForm)
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
      const { code } = await deleteMenu(this.selectedList[0].id)
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
      const { data, code } = await getMenuList(this.searchForm)
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
