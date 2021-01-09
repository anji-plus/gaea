<template>
  <div class="search-pop clearfix">
      <div class="grid-content pd-main pt20 pb20">
        <el-row class="row-bg">
          <el-col type="flex" :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <div class="grid-content">
              <div class="right-box">
                <div class="log-list">
                  <div class="mt30" style="width: 100%;float: left">
                    <div class="log-repeat">
                      <el-form ref="form" :label-position="labelPosition" :model="form" label-width="100px">
                        <el-row :gutter="12" class="pd-content2">
                          <el-col :xs="24" :sm="5" :md="5" :lg="5" :xl="5">
                            <div class="grid-content">
                              <el-form-item label="推送类型">
                                <code-select v-model.trim="form.templateType" dictname="ALERT_CHANNEL" mystyle="width: 100%;" placeholder="请选择"/>
                              </el-form-item>
                            </div>
                          </el-col>
                          <el-col :xs="24" :sm="5" :md="5" :lg="5" :xl="5">
                            <div class="grid-content">
                              <el-form-item label="搜索关键字">
                                <el-input placeholder="请输入搜索字段" v-model="form.field"></el-input>
                              </el-form-item>
                            </div>
                          </el-col>
                          <el-col :xs="24" :sm="10" :md="10" :lg="10" :xl="10">
                            <div class="grid-content">
                              <el-form-item label="创建日期">
                                <el-date-picker
                                  v-model="timeSelectedValue"
                                  type="daterange"
                                  align="left"
                                  unlink-panels
                                  range-separator="至"
                                  start-placeholder="开始日期"
                                  end-placeholder="结束日期"
                                  format="yyyy-MM-dd HH:mm:ss"
                                  value-format="yyyy-MM-dd HH:mm:ss"
                                  :picker-options="datetimeRangePickerOptions" >
                                </el-date-picker>
                              </el-form-item>
                            </div>
                          </el-col>
                          <el-col :xs="24" :sm="4" :md="4" :lg="4" :xl="4">
                            <div class="grid-content">
                              <el-button v-if="hasPermission('pushHistoryManage:find')" class="c-button" type="primary" icon="el-icon-search" @click="queryByPage">查询</el-button>
                              <el-button class="c-button" plain icon="el-icon-refresh" @click="Reset">重置</el-button>
                            </div>
                          </el-col>
                        </el-row>
                      </el-form>
                    </div>
                  </div>
                  <div class="mt10" style="width: 100%;float: left">
                    <div class="log-repeat">
                      <el-row :gutter="12" class="pd-content2 mt10">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <div class="grid-content">
                            <el-table stripe border style="width: 100%" :data="tableData" element-loading-text="Loading" fit highlight-current-row>
                              <el-table-column type="expand">
                                <template slot-scope="props">
                                  <p class="table-expand-item">
                                    <span>{{props.row.sendResult}}</span>
                                  </p>
                                </template>
                              </el-table-column>
                              <el-table-column width="90" label="序号" align="center">
                                <template slot-scope="scope"><span>{{scope.$index+(pageNum - 1) * pageSize + 1}} </span></template>
                              </el-table-column>
                              <el-table-column prop="pushTo" :label="mailTo" :show-overflow-tooltip="true" ></el-table-column>
                              <el-table-column label="推送类型" width="90" align="center">
                                <template slot-scope="scope">
                                  {{ scope.row.templateType | basecode('ALERT_CHANNEL') }}
                                  <!-- {{getDictCode('ALERT_CHANNEL',scope.row.templateType,"labelEng").label}} -->
                                </template>
                              </el-table-column>
                              <el-table-column prop="pushTitle" label="标题" align="center"></el-table-column>
                              <el-table-column prop="templateCode" label="模板Code"  align="center"></el-table-column>
                              <el-table-column prop="sendTime" :formatter="dateFormat" label="发起时间" width="160" align="center"></el-table-column>
                              <el-table-column label="发送状态" align="center" width="90">
                                <template slot-scope="scope">
                                  <div v-if="scope.row.sendStatus==1" class="s-green">成功</div>
                                    <div v-if="scope.row.sendStatus==0" class="s-red">失败</div>
                                </template>
                              </el-table-column>
                              <el-table-column label="操作" align="center" width="90">
                                <template slot-scope="scope">
                                  <el-tooltip class="item" effect="dark" content="查看" placement="top">
                                    <el-button size="mini" icon="el-icon-search" :plain="true" circle @click="showMailDetails(scope.row)"></el-button>
                                  </el-tooltip>
                                </template>
                              </el-table-column>
                            </el-table>
                            <el-pagination class="fr" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage4" :page-sizes="[10,20,50,100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                            </el-pagination>
                          </div>
                        </el-col>
                      </el-row>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <Modal ref="Modal" v-show="isModalEmailInfo" :statusText="isMailTitle" @closeModal="closeModal" contentText="index.vue" @button="closeModal" alert="1">
        <Details @closeModal="closeModal" :mailDetails="mailDetails" :temolateType="temolateType"></Details>
      </Modal>
    </div>
</template>

<script>
  import Modal from "@/components/Modal.vue";
  import {queryPushHistoryByPage, queryPushHistoryById} from '@/api/push/notify';
  import Details from './Modal-details.vue';
  import parseTime from '@/utils/index'
  import moment from 'moment';
  import miment from 'miment'
  import CodeSelect from '@/components/codeSelect'
  export default {
    name: "History",
    data(){
      return{
        currentPage4: 1,
        total: 0,
        page: 1,
        pageSize: 10,
        pageNum: 1,
        labelPosition: 'right',
        timeSelectedValue:null,//时间选择器的value
        form: {
          field:"",//搜索字段
          templateType:'',
        },
        isModalEmailInfo:false,
        tableData:[],
        isMailTitle:'推送内容',
        mailDetails:{},
        beginDate:"",
        endDate:"",

        //判断 类型
        mailTo:"收件人",
        temolateType:'mail',
      }
    },
    created(){
      document.onkeypress = function(e) {
        var keycode = document.all ? event.keyCode : e.which;
        if (keycode == 13) {
          return false;
        }
      };
    },
    components: {
      Modal,
      Details,
      CodeSelect
    },
    mounted(){
      this.queryByPage()
    },
    methods: {
      //时间格式化
      dateFormat:function(row, column) {
        var date = row[column.property];
        if (date == undefined) {
          return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
      },
      // 列表查询
      queryByPage(){
        if(this.timeSelectedValue!=null){
          this.beginDate= moment(this.timeSelectedValue[0]).format("YYYY-MM-DD HH:mm:ss");
          this.endDate= moment(this.timeSelectedValue[1]).format("YYYY-MM-DD HH:mm:ss");
        }else{
          this.beginDate='';
          this.endDate=''
        }
        this.mailTo="收件人"
        let param ={
          sentTime:this.timeSelectedValue,
          keyword:this.form.field,
          pageSize:this.pageSize,
          currentPage:this.pageNum,
          startTime:this.beginDate,
          endTime:this.endDate,
          templateType:this.getDictCode('ALERT_CHANNEL' ,this.form.templateType).labelEng
        };
        queryPushHistoryByPage(param).then(res=>{
          if(res.repCode=="0000"){
            this.tableData=res.repData.list;
            this.total = res.repData.totalCount;
            this.currentPage4 = res.repData.currentPage;
          }
        })
      },
      Search(){
        this.queryByPage();
      },
      handleSizeChange (val) {
        this.pageSize = val;
        this.queryByPage();
      },
      handleCurrentChange (val) {
        this.pageNum = val;
        this.queryByPage();
      },
      closeModal(){
        this.isModalEmailInfo=false
      },
      fanhui(){
        this.$router.push("/index");
      },
      //单封邮件查询
      showMailDetails(val){
        console.log(val)
        this.temolateType=val.templateType
        this.isModalEmailInfo=true;
        let param ={
          pushId:val.pushId,
        };
        queryPushHistoryById(param).then(res=>{
          if(res.repCode=="0000"){
            this.mailDetails=res.repData
          }
        })
      },
      
      Reset(){
        this.form.field='';
        this.beginDate='';
        this.endDate='';
        this.timeSelectedValue=null;
        this.queryByPage();
      }
    }
  }
</script>

<style scoped lang="scss">
  .s-green{
    color: #55a532;
  }
  .s-red{
    color: red;
  }
  .box {
    .top {
      text-align: center;
    }
  }
  .nav-menu {
    width: 100%;
    background: #203160;
    position: fixed;
    z-index: 10000;
    top:0;
    .logo {
      margin-top: 14px;
      width: 85px;
    }
  }

  .userBox {
    line-height: 64px;
    color: rgba(255, 255, 255, 0.4);
    font-size: 12px;
    cursor: pointer;
    .goOut {
      padding: 10px 18px;

      &:hover {
        color: #03afff;
      }
    }
  }
  .el-collapse{
    position: relative;
  }
  .el-button {
    font-size: 12px;
  }
  .left-box {
    margin-right: 15px;
    overflow: hidden;
  }
  .right-box {
    padding: 20px;
    overflow-x: hidden;
  }
  .left-box,
  .right-box {
    background: #ffffff;
  }
  .project-lid {
    background: #9199b1;
    height: 60px;
    padding: 0 20px;
    color: #fff;
    line-height: 60px;
    font-size: 16px;
    overflow: hidden;
    label {
      color: #fff;
    }
  }
  .project-list {
    line-height: 42px;
    font-size: 14px;
    color: #333;
    width: 100%;
    height: calc(100vh - 110px);
    height: -moz-calc(100vh - 110px);
    height: -webkit-calc(100vh - 110px);
    height: calc(100vh - 110px);
    overflow-y: auto;
    overflow-x: hidden;
    .list {
      border-bottom: 1px solid #f4f4f4;
      cursor: pointer;
    }
    span {
      padding: 0 20px;
    }
  }

  .log-list {
    overflow-y: auto;
    overflow-x: hidden;
  }
  .log-repeat {
    .log-info {
      div {
        line-height: 20px;
        font-size: 13px;
        word-wrap:break-word;
        white-space:pre-wrap;
      }
    }
  }

  .el-collapse {
    border-width: 0;
  }
  .advanced-search {
    background: #fff;
    border-radius: 100px;
    font-size: 13px;
    color: #fff;
    height: 30px;
    line-height: 30px;
    padding: 0 20px;
  }
  .icon-zuidahua {
    color: #fff;
    font-size: 24px;
    &:hover {
      font-size: 29px;
      color: #fff;
    }
  }
  .zhiding {
    margin-top: 14px;
    font-size: 14px;
    color: #999;
    position: absolute;
    z-index: 1;
    right: 37px;
    .icon {
      color: #333;
    }
    .del-log{
      position: absolute;
      display: block;
      top: 0;
      right: -25px;
      color: #d8d8d8;
    }
    .del-log:hover {
      color: red;
    }
  }
</style>
