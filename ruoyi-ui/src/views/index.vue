<template>
  <div class="app-container home">
    <el-row  type="flex" justify="space-around" class="row-bg">
      <el-card class="box-card cardDiv1">
        <el-col :span="5">
          <div class="box-div card1">
            <div class="webBox">
              <div class="webO">今日交易额:</div>
              <div class="webT">￥645334.45</div>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="box-div card2">
            <div class="webBox">
              <div class="webO">今日订单数量:</div>
              <div class="webT">7512</div>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="box-div card3">
            <div class="webBox">
              <div class="webO">今日访问人数:</div>
              <div class="webT">53101</div>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="box-div card4">
            <div class="webBox">
              <div class="webO">今日新增用户:</div>
              <div class="webT">8868</div>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="box-div card5">
            <div class="webBox">
              <div class="webO">累计用户量:</div>
              <div class="webT">895421</div>
            </div>
          </div>
        </el-col>
      </el-card>
 
    </el-row>
<!--    <el-divider />-->
    <el-row type="flex" justify="space-around" class="row-bg" style="margin-bottom:10px">
      <el-col :span="10">
        <el-card class="box-card" style="height: 320px;margin-right: 10px">
          <div id="main1" style="height: 300px;width:700px">
 
          </div>
        </el-card>
      </el-col>
      <el-col :span="7">
        <el-card class="box-card" style="height: 320px;margin-right: 10px">
        <div id="main2" style="height: 270px;width:400px">
 
        </div>
        </el-card>
      </el-col>
      <el-col :span="7">
        <el-card class="box-card" style="height: 320px;">
          <div id="main3" style="height: 270px;width:400px">
          </div>
        </el-card>
      </el-col>
    </el-row>
<!--    <el-divider />-->
    <el-row type="flex" justify="space-around" class="row-bg" >
        <el-col :span="14" style="margin-right: 10px">
          <el-card class="box-card cardDiv2">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px">订单信息</span>
              <el-badge :value="99" class="item">
                <el-button type="text" size="small">待处理订单</el-button>
              </el-badge>
              <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
            </div>
            <el-table
              :data="tableData2"
              height="300"
              style="width: 100%">
              <el-table-column
                prop="code"
                label="订单编号">
              </el-table-column>
              <el-table-column
                prop="date"
                label="下单日期">
              </el-table-column>
              <el-table-column
                prop="money"
                label="交易金额">
              </el-table-column>
              <el-table-column
                prop="name"
                label="客户名称">
              </el-table-column>
              <el-table-column
                prop="status"
                label="订单状态">
                <template slot-scope="scope">
                  <span v-if="scope.row.status==='1'" style="color: #FF9933">待发货</span>
                  <span v-if="scope.row.status==='2'" style="color: #FF5722">已退货</span>
                  <span v-if="scope.row.status==='3'" style="color: #1890ff">已取消</span>
                </template>
              </el-table-column>
              <el-table-column
                label="操作">
                <template slot-scope="scope">
                  <el-button  type="text" icon="el-icon-view" size="small">查看</el-button>
                  <el-button type="text" icon="el-icon-edit" size="small">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
      <el-col :span="10">
        <el-card class="box-card cardDiv2">
          <div slot="header" class="clearfix">
            <span>客户信息</span>
            <el-badge :value="99" class="item" style="visibility: hidden">
              <el-button type="text" size="small">待处理订单</el-button>
            </el-badge>
            <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
          </div>
            <el-table
              :data="tableData"
              height="300"
              style="width: 100%">
              <el-table-column
                prop="name"
                label="客户名称">
              </el-table-column>
              <el-table-column
                prop="phone"
                label="联系电话">
              </el-table-column>
              <el-table-column
                prop="money"
                label="消费金额">
              </el-table-column>
              <el-table-column
                prop="level"
                label="会员级别">
                <template slot-scope="scope">
 
                  <span v-if="scope.row.level==='1'"><el-tag type="danger">至尊</el-tag></span>
                  <span v-if="scope.row.level==='2'"><el-tag type="success">钻石</el-tag></span>
                  <span v-if="scope.row.level==='3'"><el-tag type="warning">黄金</el-tag></span>
                  <span v-if="scope.row.level==='4'"><el-tag type="info">普通</el-tag></span>
                </template>
              </el-table-column>
              <el-table-column
                label="操作">
                <template slot-scope="scope">
                  <el-button  type="text" icon="el-icon-view" size="small">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
 
<script>
import {userhomepagebasicinfo,listlatestfileinfo,getdeptfilenum,getmostpopularfileinfo} from "@/api/system/homepage"; 
import * as echarts from 'echarts'
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.6",
      tableData: [{
        money: '8856.56',
        name: '张三',
        phone: '18888888888',
        level:'1'
      }, {
        money: '2384.50',
        name: '李四',
        phone: '18888888888',
        level:'2'
      }, {
        money: '6000.20',
        name: '王五',
        phone: '13688888888',
        level:'3'
      }, {
        money: '5558.34',
        name: '赵六',
        phone: '13688888888',
        level:'4'
      },
        {
          money: '1234.25',
          name: '钱七',
          phone: '13688888888',
          level:'3'
        },
        {
          money: '1234.25',
          name: '钱七',
          phone: '13688888888',
          level:'3'
        },
        {
          money: '1234.25',
          name: '钱七',
          phone: '13688888888',
          level:'2'
        }
      ],
      tableData2: [
        {
        date: '2016-05-02',
        name: '张三',
        code: '454545454234455',
        money: '2422',
        status: '1',
      },
      {
        date: '2016-05-02',
        name: '李四',
        code: '234234234324234',
        money: '7870.00',
        status: '1',
      },
        {
          date: '2016-05-02',
          name: '王五',
          code: '23523523544644',
          money: '34545.2',
          status: '1',
        },
        {
          date: '2016-05-02',
          name: '赵六',
          code: '25235235235325',
          money: '3430',
          status: '2',
        },
        {
          date: '2016-05-02',
          name: '钱七',
          code: '25235235235325',
          money: '3430',
          status: '3',
        },
      ]
    };
  },
  created() {
    this.getcurrentuserbasicinfonum();
    this.getlatestfilelist();
    this.getpopularfilelist();
    this.getdeptpublishfilenum();
  },
  mounted() {
    this.setLineData()
    this.setbData()
    this.setBireData()
  },
  methods: {
    // 获取当前用户的已办文档、待审阅、待定稿、待修改数量
    getcurrentuserbasicinfonum(){
      this.loading = true;
      userhomepagebasicinfo(this.$store.state.user.id).then(response => {
          this.userbasicnum = response.data;
          this.loading = false;
        }
      );
    },
    // 获得最新发布的文档
    getlatestfilelist(){
      this.loading = true;
      listlatestfileinfo().then(response => {
          this.latestfilelist = response.rows;
          this.loading = false;
        }
      );
    },
    // 获得最多下载的文档
    getpopularfilelist(){
      this.loading = true;
      getmostpopularfileinfo().then(response => {
          this.popularfilelist = response.rows;
          this.loading = false;
        }
      );
    },
    // 获取各个部门发布的文档数量
    getdeptpublishfilenum(){
      this.loading = true;
      getdeptfilenum().then(response => {
          this.deptfilenum = response.rows;
          this.loading = false;
          console.log(this);
        }
      );
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    setLineData(){
      var chartDom = document.getElementById('main1');
      var myChart = echarts.init(chartDom);
      var option;
 
      option = {
        title: [{
          text: '一周交易额',
          textStyle: {
            fontSize: 15
          },
 
        },
        {
          text: '单位:万元',
          left:'80%',
          textStyle:{
            fontSize:13,
            fontWeight:100
          }
        }],
        xAxis: {
          type: 'category',
          data: ['3-16', '3-17', '3-18', '3-19', '3-20', '3-21', '3-22']
        },
        yAxis: {
          type: 'value',
          splitLine:{
            show:true,
            lineStyle:{
              type:'dashed'
            }
          }
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line',
            itemStyle: {
              normal: {
                color: "#2ec7c9",
                lineStyle: {
                  color: "rgb(30 198 149)"
                }
              }
            },
          }
        ]
      };
 
      option && myChart.setOption(option);
    },
    setbData(){
 
      var chartDom = document.getElementById('main2');
      var myChart = echarts.init(chartDom);
      var option;
 
      option = {
        color: ['#49bafc', '#e9898f', '#f7b230', '#bd92e1', '#26c8aa'],
        title: {
          text: '商品销售额占比',
          left: 'left',
          textStyle: {
            fontSize: 15
          },
 
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          bottom: 1,
          left: 'center',
          data: ['食品', '服装', '家电', '日用品', '原材料']
        },
        series: [
          {
            type: 'pie',
            radius: '65%',
            center: ['50%', '45%'],
            selectedMode: 'single',
            data: [
              { value: 1548,name: '原材料'},
              { value: 735, name: '日用品'},
              { value: 510, name: '家电' },
              { value: 434, name: '服装' },
              { value: 335, name: '食品' }
            ],
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
 
      option && myChart.setOption(option);
 
    },
    setBireData(){
      var chartDom = document.getElementById('main3');
      var myChart = echarts.init(chartDom);
      var option;
 
      option = {
        title: {
          text: '订单状态',
          textStyle: {
            fontSize: 15
          },
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true,//设置自适应画布大小状态为开，也可通过设置left左移实现相同效果
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01],
          splitLine:{
            show:false
          },
          "axisLine":{     //x轴坐标轴
            "show":false
          },
          axisLabel:{
            show: false
          },
          axisTick: {		//x轴刻度线
            show:false
          },
        },
        yAxis: {
          type: 'category', //纵向改成横向柱状图
          data: ['今日取消订单', '今日评价订单','今日退货订单','今日发货订单',  ], //y轴显示文字
          axisTick: {
            show: false
          },
          axisLine: {
            show: false,
          },
        },
        series: [
          {
            name: '',
            type: 'bar',
            barWidth: 20,
            showSymbol: false,
            label:{ //柱状图显示数值
              show:true,
              position: 'insideTop', //内部顶部显示
              textStyle: {
                color: '#FFF', //字体颜色
                fontSize: 14 //字体大小
              }
            },
            data: [{
              value:1546,
              itemStyle: {
                normal: {
                  barBorderRadius: [0, 6, 6, 0],
                  color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{ //1000纵向柱状图渐变色 0100横向柱状图渐变色
                    offset: 0,
                    color: "rgba(30, 198, 149, 1)" // 0% 处的颜色
                  }, {
                    offset: 0.6,
                    color: "rgba(30, 198, 149, 1)" // 60% 处的颜色
                  }, {
                    offset: 1,
                    color: "rgba(51, 204, 204, 1)" // 100% 处的颜色
                  }], false)
                }
              },
            },
              {
                value:860,
                itemStyle: {
                  normal: {
                    barBorderRadius: [0, 6, 6, 0],//顺时针左上，右上，右下，左下）
                    color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                      offset: 0,
                      color: "rgba(170, 137, 254, 1)" // 0% 处的颜色
                    }, {
                      offset: 0.6,
                      color: "rgba(170, 137, 254, 1)" // 60% 处的颜色
                    }, {
                      offset: 1,
                      color: "rgba(204, 153, 204, 1)" // 100% 处的颜色
                    }], false)
                  }
                },
 
              },
              {
                value:1022,
                itemStyle: {
                  normal: {
                    barBorderRadius: [0, 6, 6, 0],
                    color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                      offset: 0,
                      color: "rgba(226, 113, 140, 1)" // 0% 处的颜色
                    }, {
                      offset: 0.6,
                      color: "rgba(244, 174, 149, 1)" // 60% 处的颜色
                    }, {
                      offset: 1,
                      color: "rgba(244, 174, 149, 1)" // 100% 处的颜色
                    }], false)
                  }
                },
 
              },
              {
                value:1600,
                itemStyle: {
                  normal: {
                    barBorderRadius: [0, 6, 6, 0],
                    color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                      offset: 0,
                      color: "rgba(255, 153, 51, 1)" // 0% 处的颜色
                    }, {
                      offset: 0.6,
                      color: "rgba(255, 153, 51, 1)" // 60% 处的颜色
                    }, {
                      offset: 1,
                      color: "rgba(239, 203, 45, 1)" // 100% 处的颜色
                    }], false)
                  }
                },
              },
 
            ]
          },
        ]
      };
 
      option && myChart.setOption(option);
 
    }
  }
};
</script>
 
<style scoped lang="scss">
  ::v-deep .el-card__body {
    padding: 15px 20px 20px 20px;
    display: flex;
    justify-content: center;
  }
  .box-div{
    height: 120px;
    padding: 35px;
    margin: 20px;
  }
  .cardDiv1{
    width: 100%;
    margin-bottom: 10px;
  }
  .cardDiv2{
    width: 100%;
    margin-bottom: 20px;
  }
  .app-container {
    padding: 20px;
    background: #eeeeee50;
  }
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }
 
  ul {
    padding: 0;
    margin: 0;
  }
 
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
 
  ul {
    list-style-type: none;
  }
 
  h4 {
    margin-top: 0px;
  }
 
  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }
 
  p {
    margin-top: 10px;
 
    b {
      font-weight: 700;
    }
  }
 
  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
  .webBox{
    display: -webkit-box;
    -webkit-box-orient: vertical;
  }
  .webO{
    display: -webkit-box;
    -webkit-box-pack: start;
    -webkit-box-align: center;
    font-size: 14px;
    font-weight: 700;
    color: #fff;
  }
  .webT{
    display: -webkit-box;
    -webkit-box-pack: end;
    -webkit-box-align: center;
    font-size: 20px;
    font-weight: 700;
    padding-right: 50px;
    padding-top: 10px;
    color: #fff;
  }
  .card1{
    background: linear-gradient(180deg, rgba(30, 198, 149, 1) 0%, rgba(30, 198, 149, 1) 0%, rgba(51, 204, 204, 1) 100%, rgba(51, 204, 204, 1) 100%);
    border: none;
    border-radius: 4px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
  .card2{
    background: linear-gradient(90deg, rgba(244, 174, 149, 1) 0%, rgba(244, 174, 149, 1) 0%, rgba(226, 113, 140, 1) 100%, rgba(226, 113, 140, 1) 100%);
    border: none;
    border-radius: 4px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
  .card3{
    background: linear-gradient(180deg, rgba(255, 153, 51, 1) 0%, rgba(255, 153, 51, 1) 0%, rgba(239, 203, 45, 1) 100%, rgba(239, 203, 45, 1) 100%);
    border: none;
    border-radius: 4px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
  .card4{
    background: linear-gradient(180deg, rgba(45, 169, 250, 1) 0%, rgba(45, 169, 250, 1) 0%, rgba(102, 204, 255, 1) 100%, rgba(102, 204, 255, 1) 100%);
    border: none;
    border-radius: 4px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
  .card5{
    background: linear-gradient(180deg, rgba(170, 137, 254, 1) 0%, rgba(170, 137, 254, 1) 0%, rgba(204, 153, 204, 1) 100%, rgba(204, 153, 204, 1) 100%);
    border: none;
    border-radius: 4px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
}
</style>
 