<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container">
      <el-input v-model.trim="selectInput.keyword" class="input-with-select filter-item" @change="handlerInputchange" style="width: 280px;" placeholder="请输入关键字" >
        <el-select slot="prepend" clearable v-model="selectInput.keyname" placeholder="请选择">
          <el-option label="参数名称" value="settingName"/>
          <el-option label="参数描述" value="settingLabel"/>
        </el-select>
      </el-input>
      <el-button v-if="hasPermission('settingManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="hasPermission('settingManage:add')" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="handleClickAddOrEdit(null)">添加</el-button>
    </div>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
      <el-table-column type="expand">
        <template slot-scope="props">
          <!-- 
          <p class="table-expand-item">
            <span class="titel">提取表达式: </span>
            <span style="line-height:18px">{{props.row.settingForm}}</span>
          </p>
          -->
          <p class="table-expand-item">
            <span class="titel">参数值: </span>
            <span>{{props.row.settingValue}}</span>
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="序号" min-width="80">
        <template slot-scope="scope">
          {{ params.pageSize*(params.currentPage-1)+scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label="参数名称" min-width="180" align="center" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <span>{{ scope.row.settingName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="参数描述" min-width="300" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.settingLabel }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="参数值类型" min-width="120" align="center">
        <template slot-scope="scope">
          {{ scope.row.itemDesc }}
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="参数表单组件" min-width="300" align="center" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          {{ scope.row.settingForm }}
        </template>
      </el-table-column>
      <el-table-column label="表单组件值" align="center" min-width="300" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          {{ scope.row.settingValue }}
        </template>
      </el-table-column> -->
      <el-table-column label="启用状态" min-width="120" align="center" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.enableFlag == 1?'已启用':'已禁用'" placement="top">
            <el-switch
              v-model="scope.row.enableFlag"
              active-color="#13ce66"
              inactive-color="#ccc"
              :active-value="1"
              :inactive-value="0"
              @change ="switchEnableById(scope.row)">
            </el-switch>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="210" align="center" :show-overflow-tooltip="true">
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
      <el-table-column label="修改人" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.updatedBy}}
        </template>
      </el-table-column>
      <el-table-column label="修改时间" min-width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.updatedTime | formatTimestamp }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="75" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button v-if="hasPermission('settingManage:find') && !hasPermission('settingManage:edit')" :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="handleClickAddOrEdit(scope.row.settingId, 'find')"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button v-if="hasPermission('settingManage:edit')" :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.settingId)"/>
          </el-tooltip>
          <!-- <el-button :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row.parameterId)"/> -->
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
import { queryByPage, reqSwitchEnableById } from "@/api/system/setting"

export default {
  data() {
    return {
      selectInput:{
        keyname: 'settingName'
      },
      params: {
        currentPage: 1,
        pageSize: 10,
        settingName: null,
        settingLabel: null
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
    // 启用禁用切换
    switchEnableById(val){
      reqSwitchEnableById({settingId: val.settingId}).then(res => {
        if (res.repCode == '0000') {
           this.$message.success('状态切换成功')
        }
      })
    },
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
      this.$router.push({ path: '/system/setting/edit', query: { id: id, val: val }})
    }
    /*
    handleClickDelete(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }*/
  }
}
</script>
