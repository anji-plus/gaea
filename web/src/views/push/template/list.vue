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
                              <el-button v-if="hasPermission('templateManage:find')" class="c-button mr20" type="primary" icon="el-icon-search" @click="queryByPage">查询</el-button>
                              <!-- <el-button class="c-button" type="info" >重置</el-button> -->
                            </div>
                          </el-col>
                        </el-row>
                      </el-form>
                    </div>
                  </div>
                  <div class="mt10" style="width: 100%;float: left">
                    <div class="log-repeat">
                      <el-row :gutter="12" class="pd-content2">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <div class="grid-content">
                            <!-- <el-button class="c-button" type="primary" @click="Tutorial">使用教程</el-button> -->
                            <el-button v-if="hasPermission('templateManage:add')" class="c-button" type="success" @click="addOrEditMailDemo(undefined)">新增模板</el-button>
                          </div>
                        </el-col>
                      </el-row>
                      <el-row :gutter="12" class="pd-content2 mt10">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <div class="grid-content">
                            <el-table stripe border style="width: 100%" :data="tableData">
                              <el-table-column width="90" label="序号" align="center">
                                <template slot-scope="scope"><span>{{scope.$index+(pageNum - 1) * pageSize + 1}} </span></template>
                              </el-table-column>
                              <el-table-column prop="templateName" label="模板名称" align="left" header-align="center" :show-overflow-tooltip="true" ></el-table-column>
                              <el-table-column label="推送类型" width="90">
                                <template slot-scope="scope">
                                   {{ scope.row.templateType | basecode('ALERT_CHANNEL') }}
                                  <!-- {{getDictCode('ALERT_CHANNEL',scope.row.templateType,"labelEng").label}} -->
                                </template>
                              </el-table-column>
                              <el-table-column prop="templateCode" label="模板代码" align="center" width="160"></el-table-column>
                              <el-table-column prop="templateParam" label="模板参数" align="center" :show-overflow-tooltip="true"></el-table-column>
                              <el-table-column prop="updatedBy" label="创建者" align="center" width="120"></el-table-column>
                              <el-table-column prop="createdTime" :formatter="dateFormat" align="center" label="创建时间" width="160"></el-table-column>
                              <el-table-column prop="updatedTime" :formatter="dateFormat" align="center" label="修改时间" width="160"></el-table-column>
                              
                              <el-table-column label="操作"  width="160" align="center">
                                <template slot-scope="scope">
                                  <el-tooltip class="item" effect="dark" content="短信测试" placement="top" v-if="scope.row.templateType =='sms'">
                                    <el-button size="mini" type="warning" icon="el-icon-chat-dot-square" :plain="true" circle @click="testSms(scope.row)"></el-button>
                                  </el-tooltip>
                                  <el-tooltip class="item" effect="dark" content="查看" placement="top" v-else>
                                    <el-button size="mini" icon="el-icon-search" :plain="true" circle @click="Preview(scope.row)"></el-button>
                                  </el-tooltip>
                                  <el-tooltip class="item" effect="dark" content="查看" placement="top">
                                    <el-button v-if="hasPermission('templateManage:find') && !hasPermission('templateManage:edit')" size="mini" type="success" icon="el-icon-view" :plain="true" circle @click="queryOrEditMailDemo(scope.$index,scope.row)"></el-button>
                                  </el-tooltip>
                                  <el-tooltip class="item" effect="dark" content="编辑" placement="top">
                                    <el-button v-if="hasPermission('templateManage:edit')" type="primary" size="mini" icon="el-icon-edit" :plain="true" circle @click="addOrEditMailDemo(scope.$index,scope.row)"></el-button>
                                  </el-tooltip>
                                  <el-tooltip class="item" effect="dark" content="删除" placement="top">
                                    <el-button v-if="hasPermission('templateManage:delete')" type="danger" size="mini" icon="el-icon-delete" :plain="true" circle @click="DemoDel(scope.row.templateId)"></el-button>
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
      <div v-if="isModalTrueVisible">
        <Modal ref="Modal" v-show="isModalVisible" :statusText="statusText" @closeModal="closeModal" contentText="index.vue" sure="确定" alert="1">
          <Edit :proData="proData" :templateDeployType="templateDeployType" :isFind="isFind" @closeModal="closeModal"></Edit>
        </Modal>
      </div>
      <div v-if="isModalTrueMailDemo">
        <Modal ref="Modal" v-show="isModalMailDemo" statusText="模板详情" @closeModal="closeModal" contentText="index.vue" sure="确定" alert="1">
          <Details :templateDetails="templateDetails" @closeModal="closeModal"></Details>
        </Modal>
      </div>
      <div v-if="isTsetSms">
        <Modal ref="Modal" v-show="isModalTsetSms" statusText="模板查看" @closeModal="closeModal" contentText="index.vue" sure="确定" alert="1">
          <SMSTest :proTestSmsData="proTestSmsData" :templateDeployType="templateDeployType" @closeModal="closeModal"></SMSTest>
        </Modal>
      </div>
    </div>
</template>

<script>
  import Modal from "@/components/Modal.vue";
  import Edit from "./Modal-edit";
  import Details from "./Modal-details";
  import SMSTest from "./Modal-SMSTest";
  import {queryTemplateList,deleteTemplate,showTemplateById} from '@/api/push/notify';
  import parseTime from '@/utils/index'
  import moment from 'moment'
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
        statusText:'添加模板',
        timeSelectedValue: null, //时间选择器的value
        form: {
          field:"",              //搜索字段
          templateType:'',
        },
        proData:null,
        isModalVisible:false,    //显示隐藏新增编辑弹出框
        editFlag:true,           //新增编辑判断
        tableData:[],            //模板列表
        isModalMailDemo:false,   //显示隐藏模板弹出框
        templateDetails:{},      //模板查看的详情 
        isModalTrueVisible:false,
        isModalTrueMailDemo:false, 
        beginDate:"",
        endDate:"",

        //判断 类型
        mailTo:"收件人",
        isTsetSms:false,
        isModalTsetSms:false,
        proTestSmsData:null,
        templateType:'',
        templateDeployType:'',
        isFind: true
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
      Edit,
      Details,
      SMSTest,
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
        let param ={
          sentTime:this.timeSelectedValue,
          keyword:this.form.field,
          pageSize:this.pageSize,
          currentPage:this.pageNum,
          startTime:this.beginDate,
          endTime:this.endDate,
          templateType:this.getDictCode('ALERT_CHANNEL' ,this.form.templateType).labelEng
        };
        queryTemplateList(param).then(res=>{
          if(res.repCode=="0000"){
            this.tableData=res.repData.list;
            this.total = res.repData.totalCount;
            this.currentPage4 = res.repData.currentPage;
          }
        })
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
        this.isModalTrueVisible=false;
        this.isModalVisible=false;
        this.isModalMailDemo=false; 
        this.isTsetSms=false;
        this.isModalTsetSms=false;
        this.$refs.form1 && this.$refs.form1.resetFields()
        this.queryByPage()
      },
      fanhui(){
        this.$router.push("/index");
      },
       //获取单封模板信息
      Preview(val){
        this.isModalMailDemo=true;
        this.isModalTrueMailDemo=true;
        showTemplateById({templateId:val.templateId}).then(res => {
          if (res.repCode == "0000") {
            this.templateDetails=res.repData;
          }
        }).catch(error => { });
      },
      queryOrEditMailDemo(index, val) {
        this.statusText='查看模板';
        this.proData=val;
        this.templateDeployType=val.templateType
        this.isModalVisible=true;
        this.isModalTrueVisible=true;
        this.isFind = false
      },
      addOrEditMailDemo(index,val){
        this.isFind = true
        if(val===undefined){
          this.statusText='新增模板';
          this.proData=null;
          this.templateDeployType=null;
        }else {
          this.statusText='编辑模板';
          this.proData=val;
          this.templateDeployType=val.templateType
        }
        this.isModalVisible=true;
        this.isModalTrueVisible=true;
      },
      //删除模板
      DemoDel(val){
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params= {
            templateType:this.templateDeployType,
            templateId: val
          };
          deleteTemplate(params).then(response => {
            if (response.repCode == '0000') {
              this.$message({ message: '删除成功', type: 'success', duration: 1500 })
              this.queryByPage()
            }
          })
        }).catch(() => {})
      },
      Tutorial(){
        this.$router.push({path: '/push/push/tutorial'})
      },
      Reset(){
        this.form.field='';
        this.beginDate='';
        this.endDate='';
        this.timeSelectedValue=null;
        this.queryByPage();
      },
      testSms(row){
        this.isTsetSms=true;
        this.isModalTsetSms=true;
        this.proTestSmsData=row
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
