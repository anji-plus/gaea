<template>
  <div class="">
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
                          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                            <div class="mb20">
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
                              <el-button v-if="hasPermission('statisticsManage:find')" type="primary" class="c-button ml30" icon="el-icon-search" @click="quiryChartMyData(timeSelectedValue)">查询</el-button>
                            </div>
                          </el-col>
                        </el-row>
                        <el-row :gutter="12" class="pd-content2">
                          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                            <div class="grid-content mt10">
                              <div id="add" ref="chart" style="margin-top: 12px" :style="{ width: width, height: height }"></div>
                            </div>
                          </el-col>
                        </el-row>
                      </el-form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
  import echarts from "echarts";
  import 'echarts-gl';
  import Modal from "@/components/Modal.vue";
  import {chartsOption} from './chartsOptions';
  import { getPushStatistics} from "@/api/push/notify";
  import moment from 'moment';
  import miment from 'miment'
  const myEchars = require('echarts/lib/echarts');
  const myChart = {};
  const mainChart = {};
  export default {
    name: "projectDetails",
    data(){
      return{
        labelPosition: 'right',
        timeSelectedValue: null,//时间选择器的value
        form: {
          field:"",//搜索字段
        },
        radio2:1,
        isModalEditPro:false,
        tableData:[],
        isEditProject:true,
        //图表
        chart: null,
        chartIds:1,
        width: "70vw",
        height: "60vh",
        chartOption: null,
        startTime:'',
        endTime:'',
      }
    },
    components: {
      Modal,
    },
    watch: {
    },
    mounted(){
      this.myChart = this.$refs.chart;
      this.mainChart = myEchars.init(this.myChart);
      this.chartOption={};
      this.drawChart();
    },
    beforeDestroy() {
      this.mainChart.clear()
      this.chartOption={};
    },
    methods: {
      fanhui(){
        this.$router.push("/index");
      },
      closeModal () {
      },
      // 邮件列查询
      drawChart (val){
        this.chartOption = chartsOption;
        if(val!=null && this.timeSelectedValue!=null){
          this.startTime= moment(this.timeSelectedValue[0]).format("YYYY-MM-DD");
          this.endTime= moment(this.timeSelectedValue[1]).format("YYYY-MM-DD");
        }else {
          let startTime=new Date().getTime() - 3600 * 1000 * 24 * 7;
          let endTime=new Date().getTime()
          this.startTime=moment(startTime).format("YYYY-MM-DD");
          this.endTime= moment(endTime).format("YYYY-MM-DD");
        }
        let param ={
          startTime: this.startTime,
          endTime:  this.endTime,
          sendStatus:1
        };
        getPushStatistics(param).then(res=>{
          if(res.repCode=="0000"){ 
            this.chartOption.xAxis[0].data=res.repData.xaxisData;
            this.chartOption.xAxis[1].data=res.repData.xaxisData;

            let color= ['#2cb801', '#03bdbf', '#107acb','#dc770b', '#dc3c0b', '#e0b03d'];
            let series=[];
            for(let i=0;i<res.repData.seriesData.length;i++){
              series.push({
                name: res.repData.legendData[i]+'成功',
                type: "line",
                xAxisIndex: 0, 
                yAxisIndex: 0,
                color:color[i],
                symbolSize: 8,
                smooth: true,
                itemStyle:{
                  normal:{
                    areaStyle:{
                      color:new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                        offset: 0,
                        color: 'rgba(255,255,255,0)'
                      }, {
                        offset: 1,
                        color: color[i]
                      }]),
                    },
                    lineStyle:{
                      width:.5,
                    }
                  }
                },
                data:res.repData.seriesData[i].success,
              },{
                name: res.repData.legendData[i]+'失败',
                type: "line",
                xAxisIndex: 1,
                yAxisIndex: 1,
                symbolSize: 8,
                smooth: true,
                color:color[i+3],
                itemStyle:{
                  normal:{
                    areaStyle:{
                      color:new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                        offset: 0,
                        color: color[i +3]
                      }, {
                        offset: 1,
                        color:'rgba(255,255,255,0)'
                      }]),
                    },
                    lineStyle:{
                      width:.5,
                    }
                  }
                },
                data:res.repData.seriesData[i].fail,
              })
            } 
            this.chartOption.series=series;
            this.mainChart.setOption(this.chartOption,true);
          }
        });
      },
      editPro(val){
      },
      //时间区间查询图表邮件短信发送量
      quiryChartMyData(val){
        this.drawChart(val)
      }
    }
  }
</script>

<style scoped lang="scss">
  .row-bg{
    padding: 20px;
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
  .project-lid {
    background: #9199b1;
    height: 60px;
    padding: 0 20px;
    color: #fff;
    line-height: 60px;
    font-size: 16px;
    overflow: hidden;
    label {
      color:  #fff;
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
  }
  .log-list {
    min-height: calc(100vh - 90px);
    min-height: -moz-calc(100vh - 90px);
    min-height: -webkit-calc(100vh - 90px);
    min-height: calc(100vh - 90px);
    max-height: calc(100vh - 90px);
    max-height: -moz-calc(100vh - 90px);
    max-height: -webkit-calc(100vh - 90px);
    max-height: calc(100vh - 90px);
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
    background:  #fff;
    border-radius: 100px;
    font-size: 13px;
    color: #333;
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
    font-size:14px;
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
  .amount{
    padding: 40px;
    line-height: 40px;
    strong{
      font-size: 24px;
      line-height: 36px;
      i{
        font-size: 32px;
        margin-right: 6px;
      }
      &.yj{
        color: #DD4A68;

      }
      &.dx{
        color: #ea9015;

      }
      &.jg{
        color: #4bc70b;

      }
      &.ym{
        color: #3b89dd;

      }
    }
  }

  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
</style>
