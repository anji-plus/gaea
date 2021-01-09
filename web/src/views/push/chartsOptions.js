const chartsOption = {
  title: {
    text: "消息推送成功、失败对比"
  },
  axisPointer: {
    link: {
      xAxisIndex: "all",
    },
  },
  tooltip: {
    show: true,
    trigger: "axis",
    axisPointer: {
      type: "line",
      lineStyle: {
        type: "dashed",
      },
    },
  },
  legend: {
    show: true,
  },
  grid: [{
    left: 30,
    right: 20,
    top: "80px",
    containLabel: true,
    bottom: '50%',
  },{
    left: 30,
    containLabel: true,
    right: 20,
    top: '51%',
  }],
  xAxis: [{
    gridIndex: 0,
    type: "category",
    boundaryGap: false,
    axisLine: {
      onZero: false,
    },
    axisLabel: {
    },
    data: []},{
      gridIndex: 1,
      type: "category",
      position: 'top',
      boundaryGap: false,
      axisLine: {},
      axisLabel: {
        show: false,
      },
      data: [],
    }],
  yAxis: [{
    gridIndex: 0,
    name:"成功",
    scale: true,
    axisLine:{
      show: false
    },
    axisTick:{
      show: false
    },
    splitLine: {
      show: true,
      lineStyle: {
        color: '#E5E9ED',
      // 	opacity:0.1
      }
    }
  },{
    gridIndex: 1,
    name:"失败",
    scale: true,
    inverse: true,
    axisLine:{
      show: false
    },
    axisTick:{
      show: false
    },
    splitLine: {
      show: true,
      lineStyle: {
        color: '#E5E9ED',
      // 	opacity:0.1
      }
    }
  },
],
series: [],
};
export {chartsOption};
