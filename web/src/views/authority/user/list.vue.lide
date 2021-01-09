<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container">
      <el-input v-model="selectInput.keyword" class="input-with-select filter-item" @change="handlerInputchange" style="width: 220px;" placeholder="请输入关键字" >
        <el-select slot="prepend" clearable v-model="selectInput.keyname" placeholder="请选择">
          <el-option label="登录名" value="loginName"/>
          <el-option label="真实姓名" value="realName"/>
        </el-select>
      </el-input>
      <code-select v-model="params.enableFlag" dictname="ENABLE_FLAG" mystyle="width: 120px;" placeholder="启用状态"/>
      <el-button v-if="hasPermission('userManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="hasPermission('userManage:add')" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="handleClickAddOrEdit(null)">添加</el-button>
    </div>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" width="60" type="index">
      </el-table-column>
      <el-table-column label="登录名" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.loginName }}
        </template>
      </el-table-column>
      <el-table-column label="真实姓名" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号码" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.phone }}
        </template>
      </el-table-column>
      <el-table-column label="用户邮箱" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.email }}
        </template>
      </el-table-column>
      <el-table-column label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.enableFlag | basecode('ENABLE_FLAG') }}
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" max-width="220">
        <template slot-scope="scope">
          {{scope.row.remark?scope.row.remark:"无"}}
        </template>
      </el-table-column>
      <el-table-column label="推荐人" width="100" align="center">
        <template slot-scope="scope">
          {{scope.row.recommender?scope.row.recommender:"无"}}
        </template>
      </el-table-column>
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
      <el-table-column fixed="right" label="操作" min-width="150" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="分配角色" placement="top">
            <el-button :circle="true" :plain="true" type="warning" icon="el-icon-setting" size="mini" @click="handleClickGrantRole(scope.row.userId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button v-if="hasPermission('userManage:find') && !hasPermission('userManage:edit')" :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="handleClickAddOrEdit(scope.row.userId, 'find')"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button v-if="hasPermission('userManage:edit')" :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.userId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button v-if="hasPermission('userManage:delete')" :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row.userId)"/>
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
    <el-dialog :visible.sync="dialogVisibleForRoleGrantRole" title="选择角色" >
      <el-form :model="form">
        <el-form-item>
          <div class="box-height">
            <el-tree ref="roleTree" :check-on-click-node="true" @check="handleTreeNodeChecked" :data="form.treeData" :default-checked-keys="form.checkedIds" :default-expanded-keys="form.checkedIds" show-checkbox node-key="id"/>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleClickRoleGrantRoleSave">确定</el-button>
          <el-button @click="dialogVisibleForRoleGrantRole=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { queryByPage, deleteById, queryRoleTree, saveRoleTree } from '@/api/authority/user'
import CodeSelect from '@/components/codeSelect'

export default {
  name:'userList',
  components: {
    CodeSelect
  },
  data() {
    return {
      selectInput:{
        keyname: 'loginName'
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

      dialogVisibleForRoleGrantRole: false,
      form: {
        userId: null,
        orgRoleIds: [],
        treeData: [],
        checkedIds: []
      },
      tempParentNode: {},//在用户授权角色的交互树中，查询选中节点的父节点时使用
      tempNodeIds: [],//在用户授权角色的交互树中，查询相同label的兄弟节点和子节点的id
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path == "/authority/user/edit") {
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
    this.queryByPage();
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
      this.$router.push({ path: '/authority/user/edit', query: { id: id, val: val }})
    },
    //check树，某节点被选中
    handleTreeNodeChecked(item, event){
      //{id: "1_3", label: "审计员角色", disabled: false, children: [], extend: null, parentId: null, name: null,…}
      var clickId = item.id.toString();
      var searchLabel = item.label;//审计员角色
      var checkedKeys = event.checkedKeys
      var isChecked = checkedKeys.some(v => v === clickId) //当前操作，是取消选中，还是选中，true 选择 flase 取消

      //根据checkId，查询父节点，只有id=orgId_roleId的节点才触发
      if(clickId.indexOf("_") == -1){
        return;
      }
      //查找指定父节点
      var parentId = clickId.substring(0, clickId.indexOf("_"));
      this.tempParentNode={};
      this.findNodeById(this.form.treeData[0], parentId);

      //从this.form.treeData中，查找所有的label=searchLabel的节点id
      this.tempNodeIds=[];
      this.findIdByLabelWithChildren(this.tempParentNode, searchLabel);

      //修改选中的值
      for(var id of this.tempNodeIds){
        this.$refs.roleTree.setChecked(id, isChecked);
      }
    },
    //查询指定的节点
    findNodeById(node, id){
      if(node.id == id){
        this.tempParentNode = node;
        return;
      }
      //子节点
      if(node.children != null && node.children.length >0 ){
        for(var item of node.children){
          this.findNodeById(item, id);
        }
      }
    },
    //根据label，查询该id节点下所有的节点id，包含子节点
    findIdByLabelWithChildren(node, searchLabel){
      //节点本身
      if(node.label == searchLabel && this.tempNodeIds.indexOf(node.id) < 0){
        this.tempNodeIds.push(node.id);
        return;
      }
      //子节点
      if(node.children != null && node.children.length >0 ){
        for(var item of node.children){
          this.findIdByLabelWithChildren(item, searchLabel);
        }
      }
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
    handleClickGrantRole(userId) {
      this.form.userId = userId
      queryRoleTree({userId:userId}).then(response => {
        if (response.repCode == '0000') {
          this.form.checkedIds = response.repData.checkedIds
          this.form.treeData = response.repData.treeData
          this.dialogVisibleForRoleGrantRole = true
        }
      })
    },
    handleClickRoleGrantRoleSave() {
      var checkIds = this.$refs.roleTree.getCheckedKeys(true)
      this.form.orgRoleIds = checkIds
      saveRoleTree(this.form).then(response => {
        if (response.repCode == '0000') {
          this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
          this.search()
          this.dialogVisibleForRoleGrantRole = false
        }
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