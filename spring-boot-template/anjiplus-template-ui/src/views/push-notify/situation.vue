<template>
  <div class="app-container">
    <el-form ref="form" :label-position="labelPosition" label-width="100px">
      <el-row>
        <el-col :span="18">
          <el-form-item label="选择日期" prop="actionCode">
            <el-date-picker v-model="timeSelectedValue" type="daterange" align="left" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd HH:mm:ss" />
          </el-form-item>
        </el-col>
        <el-col :span="6" style="text-align: center">
          <el-button type="primary" @click="quiryChartMyData(timeSelectedValue)">{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="reset">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <div style="width: 100%">
            <div ref="chart" style="margin-top: 12px" :style="{ width: width, height: height }" />
          </div>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { generalSituationReceiving } from '@/api/push-notify'
import echarts from 'echarts'
// import 'echarts-gl';
import moment from 'moment'
const myEchars = require('echarts/lib/echarts')
// const myChart = {};
// const mainChart = {}
export default {
  name: 'ProjectDetails',
  components: {
    // Modal,
  },
  data() {
    return {
      labelPosition: 'right',
      timeSelectedValue: null, // 时间选择器的value
      // 图表
      chart: null,
      width: '100%',
      height: '80vh',
      chartOption: null,
      startTime: '',
      endTime: '',
    }
  },
  watch: {},
  mounted() {
    this.myChart = this.$refs.chart
    // this.mainChart = this.$echarts.init(this.myChart);
    this.mainChart = myEchars.init(this.myChart)
    this.chartOption = {}
    this.drawChart()
  },
  beforeDestroy() {
    this.mainChart.clear()
    this.chartOption = {}
  },
  methods: {
    reset() {
      this.drawChart()
    },
    // 图表基础配置
    options() {
      const data = {
        title: {
          text: '消息推送成功、失败对比',
        },
        axisPointer: {
          link: {
            xAxisIndex: 'all',
          },
        },
        tooltip: {
          show: true,
          trigger: 'axis',
          axisPointer: {
            type: 'line',
            lineStyle: {
              type: 'dashed',
            },
          },
        },
        legend: {
          show: true,
        },
        grid: [
          {
            left: '20',
            right: '20',
            top: '80px',
            bottom: '50%',
            containLabel: true,
          },
          {
            left: '20',
            right: '20',
            top: '52%',
            containLabel: true,
          },
        ],
        xAxis: [
          {
            gridIndex: 0,
            type: 'category',
            boundaryGap: false,
            axisLine: {
              onZero: false,
            },
            axisLabel: {},
            data: [],
          },
          {
            gridIndex: 1,
            type: 'category',
            position: 'top',
            boundaryGap: false,
            axisLine: {},
            axisLabel: {
              show: false,
            },
            data: [],
          },
        ],
        yAxis: [
          {
            gridIndex: 0,
            name: '成功',
            scale: true,
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#E5E9ED',
                // 	opacity:0.1
              },
            },
          },
          {
            gridIndex: 1,
            name: '失败',
            scale: true,
            inverse: true,
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#E5E9ED',
                // 	opacity:0.1
              },
            },
          },
        ],
        series: [],
      }
      return data
    },
    // 格式化时间格式，用于请求图表数据
    formatTime(val) {
      if (val != null && this.timeSelectedValue != null) {
        this.startTime = moment(this.timeSelectedValue[0]).format('YYYY-MM-DD')
        this.endTime = moment(this.timeSelectedValue[1]).format('YYYY-MM-DD')
      } else {
        const startTime = new Date().getTime() - 3600 * 1000 * 24 * 7
        const endTime = new Date().getTime()
        this.startTime = moment(startTime).format('YYYY-MM-DD')
        this.endTime = moment(endTime).format('YYYY-MM-DD')
      }
    },
    // 邮件列查询
    drawChart(val) {
      // 初始化默认配置
      this.chartOption = this.options()
      // 格式化时间格式
      this.formatTime(val)
      const param = {
        startTime: this.startTime,
        endTime: this.endTime,
        // sendStatus: 1,
      }
      generalSituationReceiving(param).then((res) => {
        console.log(res)
        // getPushStatistics(param).then(res=>{
        if (res.code == '200') {
          // 给X轴赋值
          this.chartOption.xAxis[0].data = res.data.xaxisData
          this.chartOption.xAxis[1].data = res.data.xaxisData

          const color = ['#2cb801', '#03bdbf', '#107acb', '#dc770b', '#dc3c0b', '#e0b03d']
          const series = []
          for (let i = 0; i < res.data.seriesData.length; i++) {
            series.push(
              {
                name: res.data.legendData[i] + '成功',
                type: 'line',
                xAxisIndex: 0,
                yAxisIndex: 0,
                color: color[i],
                symbolSize: 8,
                smooth: true,
                itemStyle: {
                  normal: {
                    areaStyle: {
                      color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                        {
                          offset: 0,
                          color: 'rgba(255,255,255,0)',
                        },
                        {
                          offset: 1,
                          color: color[i],
                        },
                      ]),
                    },
                    lineStyle: {
                      width: 0.5,
                    },
                  },
                },
                data: res.data.seriesData[i].success,
              },
              {
                name: res.data.legendData[i] + '失败',
                type: 'line',
                xAxisIndex: 1,
                yAxisIndex: 1,
                symbolSize: 8,
                smooth: true,
                color: color[i + 3],
                itemStyle: {
                  normal: {
                    areaStyle: {
                      color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                        {
                          offset: 0,
                          color: color[i + 3],
                        },
                        {
                          offset: 1,
                          color: 'rgba(255,255,255,0)',
                        },
                      ]),
                    },
                    lineStyle: {
                      width: 0.5,
                    },
                  },
                },
                data: res.data.seriesData[i].fail,
              }
            )
          }
          this.chartOption.series = series
          this.mainChart.setOption(this.chartOption, true)
        }
      })
    },
    // 时间区间查询图表邮件短信发送量
    quiryChartMyData(val) {
      this.drawChart(val)
    },
  },
}
</script>

<style scoped lang="scss"></style>
