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
              <el-form-item label="菜单路径" prop="menuUrl">
                <el-input v-model="searchForm.menuUrl" />
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
              <el-form-item label="系统终端" prop="sysCode">
                <el-select v-model="searchForm.sysCode" :placeholder="$t('placeholder.select')">
                  <el-option key="1" label="PC端" :value="1" />
                  <el-option key="2" label="APP端" :value="2" />
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
    <el-button type="primary" icon="el-icon-plus" @click="openCreateUser">{{ $t('btn.add') }}</el-button>
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="editDetail('edit', null)">{{ $t('btn.edit') }}</el-button>
    <el-button type="primary" icon="el-icon-setting" :disabled="selectedList.length != 1" @click="related">按钮关联</el-button>
    <delete-btn :disabled="selectedList.length != 1" @handleDelete="handleDelete" />
    <el-table :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column label="菜单代码" min-width="140" align="center">
        <template slot-scope="scope">
          <span class="view" @click="editDetail('view', scope.row)">{{ scope.row.menuId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="父级菜单" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.parentMenuName ? scope.row.parentMenuName : '顶级' }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="菜单名称" min-width="110" align="center" />
      <el-table-column prop="sysName" label="系统终端" min-width="110" align="center" />
      <el-table-column prop="path" label="路由地址" min-width="110" align="center" />
      <el-table-column prop="createdTime" :label="$t('userManage.creationTime')" align="center" min-width="160" />
      <el-table-column prop="createdBy" :label="$t('userManage.creator')" align="center" min-width="160" />
      <el-table-column prop="updatedTime" :label="$t('userManage.modifyTime')" align="center" min-width="180" />
      <el-table-column prop="updatedBy" :label="$t('userManage.modifyUser')" align="center" min-width="140" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.currentPage" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="40%" :close-on-click-modal="false" center :visible.sync="basicDialog" @close="closeDialog">
      <el-form ref="userForm" :model="dialogForm" :rules="formRules" label-width="100px" style="padding-right: 15px" :disabled="dialogTittle == 'view'">
        <el-row class="form_table">
          <el-col :span="12">
            <el-form-item label="菜单ID" prop="menuId">
              <el-input v-model="dialogForm.menuId" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="父菜单ID" prop="parentMenuId">
              <el-input v-model="dialogForm.parentMenuId" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label=" 菜单名称" prop="title">
              <el-input v-model="dialogForm.title" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="路由地址" prop="path">
              <el-input v-model="dialogForm.path" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="路由name" prop="name">
              <el-input v-model="dialogForm.name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="icon" prop="icon">
              <el-input v-model="dialogForm.icon" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文件路径" prop="component">
              <el-input v-model="dialogForm.component" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="重定向地址" prop="redirect">
              <el-input v-model="dialogForm.redirect" />
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
            <el-form-item label="系统终端" prop="sysCode">
              <el-select v-model="dialogForm.sysCode" :placeholder="$t('placeholder.select')">
                <el-option key="1" label="PC端" :value="1" />
                <el-option key="2" label="APP端" :value="2" />
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
// import { queryActionTreeForMenu } from '@/api/authority'
export default {
  data() {
    return {
      selectedList: [],
      searchForm: {
        menuCode: null,
        menuName: null,
        menuUrl: null,
        sysCode: null,
        enableFlag: null,
        currentPage: 1,
        pageSize: 10,
      },
      tableList: [
        {
          parentMenuName: '', // 父级菜单名称
          title: '首页', // 菜单名称（该字段需要需要展示，需要考虑国际化问题）
          sysCode: 1,
          sysName: 'PC端',
          menuId: 10, // 菜单id/code
          parentMenuId: '', // 父级菜单id
          path: '/dashboard', // 路由地址
          redirect: '/dashboard/redirect', // 重定向的地址
          component: '/dashboard/index', // 该路由需要加载的页面组件的文件路径
          hidden: 0, // 是否隐藏菜单 true为隐藏 默认false
          alwaysShow: 1, // 是否一直展示
          name: 'Dashboard',
          icon: 'iconSvg', // 菜单图标
        },
      ],
      total: 1,
      dialogTittle: 'view',
      basicDialog: false,
      dialogForm: {
        title: null, // 菜单名称（该字段需要需要展示，需要考虑国际化问题）
        sysCode: 1,
        menuId: null, // 菜单id/code
        parentMenuId: null, // 父级菜单id
        path: null, // 路由地址
        redirect: null, // 重定向的地址
        component: null, // 该路由需要加载的页面组件的文件路径
        hidden: 0, // 是否隐藏菜单 true为隐藏 默认false
        alwaysShow: 1, // 是否一直展示
        name: null,
        icon: null, // 菜单图标
      },
      formRules: {
        menuId: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
      },
      relatedBtnDialog: false,
      form: {
        menuId: null,
        actionIds: [],
        treeData: [],
        checkedIds: [],
      },
    }
  },
  methods: {
    // 关联按钮操作
    related() {
      this.form.menuId = this.selectedList[0].menuId
      // queryActionTreeForMenu(menuId).then(response => {
      //   if (response.repCode == '0000') {
      var res = {
        checkedIds: [1, 2, 3, 4, 22],
        treeData: [
          {
            id: 1,
            label: '新增',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
          {
            id: 2,
            label: '修改',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
          {
            id: 3,
            label: '删除',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
          {
            id: 4,
            label: '查询',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
          {
            id: 11,
            label: '分配权限',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
          {
            id: 12,
            label: '分配角色',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
          {
            id: 13,
            label: '重置密码',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
          {
            id: 21,
            label: '导入',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
          {
            id: 22,
            label: '导出',
            disabled: false,
            children: [],
            extend: null,
            parentId: null,
            name: null,
            value: null,
            type: null,
            checked: false,
            expend: false,
          },
        ],
      }
      this.form.checkedIds = res.checkedIds
      this.form.treeData = res.treeData
      this.relatedBtnDialog = true
      //   }
      // })
    },
    handleClickRoleGrantRoleSave() {
      this.$confirm('当前操作可能会导致角色已勾选的菜单按钮权限遗失，请谨慎操作', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        var checkIds = this.$refs.actionTree.getCheckedKeys(true)
        this.form.actionIds = checkIds
        // saveActionTreeForMenu(this.form).then(response => {
        //   if (response.repCode == '0000') {
        //     this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
        //   }
        this.dialogVisibleForMenuActionTree = false
        // })
        // .catch(() => {
        //     this.$message({
        //       type: "info",
        //       message: "已取消"
        //     });
        //   });
      })
    },
    // 提交按钮
    confirm() {
      this.$refs.userForm.validate((valid, obj) => {
        if (valid) {
          if (this.dialogTittle == 'add') {
            // userAdd(this.dialogForm).then(res => {
            //   if (res.code === '2000') {
            this.closeDialog(true)
            //     return
            //   }
            // })
          } else {
            // userEdit(this.dialogForm).then(res => {
            //   if (res.code === '2000') {
            this.closeDialog(true)
            //     return
            //   }
            // })
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
    handleDelete() {
      // userDelete(this.selectedList[0].pkId).then(res => {
      //   if (res.code === '2000') {
      this.getData()
      return
      //   }
      // })
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
    getData() {
      // listData(this.searchForm).then((res) => {
      //   if (res.code == '2000') {
      //     this.tableList = res.data.list
      //     this.total = res.data.total
      //     return
      //   }
      // })
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
