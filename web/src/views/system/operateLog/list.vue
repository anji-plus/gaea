<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="filter-container log-search">
      <el-form :model="params" ref="form" label-position="right" label-width="80px" class="demo-ruleForm">
        <el-row :gutter="10">
          <el-col :span="6">
            <el-form-item label="用户名称">
              <el-select v-model="params.loginName" filterable clearable placeholder="请选择">
              <el-option v-for="(item,index) in userArr" :key="index" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="页面标题">
              <el-input clearable v-model.trim="params.pageTitle" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="请求路径">
              <el-input clearable v-model.trim="params.requestUrl" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label-width='0px'>
              <el-button v-if="hasPermission('logManage:find')" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleClickAddOrEdit(null)"/> -->
    </div>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column type="expand">
        <template slot-scope="props">
          <p class="table-expand-item">
            <span class="titel">请求路径: </span>
            <span >{{props.row.requestUrl}}</span>
          </p>
          <!--
          <p class="table-expand-item">
            <span class="titel">请求类型: </span>
            <span>{{props.row.requestMethod}}</span>
          </p>
          -->
          <p class="table-expand-item">
            <span class="titel">请求参数: </span>
            <span>{{props.row.requestParam}}</span>
          </p>
          <p class="table-expand-item">
            <span class="titel">响应参数: </span>
            <span>{{props.row.responseParam}}</span>
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="序号" min-width="40">
        <template slot-scope="scope">
          {{ params.pageSize*(params.currentPage-1)+scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label="用户名称" max-width="120" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.userName }}
        </template>
      </el-table-column>
      <el-table-column label="页面标题" min-width="110" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.pageTitle }}
        </template>
      </el-table-column>
      <el-table-column label="请求路径" min-width="130" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.requestUrl }}</span>
        </template>
      </el-table-column>
      <!--
      <el-table-column label="请求类型" min-width="80" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.requestMethod }}
        </template>
      </el-table-column>
      -->
      <el-table-column label="请求参数" min-width="110" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.requestParam }}
        </template>
      </el-table-column>
      <el-table-column label="响应参数" min-width="110" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.responseParam }}
        </template>
      </el-table-column>
      <el-table-column label="来源IP" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.sourceIp }}
        </template>
      </el-table-column>
      <el-table-column label="访问时间" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.requestTime | formatTimestamp}}
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
import { queryByPage } from '@/api/system/log'
import CodeSelect from '@/components/codeSelect'
import {selectOption} from '@/api/authority/user'
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
        pageTitle: null,
        requestUrl: null,
        userName: null,
        loginName:null,
      },
      list: null,
      totalCount: 0,
      totalPage: 0,
      listLoading: true,
      userArr:[],
    }
  },
  created() {
    this.queryByPage();
    this.querySelectOption();
  },
  methods: {
    querySelectOption(query){
      let self=this;
      selectOption({loginName:query}).then(res => {
        if (res.repCode == "0000") {
          this.listLoading=false;
          self.userArr = res.repData;
        }
      }).catch(error => {});
    },
    queryByPage() {
      this.listLoading = true;
      if(this.params.loginName !=null){
        this.params.userName=this.params.loginName
      }
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
    }
  }
}
</script>

<style lang="scss" scoped>
.log-search{
  .el-form-item{
    margin-bottom: 0px;
  }
}
</style>
