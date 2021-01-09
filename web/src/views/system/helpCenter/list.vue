<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container">
      <el-form label-position="right" :model="params" label-width="70px">
        <el-row :gutter="10">
          <el-col :span="6">
            <el-form-item label="所属分类">
              <code-select v-model.trim="params.helpCategory" dictname="HELP_CATEGORY" mystyle="width: 100%;" placeholder="请选择所属分类"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="标题">
              <el-input v-model="params.helpTitle" placeholder="请输入标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="启用状态">
              <code-select v-model="params.enableFlag" dictname="ENABLE_FLAG" mystyle="width: 100%;" placeholder="请选择启用状态"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-button v-if="hasPermission('helpManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="queryByPage">查询</el-button>
            <el-button v-if="hasPermission('helpManage:add')" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="handleClickAddOrEdit(null)">添加</el-button>
          </el-col>
        </el-row> 
      </el-form>
    </div>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" width="70">
        <template slot-scope="scope">
          {{ params.pageSize*(params.currentPage-1)+scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label="所属分类" min-width="60" align="center">
        <template slot-scope="scope">
          {{ scope.row.helpCategory | basecode('HELP_CATEGORY') }}
        </template>
      </el-table-column>
      <el-table-column label="标题" min-width="140" align="center">
        <template slot-scope="scope">
          <span class="title">{{ scope.row.helpTitle }}</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" min-width="40" align="center">
        <template slot-scope="scope">
          {{ scope.row.sort }}
        </template>
      </el-table-column>
      <el-table-column label="启用状态" min-width="40" align="center">
        <template slot-scope="scope">
          {{scope.row.status}}
          {{ scope.row.enableFlag | basecode('ENABLE_FLAG') }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button v-if="hasPermission('helpManage:find') && !hasPermission('helpManage:edit')" :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="handleClickAddOrEdit(scope.row.helpId, 'find')"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button v-if="hasPermission('helpManage:edit')" :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.helpId)"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button v-if="hasPermission('helpManage:delete')" :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row)"/>
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
import { queryByPage, deleteOne } from '@/api/system/helpCenter'
import CodeSelect from '@/components/codeSelect'
import {getStorageItem,setStorageItem} from '@/utils/storage.js'

export default {
  components: {
    CodeSelect
  },
  data() {
    return {
      params: {
        currentPage: 1,
        pageSize: 10,
        helpTitle: '',
        enableFlag: '',
        helpCategory: ''
      },
      list: [],
      totalCount: 0,
      totalPage: 0,
      listLoading: false
    }
  },
  // created() {
  //   this.queryByPage()
  // },
  mounted() {
    if(getStorageItem('helpCenterDetail')) {
      this.params = getStorageItem('helpCenterDetail')
    }
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
    // 新增 or 编辑
    handleClickAddOrEdit(id, val="") {
      if(id) {
        setStorageItem('helpCenterDetail', this.params)
      }
      this.$router.push({ path: '/system/helpCenter/edit', query: { id: id, val: val }})
    },
    // 删除
    handleClickDelete(row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteOne({helpId: row.helpId}).then(response => {
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
    }
  }
}
</script>
<style lang="scss" scoped>
.title{
  width: 100%;
  display: inline-block;
  text-align: left;
}
</style>