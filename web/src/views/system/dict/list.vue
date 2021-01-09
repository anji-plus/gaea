<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container">
      <el-input v-model.trim="selectInput.keyword" class="input-with-select filter-item"  style="width: 220px;" @change="handlerInputchange" placeholder="请输入关键字" >
        <el-select slot="prepend" clearable v-model="selectInput.keyname" placeholder="请选择">
          <el-option label="字典名称" value="dictName"/>
          <el-option label="字典描述" value="dictDesc"/>
          <el-option label="字典代码" value="itemName"/>
          <el-option label="代码描述" value="itemDesc"/>
        </el-select>
      </el-input>
      <code-select v-model="params.enableFlag" dictname="ENABLE_FLAG" mystyle="width: 120px;" placeholder="启用状态"/>
      <el-button v-if="hasPermission('dictManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="hasPermission('dictManage:add')" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="handleClickAddOrEdit(null)">添加</el-button>
    </div>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" min-width="70">
        <template slot-scope="scope">
          {{ params.pageSize*(params.currentPage-1)+scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label="字典名称" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.dictName }}
        </template>
      </el-table-column>
      <el-table-column label="字典描述" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.dictDesc }}</span>
        </template>
      </el-table-column>
      <el-table-column label="字典代码" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.itemName }}
        </template>
      </el-table-column>
      <el-table-column label="代码值" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.itemValue }}
        </template>
      </el-table-column>
      <el-table-column label="代码描述" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.itemDesc }}
        </template>
      </el-table-column>
      <el-table-column label="排序" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.sort }}
        </template>
      </el-table-column>
      <el-table-column label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.enableFlag | basecode('ENABLE_FLAG') }}
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.remark }}
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
      <el-table-column fixed="right" label="操作" min-width="115" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button v-if="hasPermission('dictManage:find') && !hasPermission('dictManage:edit')"  :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="handleClickAddOrEdit(scope.row.dictId, 'find')"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button v-if="hasPermission('dictManage:edit')"  :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.dictId)"/>
          </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除" placement="top">
          <el-button v-if="hasPermission('dictManage:delete')" :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row)"/>
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
import { queryByPage, deleteOne } from '@/api/system/dict'
import CodeSelect from '@/components/codeSelect'

export default {
  components: {
    CodeSelect
  },
  data() {
    return {
      selectInput:{
        keyname: 'dictName'
      },
      params: {
        currentPage: 1,
        pageSize: 10,
        dictName: null,
        dictDesc: null,
        itemName: null,
        itemDesc: null,
        enableFlag: null
      },
      list: null,
      totalCount: 0,
      totalPage: 0,
      listLoading: true
    }
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
      this.$router.push({ path: '/system/dict/edit', query: { id: id, val: val }})
    },
    handleClickDelete(row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteOne({dictId: row.dictId}).then(response => {
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