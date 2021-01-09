<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container">
      <el-input v-model="selectInput.keyword" class="input-with-select filter-item" @change="handlerInputchange" style="width: 220px;" placeholder="请输入关键字" >
        <el-select slot="prepend" clearable v-model="selectInput.keyname" placeholder="请选择">
          <el-option label="角色名称" value="roleName"/>
          <el-option label="角色说明" value="roleDesc"/>
        </el-select>
      </el-input>
      <code-select v-model="params.enableFlag" dictname="ENABLE_FLAG" mystyle="width: 120px;" placeholder="启用状态"/>
      <el-button v-if="hasPermission('roleManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="hasPermission('roleManage:add')" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="handleClickAddOrEdit(null)">添加</el-button>
    </div>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" width="60" type="index">
      </el-table-column>
      <el-table-column label="角色名称" width="130" align="center">
        <template slot-scope="scope">
          {{ scope.row.roleName }}
        </template>
      </el-table-column>
      <el-table-column label="角色说明" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.roleDesc }}</span>
        </template>
      </el-table-column>
      <el-table-column label="启用状态" width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.enableFlag | basecode('ENABLE_FLAG') }}
        </template>
      </el-table-column>
      <el-table-column label="创建人" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.createdBy }}
        </template>
      </el-table-column>
      <el-table-column label="创建日期" width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.createdTime | formatTimestamp }}
        </template>
      </el-table-column>
      <el-table-column label="更新人" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.updatedBy}}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.updatedTime | formatTimestamp }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="240" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="设定角色权限" placement="top">
            <el-button :circle="true" :plain="true" type="success" icon="el-icon-user" size="mini" @click="handleClickConfigRole(scope.row.roleId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="设定角色组织" placement="top">
            <el-button :circle="true" :plain="true" type="warning" icon="el-icon-setting" size="mini" @click="handleClickConfigRoleOrg(scope.row.roleId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="设定角色组织用户" placement="top">
            <el-button :circle="true" :plain="true" type="info" icon="el-icon-s-custom" size="mini" @click="handleClickConfigRoleuser(scope.row.roleId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button v-if="hasPermission('roleManage:find') && !hasPermission('roleManage:edit')" :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="handleClickAddOrEdit(scope.row.roleId, 'find')"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button v-if="hasPermission('roleManage:edit')" :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.roleId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button v-if="hasPermission('roleManage:delete')" :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row.roleId)"/>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        :total="totalCount"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="params.pageSize"
        :current-page="params.currentPage"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
    </div>
    <!-- 设定角色权限 -->
    <el-dialog :visible.sync="dialogVisibleForRoleConfigRole" title="设定角色权限" >
      <el-form :model="form">
        <el-form-item>
          <div class="box-height">
            <el-tree ref="roleTree" :data="form.treeData" :default-checked-keys="form.checkedIds" :default-expanded-keys="form.checkedIds" show-checkbox node-key="id"/>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleClickSaveConfigRole">确定</el-button>
          <el-button @click="dialogVisibleForRoleConfigRole=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 关联组织 -->
    <el-dialog :visible.sync="dialogVisibleForRoleConfigOrg" title="关联组织" >
      <el-form :model="form">
        <el-form-item>
          <div class="box-height">
           <el-tree ref="roleOrgTree" :data="form.treeData" :default-checked-keys="form.checkedIds" :default-expanded-keys="form.checkedIds" show-checkbox node-key="id"/>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleClickSaveConfigRoleOrg">确定</el-button>
          <el-button @click="dialogVisibleForRoleConfigOrg=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 关联用户 -->
    <el-dialog :visible.sync="dialogVisibleForRoleConfigUser" title="关联用户" >
      <el-form :model="form">
        <el-form-item>
          <div class="box-height">
           <el-tree ref="roleUserTree" :data="form.treeData" :default-checked-keys="form.checkedIds" :default-expanded-keys="form.checkedIds" show-checkbox node-key="id"/>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleClickSaveConfigRoleUser">确定</el-button>
          <el-button @click="dialogVisibleForRoleConfigUser=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { queryByPage, deleteById, queryMenuActionTreeForRole, saveMenuActionTreeForRole, queryOrgTreeForRole, saveOrgTreeForRole,queryUserOrgTreeForRole,saveOrgTreeForUserRole } from '@/api/authority/role'
import CodeSelect from '@/components/codeSelect'

export default {
  name:'roleList',
  components: {
    CodeSelect
  },
  data() {
    return {
      selectInput:{
        keyname: 'roleName'
      },
      params: {
        currentPage: 1,
        pageSize: 10,

        userName: null,
        nikeName: null,
        merchantName: null,
        userType: null,
        enableFlag: null
      },
      list: null,
      totalCount: 0,
      totalPage: 0,
      listLoading: true,

      //角色菜单按钮对话框
      dialogVisibleForRoleConfigRole: false,
      form: {
        roleId: null,
        menuActionIds: [],
        treeData: [],
        checkedIds: [],
        ignoreKeyList: ['treeData']   // TODO 测试数据
      },

      //角色关联组织对话框
      dialogVisibleForRoleConfigOrg: false,
      
      // 角色关联用户
      dialogVisibleForRoleConfigUser: false,
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path == "/authority/role/edit") {
      to.meta.isBack = true;
    } else {
      to.meta.isBack = false;
    }
    next();
  },
  activated() {
    if (!this.$route.meta.isBack) {
      /*清空查询条件 并查询列表*/
      this.selectInput.keyword = ''
      this.resetForm(this.params)
      this.params.currentPage = 1
      this.params.pageSize = 10
    }else{
      this.$route.meta.isBack = false;
      // this.queryByPage(); /*仅列表重新加载*/
    }
    this.queryByPage()
  },
  created() {
    this.queryByPage()
  },
  methods: {
    queryByPage() {
      this.listLoading = true
      queryByPage(this.params).then(response => {
        if (response.repCode == '0000') {
          this.list = response.repData.list
          this.totalCount = response.repData.totalCount
          this.totalPage = response.repData.totalPage
          this.params.currentPage = response.repData.currentPage
          this.params.pageSize = response.repData.pageSize
        }
        this.listLoading = false
      })
    },
    handleSizeChange(val) {
      this.params.pageSize = val
      this.queryByPage()
    },
    handleCurrentChange(val) {
      this.params.currentPage = val
      this.queryByPage()
    },
    handleClickAddOrEdit(id, val="") {
      this.$router.push({ path: '/authority/role/edit', query: { id: id, val: val }})
    },
    handleClickDelete(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById(id).then(response => {
          if (response.repCode == '0000') {
            this.$message({ message: '删除成功', type: 'success', duration: 1500 })
            this.search()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    //设定角色菜单按钮
    handleClickConfigRole(roleId) {
      this.form.roleId = roleId
      queryMenuActionTreeForRole(roleId).then(response => {
        if (response.repCode == '0000') {
          this.form.checkedIds = response.repData.checkedIds
          this.form.treeData = response.repData.treeData
          this.dialogVisibleForRoleConfigRole = true
        }
      })
    },
    handleClickSaveConfigRole() {
      var checkIds = this.$refs.roleTree.getCheckedKeys(true)
      this.form.menuActionIds = checkIds;
      let params={
        checkedIds:this.form.checkedIds,
        menuActionIds:this.form.menuActionIds,
        orgIds:this.form.orgIds,
        roleId:this.form.roleId
      }
      saveMenuActionTreeForRole(params).then(response => {
        if (response.repCode == '0000') {
          this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
        }
        this.dialogVisibleForRoleConfigRole = false
      })
    },
    //设定角色组织
    handleClickConfigRoleOrg(roleId) {
      this.form.roleId = roleId
      queryOrgTreeForRole(roleId).then(response => {
        if (response.repCode == '0000') {
          this.form.checkedIds = response.repData.checkedIds
          this.form.treeData = response.repData.treeData
          this.dialogVisibleForRoleConfigOrg = true
        }
      })
    },
    handleClickSaveConfigRoleOrg() {
      this.$confirm("当前操作可能会导致用户已勾选的关联组织权限遗失，请谨慎操作", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
      .then(() => {
      // var checkIds = this.$refs.roleOrgTree.getCheckedKeys(true)
      var checkIds;
      var allChecked = this.$refs.roleOrgTree.getCheckedKeys().concat(this.$refs.roleOrgTree.getHalfCheckedKeys())
      if(this.$refs.roleOrgTree.getHalfCheckedKeys(true).length > 0) {
        checkIds = allChecked.filter(v => !this.$refs.roleOrgTree.getHalfCheckedKeys(true).includes(v))
      } else {
        checkIds = allChecked
      }
      this.form.orgIds = checkIds;
      let params={
        checkedIds:this.form.checkedIds,
        menuActionIds:this.form.menuActionIds,
        orgIds:this.form.orgIds,
        roleId:this.form.roleId
      }
      saveOrgTreeForRole(params).then(response => {
        if (response.repCode == '0000') {
          this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
        }
        this.dialogVisibleForRoleConfigOrg = false
      })
      .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
      })
    },
    // 设定角色组织用户
    handleClickConfigRoleuser(roleId) {
      this.form.roleId = roleId
      queryUserOrgTreeForRole(roleId).then(response => {
        if (response.repCode == '0000') {
          this.form.checkedIds = response.repData.checkedIds
          this.form.treeData = response.repData.treeData
          this.dialogVisibleForRoleConfigUser = true
        }
      })
    },
    handleClickSaveConfigRoleUser() {
      this.$confirm("当前操作可能会导致用户已勾选的关联组织权限遗失，请谨慎操作", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
      .then(() => {
      var checkIds = this.$refs.roleUserTree.getCheckedKeys(true)
      this.form.orgIds = checkIds;
      let params={
        checkedIds:this.form.checkedIds,
        menuActionIds:this.form.menuActionIds,
        userIdOrgIds:this.form.orgIds,
        orgIds: [],
        roleId:this.form.roleId
      }
      saveOrgTreeForUserRole(params).then(response => {
        if (response.repCode == '0000') {
          this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
        }
        this.dialogVisibleForRoleConfigUser = false
      })
      .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
      })
    }
  }
}
</script>
<style scoped>
.box-height{
  height:50vh;
  overflow:auto
}
</style>