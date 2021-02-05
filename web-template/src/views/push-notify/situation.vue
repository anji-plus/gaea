<template>
  <div class="app-container">
    <el-form ref="form" :label-position="labelPosition" label-width="100px">
      <el-row>
        <el-col :span="18">
          <el-form-item label="选择日期" prop="actionCode">
            <el-date-picker v-model="timeSelectedValue" type="daterange" align="left" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" />
          </el-form-item>
        </el-col>
        <el-col :span="6" style="text-align: center">
          <el-button type="primary" @click="quiryChartMyData(timeSelectedValue)">{{ $t('btn.query') }}</el-button>
          <el-button type="danger">{{ $t('btn.reset') }}</el-button>
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
      // const param = {
      //   startTime: this.startTime,
      //   endTime: this.endTime,
      //   sendStatus: 1,
      // }
      // getPushStatistics(param).then(res=>{
      const res = {
        repCode: '0000',
        repMsg: null,
        repData: {
          legendData: ['邮件', '短信', '钉钉'],
          seriesData: [
            {
              fail: ['0', '0', '0', '0', '0', '0', '0', '0', '40', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
              success: ['0', '0', '3', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '10', '230', '440', '430', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
            },
            {
              fail: ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '220', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
              success: ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
            },
            {
              fail: ['0', '0', '0', '0', '30', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
              success: ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
            },
          ],
          startTime: null,
          endTime: null,
          sendTime: null,
          sendStatus: null,
          xaxisData: [
            '2021-01-04',
            '2021-01-05',
            '2021-01-06',
            '2021-01-07',
            '2021-01-08',
            '2021-01-09',
            '2021-01-10',
            '2021-01-11',
            '2021-01-12',
            '2021-01-13',
            '2021-01-14',
            '2021-01-15',
            '2021-01-16',
            '2021-01-17',
            '2021-01-18',
            '2021-01-19',
            '2021-01-20',
            '2021-01-21',
            '2021-01-22',
            '2021-01-23',
            '2021-01-24',
            '2021-01-25',
            '2021-01-26',
            '2021-01-27',
            '2021-01-28',
            '2021-01-29',
            '2021-01-30',
            '2021-01-31',
            '2021-02-01',
            '2021-02-02',
            '2021-02-03',
            '2021-02-04',
            '2021-02-05',
            '2021-02-06',
            '2021-02-07',
            '2021-02-08',
            '2021-02-09',
            '2021-02-10',
            '2021-02-11',
            '2021-02-12',
            '2021-02-13',
            '2021-02-14',
            '2021-02-15',
            '2021-02-16',
            '2021-02-17',
            '2021-02-18',
            '2021-02-19',
            '2021-02-20',
            '2021-02-21',
            '2021-02-22',
            '2021-02-23',
            '2021-02-24',
            '2021-02-25',
            '2021-02-26',
            '2021-02-27',
            '2021-02-28',
          ],
          yaxisData: null,
        },
        success: true,
        error: false,
      }
      if (res.repCode == '0000') {
        // 给X轴赋值
        this.chartOption.xAxis[0].data = res.repData.xaxisData
        this.chartOption.xAxis[1].data = res.repData.xaxisData

        const color = ['#2cb801', '#03bdbf', '#107acb', '#dc770b', '#dc3c0b', '#e0b03d']
        const series = []
        for (let i = 0; i < res.repData.seriesData.length; i++) {
          series.push(
            {
              name: res.repData.legendData[i] + '成功',
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
              data: res.repData.seriesData[i].success,
            },
            {
              name: res.repData.legendData[i] + '失败',
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
              data: res.repData.seriesData[i].fail,
            }
          )
        }
        this.chartOption.series = series
        this.mainChart.setOption(this.chartOption, true)
      }
      // });
    },
    // 时间区间查询图表邮件短信发送量
    quiryChartMyData(val) {
      this.drawChart(val)
    },
  },
}
</script>

<style scoped lang="scss"></style>
