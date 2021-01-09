<template>
  <div>
    <el-container style="width: 100%;height: 100%;">
      <el-main>
        <div class="search-pop clearfix">
          <el-row>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="grid-content">
                <div class="bg-w c-mt20 pb40">
                  <el-row type="flex" justify="center">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <div class="grid-content pd-main  pb20">
                        <el-form :model="params" ref="form" label-position="right" label-width="100px" class="demo-ruleForm">
                          <el-row :gutter="20">
                            <el-col :span="6">
                              <el-form-item label="文件标题">
                                <el-input v-model="params.fileTitle" placeholder="请输入"></el-input>
                              </el-form-item>
                            </el-col>
                            <el-col :span="6">
                              <el-form-item label-width="0px">
                                <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="search">查询</el-button>
                              </el-form-item>
                            </el-col>
                          </el-row>
                        </el-form>
                        <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border highlight-current-row>
                          <el-table-column align="center" label="序号" width="80">
                            <template slot-scope="scope">
                              {{ scope.$index + (params.currentPage - 1) * params.pageSize + 1 }}
                            </template>
                          </el-table-column>
                          <el-table-column label="文件标题" prop="fileTitle" align="center" min-width="180"/>
                          <el-table-column label="文件描述" align="center" min-width="300">
                            <template slot-scope="scope">
                              数据范围:{{ scope.row.resultStartTime | formatDate }} ~ {{scope.row.resultEndTime | formatDate}}，累计{{scope.row.resultSize}}条数据
                            </template>
                          </el-table-column>
                          <el-table-column label="文件状态" align="center" width="100">
                            <template slot-scope="scope">
                              {{ scope.row.fileStatus | basecode('FILE_STATUS')}}
                            </template>
                          </el-table-column>
                          <el-table-column label="文件创建时间" align="center" width="160">
                            <template slot-scope="scope">
                              {{ scope.row.fileCreateTime | formatTimestamp}}
                            </template>
                          </el-table-column>
                          <el-table-column label="文件导出时间" align="center" width="160">
                            <template slot-scope="scope">
                              {{ scope.row.fileFinishTime | formatTimestamp}}
                            </template>
                          </el-table-column>
                          <el-table-column label="创建者" prop="createrUsername" align="center" width="80"/>
                          <el-table-column label="操作" align="center" width="80">
                            <template slot-scope="scope">
                              <el-tooltip class="item" effect="dark" content="下载" placement="top">
                                <el-button :circle="true" :plain="true" type="success" icon="el-icon-download" size="mini" @click="downloadFile(scope.row)"></el-button>
                              </el-tooltip>
                            </template>
                          </el-table-column>
                        </el-table>
                        <el-pagination
                          @size-change="handleSizeChange"
                          @current-change="handleCurrentChange"
                          :current-page="params.currentPage"
                          :page-sizes="[10, 20, 50, 100]"
                          :page-size="10"
                          layout="total, sizes, prev, pager, next, jumper"
                          :total="totalCount">
                        </el-pagination>
                      </div>
                    </el-col>
                  </el-row>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-main>
    </el-container>
    
  </div>
</template>
<script> 
  import {queryByPage,downloadFileId} from "@/api/download/download"
  import axios from 'axios';
  export default {
    name: "Alarm",
    data(){
      return{
        list:[],          //查询列表数据
        params:{
          fileTitle:'',
          currentPage: 1,
          pageSize: 10,
        },
        totalCount: 0,
        totalPage: 0,
        listLoading: true,
        downloadPath:''
      }
    },
    components: {
      
    },
    computed:{},
    created() {
      
    },
    mounted(){ 
      this.queryPageList();
    },
    methods: {
      search(){
        this.queryPageList()
      },
      queryPageList(){
        this.listLoading = true
        queryByPage(this.params).then(response => {
          if (response.repCode == '0000') {
            this.listLoading = false
            this.list = response.repData.list;
            this.totalCount = response.repData.totalCount;
            this.totalPage = response.repData.totalPage
          }
        })
      },
      // 点击下载文件     
      downloadFile(item) {
        axios.defaults.baseURL = process.env.BASE_API;
        this.downloadPath = axios.defaults.baseURL +'/auth-service/file/download/' +item.fileId;
        var link = document.createElement("a");
        link.download =item.fileTitle;
        link.href = this.downloadPath;
        link.click();
        link.remove();
      },
      handleSizeChange(val) {
        this.params.pageSize = val; 
        this.queryPageList()
      },
      handleCurrentChange(val) {
        this.params.currentPage = val;
        this.queryPageList()
      },
    }
  }
</script>

<style scoped>

</style>
