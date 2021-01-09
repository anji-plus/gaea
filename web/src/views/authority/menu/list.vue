<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container">
      <el-input v-model="selectInput.keyword" class="input-with-select filter-item" @change="handlerInputchange" style="width: 220px;" placeholder="请输入关键字" >
        <el-select slot="prepend" clearable v-model="selectInput.keyname" placeholder="请选择">
          <el-option label="菜单代码" value="menuCode"/>
          <el-option label="菜单名称" value="menuName"/>
          <el-option label="菜单路径" value="menuUrl"/>
        </el-select>
      </el-input>
      <code-select v-model="params.enableFlag" dictname="ENABLE_FLAG" mystyle="width: 120px;" placeholder="启用状态"/>
      <code-select v-model="params.sysCode" dictname="SYSTEM_CODE" mystyle="width: 120px;" placeholder="系统代码"/>
      <el-button v-if="hasPermission('menuManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="hasPermission('menuManage:add')" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="handleClickAddOrEdit(null)">添加</el-button>
    </div>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" width="60" type="index">
      </el-table-column>
      <el-table-column label="父级菜单" min-width="110" align="center">
        <template slot-scope="scope">
          {{scope.row.parentMenuName?scope.row.parentMenuName:"顶级"}}
        </template>
      </el-table-column>
      <el-table-column label="菜单代码" min-width="140" align="center">
        <template slot-scope="scope">
          {{ scope.row.menuCode }}
        </template>
      </el-table-column>
      <el-table-column label="菜单名称" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.menuName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="系统终端" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.sysCode | basecode('SYSTEM_CODE')}}
        </template>
      </el-table-column>
      <!-- <el-table-column label="菜单路径" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.menuUrl }}
        </template>
      </el-table-column> -->
      <el-table-column label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.enableFlag | basecode('ENABLE_FLAG') }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="备注" :show-overflow-tooltip="true" align="center">
        <template slot-scope="scope">
          {{scope.row.remark?scope.row.remark:"无"}}
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="推荐人" width="100" align="center">
        <template slot-scope="scope">
          {{scope.row.recommender?scope.row.recommender:"无"}}
        </template>
      </el-table-column> -->
      <el-table-column label="创建人" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.createdBy }}
        </template>
      </el-table-column>
      <el-table-column label="创建日期" min-width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.createdTime | formatTimestamp }}
        </template>
      </el-table-column>
      <el-table-column label="更新人" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.updatedBy}}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" min-width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.updatedTime | formatTimestamp }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="140" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="关联操作" placement="top">
            <el-button :circle="true" :plain="true" type="warning" icon="el-icon-setting" size="mini" @click="handleClickGrantAction(scope.row.menuId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button v-if="hasPermission('menuManage:find') && !hasPermission('menuManage:edit')" :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="handleClickAddOrEdit(scope.row.menuId, 'find')"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button v-if="hasPermission('menuManage:edit')" :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.menuId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button v-if="hasPermission('menuManage:delete')" :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row.menuId)"/>
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
    <!-- 设置角色权限 -->
    <el-dialog :visible.sync="dialogVisibleForMenuActionTree" title="菜单关联操作按钮">
      <el-form :model="form">
        <el-form-item>
          <el-tree ref="actionTree" :data="form.treeData" :default-checked-keys="form.checkedIds" :default-expanded-keys="form.checkedIds" show-checkbox node-key="id"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleClickRoleGrantRoleSave">确定</el-button>
          <el-button @click="dialogVisibleForMenuActionTree=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { queryByPage, deleteById, queryActionTreeForMenu, saveActionTreeForMenu } from '@/api/authority/menu'
import CodeSelect from '@/components/codeSelect'

export default {
  name:'menuList',
  components: {
    CodeSelect
  },
  data() {
    return {
      selectInput:{
        keyname: 'menuCode'
      },
      params: {
        currentPage: 1,
        pageSize: 10,

        menuCode: null,
        menuName: null,
        menuUrl: null,
        sysCode: null,
        enableFlag: null
      },
      list: null,
      totalCount: 0,
      totalPage: 0,
      listLoading: true,

      dialogVisibleForMenuActionTree: false,
      form: {
        menuId: null,
        actionIds: [],
        treeData: [],
        checkedIds: []
      }
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path == "/authority/menu/edit") {
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
      this.$router.push({ path: '/authority/menu/edit', query: { id: id, val: val }})
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
    handleClickGrantAction(menuId) {
      this.form.menuId = menuId
      queryActionTreeForMenu(menuId).then(response => {
        if (response.repCode == '0000') {
          this.form.checkedIds = response.repData.checkedIds
          this.form.treeData = response.repData.treeData
          this.dialogVisibleForMenuActionTree = true
        }
      })
    },
    handleClickRoleGrantRoleSave() {
      this.$confirm("当前操作可能会导致角色已勾选的菜单按钮权限遗失，请谨慎操作", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
      .then(() => {
      var checkIds = this.$refs.actionTree.getCheckedKeys(true)
      this.form.actionIds = checkIds
      saveActionTreeForMenu(this.form).then(response => {
        if (response.repCode == '0000') {
          this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
        }
        this.dialogVisibleForMenuActionTree = false
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
