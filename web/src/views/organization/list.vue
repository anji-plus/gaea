<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container">
      <el-input v-model="selectInput.keyword" class="input-with-select filter-item" @change="handlerInputchange" style="width: 380px;" placeholder="请输入关键字" >
        <el-select slot="prepend" clearable style="width:160px" v-model="selectInput.keyname" placeholder="请选择">
          <el-option label="机构代码" value="orgCode"/>
          <el-option label="机构名称" value="orgName"/>
          <!-- <el-option label="上级机构代码" value="orgParentCode"/> -->
          <!-- <el-option label="上级机构代码" value="orgParentName"/> -->
          <el-option label="外部机构代码" value="outOrgCode"/>
          <el-option label="外部上级机构代码" value="outOrgParentCode"/>
          <el-option label="机构类型" value="orgType"/>
          <el-option label="联系人" value="linkman"/>
        </el-select>
      </el-input>
      <code-select v-model="params.enableFlag" dictname="ENABLE_FLAG" mystyle="width: 120px;" placeholder="启用状态"/>
      <code-select v-model="params.orgParentCode " remoteurl='/auth-service/org/selectOption' mystyle="width: 180px;" placeholder="上级组织"/>
      <el-button v-if="hasPermission('orgManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="hasPermission('orgManage:add')" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="Edit(null)">添加</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" icon="el-icon-refresh" plain @click="reset">重置</el-button> 
    </div>
    <!-- 查询结果列表 -->
    <el-table :data="list" style="width: 100%"
      border lazy >
      <el-table-column label="机构代码" min-width="200" align="center" >
        <template slot-scope="scope">
          {{scope.row.orgCode }}
        </template>
      </el-table-column>
      <el-table-column label="机构名称" min-width="140" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.orgName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上级机构代码" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.orgParentCode }}
        </template>
      </el-table-column>
      <el-table-column label="上级机构名称" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.orgParentName }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="外部机构代码" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.outOrgCode }}
        </template>
      </el-table-column>
      <el-table-column label="外部上级机构代码" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.outOrgParentCode }}
        </template>
      </el-table-column>
      <el-table-column label="机构级别" max-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.orgLevel }}
        </template>
      </el-table-column>
      <el-table-column label="机构类型" max-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.orgType }}
        </template>
      </el-table-column> -->
      <el-table-column label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.enableFlag | basecode('ENABLE_FLAG') }}
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" max-width="220"  align="center">
        <template slot-scope="scope">
          {{scope.row.remark?scope.row.remark:"无"}}
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

      <el-table-column fixed="right" label="操作" min-width="100" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button v-if="hasPermission('orgManage:find') && !hasPermission('orgManage:edit')" :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="Edit(scope.row.orgId, 'find')"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button v-if="hasPermission('orgManage:edit')" :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="Edit(scope.row.orgId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button v-if="hasPermission('orgManage:delete')" :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="Delete(scope.row.orgId)"/>
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
  </div>
</template>

<script>
import { queryByPage, deleteById } from '@/api/authority/org'
import CodeSelect from '@/components/codeSelect'

export default {
  name: 'organizationList',
  components: {
    CodeSelect
  },
  data() {
    return {
      selectInput:{
        keyname: 'orgCode'
      },
      params: {
        currentPage: 1,
        pageSize: 10,
        orgName: null,
        orgParentCode: null,
        orgParentName: null,
        outOrgCode: null,
        outOrgParentCode: null,
        orgType: null,
        linkman: null,
        enableFlag: null
      },
      list: undefined,
      totalCount: 0,
      totalPage: 0,
      listLoading: true,

      dialogVisibleForRoleGrantRole: false,
      form: {
        userId: null,
        roleIds: [],
        treeData: [],
        checkedIds: []
      },
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path == "/organization/organization/edit") {
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
    reset(){
      this.resetForm(this.params)
      this.params.currentPage=1;
      this.params.pageSize=10;
      this.selectInput.keyname="";
      this.selectInput.keyword="";
      this.queryByPage()
    },
    queryByPage() {
      this.listLoading = true;
      let _this=this;
      let ppp={};
      ppp=_this.params
      queryByPage(ppp).then(response => {
        if (response.repCode == '0000') {
          this.list = response.repData.list;
          if(this.list.lenght>0){
            this.list[0].hasChildren= true;
          }
          this.totalCount = response.repData.totalCount
          this.totalPage = response.repData.totalPage
          this.params.currentPage = response.repData.currentPage
          this.params.pageSize = response.repData.pageSize
        }
        this.listLoading = false
      })
    },
    handleSizeChange(val) {
      this.params.pageSize = val;
      this.queryByPage()
    },
    handleCurrentChange(val) {
      this.params.currentPage = val;
      this.queryByPage()
    },
    Edit(id, val = "") {
      this.$router.push({ path: '/organization/edit', query: { id: id, val: val }})
    },
    Delete(id) {
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
    load(tree, treeNode, resolve) {
      this.params.orgPCode = tree.orgCode;
        queryByPage(this.params).then(response => {
        if (response.repCode == '0000') {
          setTimeout(() => {
            resolve(response.repData.list)
            },1000)
          } 
        })
    },
    
  }
}
</script>
<style scoped>

</style>
