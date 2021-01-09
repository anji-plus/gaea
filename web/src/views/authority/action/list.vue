<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container">
      <el-input v-model="selectInput.keyword" class="input-with-select filter-item" @change="handlerInputchange" style="width: 220px;" placeholder="请输入关键字" >
        <el-select slot="prepend" clearable v-model="selectInput.keyname" placeholder="请选择">
          <el-option label="按钮代码" value="actionCode"/>
          <el-option label="按钮名称" value="actionName"/>
        </el-select>
      </el-input>
      <code-select v-model="params.enableFlag" dictname="ENABLE_FLAG" mystyle="width: 120px;" placeholder="启用状态"/>
      <el-button v-if="hasPermission('actionManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="hasPermission('actionManage:add')" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="handleClickAddOrEdit(null)">添加</el-button>
    </div>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" width="60" type="index">
      </el-table-column>
      <el-table-column label="按钮代码" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.actionCode }}
        </template>
      </el-table-column>
      <el-table-column label="按钮名称" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.actionName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.sort }}</span>
        </template>
      </el-table-column>
      <el-table-column label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.enableFlag | basecode('ENABLE_FLAG') }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="备注" :show-overflow-tooltip="true"  align="center"  max-width="220">
        <template slot-scope="scope">
          {{scope.row.remark?scope.row.remark:"无"}}
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
      <el-table-column fixed="right" label="操作" min-width="100" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button v-if="hasPermission('actionManage:find') && !hasPermission('actionManage:edit')" :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="handleClickAddOrEdit(scope.row.actionId, 'find')"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button v-if="hasPermission('actionManage:edit')" :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.actionId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button v-if="hasPermission('actionManage:delete')" :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row.actionId)"/>
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
import { queryByPage, deleteById } from '@/api/authority/action'
import CodeSelect from '@/components/codeSelect'

export default {
  name: 'actionList',
  components: {
    CodeSelect
  },
  data() {
    return {
      selectInput:{
        keyname: 'actionCode'
      },
      params: {
        currentPage: 1,
        pageSize: 10,

        actionName: null,
        actionCode: null,
        enableFlag: null
      },
      list: null,
      totalCount: 0,
      totalPage: 0,
      listLoading: true,
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path == "/authority/action/edit") {
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
      this.$router.push({ path: '/authority/action/edit', query: { id: id, val: val }})
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
  }
}
</script>
