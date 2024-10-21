<template>
  <div>
    <div class="chart-container">
      <div id="deptFileNumChart" class="chart"></div>
      <div id="classifiedFileNumChart" class="chart"></div>
      <div id="deptDownloadFileNumChart" class="chart"></div>
      <div id="classifiedDownloadFileNumChart" class="chart"></div>
      <div id="yearFileNumChart" class="chart"></div>
      <div id="monthFileNumChart" class="chart"></div>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts';
import { getdeptfilenum,getclassifiedfilenum,getyearfilenum,getmonthfilenum,getdetpdownloadfilenum,getclassifieddownloadfilenum } from "@/api/system/statistics"
export default {
  name: "Index",
  data() {
    return {
      deptfilenum: [],
      classifiedfilenum: [],
      detpdownloadfilenum: [],
      classifieddownloadfilenum: [],
      yearfilenum: [],
      monthfilenum: [],
      loading: false
    };
  },
  created() {
    this.getallnum();
  },
  mounted(){
    this.$nextTick(() => {
      this.renderCharts() // 初始化图表
    });
  },
  methods: {
    // 获得各个分类下的文件数
    getallnum() {
      this.loading = true;
      // 逐个请求各个统计数据
      Promise.all([
        getdeptfilenum(),
        getclassifiedfilenum(),
        getdetpdownloadfilenum(),
        getclassifieddownloadfilenum(),
        getyearfilenum(),
        getmonthfilenum()
      ]).then(responses => {
        this.deptfilenum = responses[0].rows;
        this.classifiedfilenum = responses[1].rows;
        this.detpdownloadfilenum = responses[2].rows;
        this.classifieddownloadfilenum = responses[3].rows;
        this.yearfilenum = responses[4].rows;
        this.monthfilenum = responses[5].rows;
        this.loading = false;
        // 确保在 DOM 更新后再渲染图表
        this.$nextTick(() => {
          this.renderCharts();
        });
      }).catch(error => {
        console.error("Error fetching data:", error);
        this.loading = false;
      });
    },
    renderCharts() {
      // 渲染各个图表
      this.renderPieChart('deptFileNumChart', this.deptfilenum, '各团队文件数');
      this.renderPieChart('classifiedFileNumChart', this.classifiedfilenum, '各分类文件数');
      this.renderPieChart('deptDownloadFileNumChart', this.detpdownloadfilenum, '各团队下载文件数');
      this.renderPieChart('classifiedDownloadFileNumChart', this.classifieddownloadfilenum, '各分类下载文件数');
      this.renderBarChart('yearFileNumChart', this.yearfilenum, '按年统计文件数');
      this.renderBarChart('monthFileNumChart', this.monthfilenum, '按月统计文件数');
    },
    renderPieChart(elementId, data, title) {
      let chartDom = document.getElementById(elementId);
      let myChart = echarts.init(chartDom);
      let option = {
        title: {
          text: title,
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend:{
          orient:'vertical',
          x:'left',
          y:'center',
          formatter: function (name) {
            let item = data.find(i => i.label === name);
            return name + ' (' + item.fileNum + ')';
          }
        },
        series: [
          {
            name: title,
            type: 'pie',
            radius: '50%',
            data: data.map(item => ({ name: item.label, value: item.fileNum})),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.setOption(option);
    },
    renderBarChart(elementId, data, title) {
      let chartDom = document.getElementById(elementId);
      let myChart = echarts.init(chartDom);
      let option = {
        title: {
          text: title,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: data.map(item => item.label),
          axisLabel: {
            interval: 0, // 强制显示所有标签
            rotate: 45  // 旋转标签以避免重叠
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: title,
            type: 'bar',
            data: data.map(item => item.fileNum)
          }
        ]
      };
      myChart.setOption(option);
    }
  }
}
</script>
<style scoped>
/* 添加必要的样式 */
.chart-container {
  padding-top: 100px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  max-width: 1260px;
  margin: auto;
}
.chart {
  width: 700px;
  height: 400px;
}
</style>