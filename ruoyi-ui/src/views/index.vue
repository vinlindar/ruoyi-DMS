<template>
  <div class="app-container home">
    <el-row  type="flex" justify="space-around" class="row-bg">
      <el-card class="box-card cardDiv1">
        <el-col :span="6">
          <div class="box-div card1">
            <div class="webBox">
              <div class="webO">我的已办:</div>
              <div class="webT">{{userbasicnum.publishNum}}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <a href="system/dmsfileupload">
            <div class="box-div card2">
              <div class="webBox">
                <div class="webO">待修改文件:</div>
                <div class="webT">{{userbasicnum.waitModifyNum}}</div>
              </div>
            </div>
          </a>
        </el-col>
        <el-col :span="6">
          <a href="system/review">
            <div class="box-div card3">
              <div class="webBox">
                <div class="webO">待评阅文件:</div>
                <div class="webT">{{userbasicnum.waitReviewNum}}</div>
              </div>
            </div>
          </a>
        </el-col>
        <el-col :span="6">
          <a href="system/publish">
            <div class="box-div card4">
              <div class="webBox">
                <div class="webO">待定稿文件:</div>
                <div class="webT">{{userbasicnum.waitPublishNum}}</div>
              </div>
            </div>
            </a>
        </el-col>
      </el-card>
    </el-row>

    <el-row type="flex" justify="space-around" class="row-bg" >
      <el-col :span="16">
          <el-card class="box-card cardDiv2">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px">近期文档</span>
            </div>
            <el-table :data="latestfilelist" height="300" style="width: 100%">
              <el-table-column prop="fileId" label="文档ID" align="center"> </el-table-column>
              <el-table-column prop="fileName" label="文档名" align="center"> </el-table-column>
              <el-table-column prop="updateBy" label="上传人" align="center"> </el-table-column>
              <el-table-column prop="fileType" label="文件类型" align="center"> 
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.dms_file_type" :value="scope.row.fileType"/>
                </template>
              </el-table-column>
              <el-table-column prop= "belongteam" label="归属团队" align="center"> </el-table-column>
              <el-table-column prop="publishTime" label="发布时间" align="center"> </el-table-column>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="16">
          <el-card class="box-card cardDiv2">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px">各团队文件数</span>
            </div>
          <div id="pie-chart" style="height: 300px;width:300px"></div>
          </el-card>
        </el-col>
      <el-col :span="16">
        <el-card class="box-card cardDiv2">
          <div slot="header" class="clearfix">
            <span>下载排行</span>
          </div>
          <el-table :data="popularfilelist" height="300" style="width: 100%">
              <el-table-column prop= "fileId" label="文件编号" align="center"> </el-table-column>
              <el-table-column prop= "fileName" label="文件名" align="center"> </el-table-column>
              <el-table-column prop= "belongteam" label="归属团队" align="center"> </el-table-column>
              <el-table-column prop= "downloadCount" label="下载次数" align="center"> </el-table-column>
            </el-table>
          </el-card>
      </el-col>
    </el-row>
    <el-row type="flex" justify="space-around" class="row-bg" >
      <el-card class="box-card cardDiv2">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px">我的置顶</span>
            </div>
            <el-table v-loading="loading" :data="favoritefilelist" height="300" style="width: 100%">
              <el-table-column prop="fileId" label="文档ID" align="center"> </el-table-column>
              <el-table-column prop="fileName" label="文档名" align="center"> </el-table-column>
              <el-table-column prop="updateBy" label="上传人" align="center"> </el-table-column>
              <el-table-column prop="fileType" label="文件类型" align="center"> 
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.dms_file_type" :value="scope.row.fileType"/>
                </template>
              </el-table-column>
              <el-table-column prop= "belongteam" label="归属团队" align="center"> </el-table-column>
              <el-table-column prop="publishTime" label="发布时间" align="center"> </el-table-column>
              <el-table-column prop="collectTime" label="置顶时间" align="center"> </el-table-column>
              <el-table-column label="操作" >
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleDownload(scope.row)"
                  >下载</el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="deletefavorite(scope.row)"
                  >取消置顶</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
          <el-card class="box-card cardDiv2">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px">我的自定义文档</span>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getmysearchfilelist"></right-toolbar>
            </div>

            <div slot="header" v-show="showSearch" style="display: flex; align-items: center;">
              <span style="margin-right: 10px;">搜索条件：</span>
              <span>{{ searchquery.fileId ? '（文件ID）' + searchquery.fileId : ''}}</span>
              <span>{{ searchquery.fileName ? '（文件名）' + searchquery.fileName : ''}}</span>
              <span>{{ searchquery.updateBy ? '（上传人）' + searchquery.updateBy : ''}}</span>
              <span>{{ searchquery.publishId ? '（定稿人）' + searchquery.publishId : ''}}</span>
              <span>{{ searchquery.fileType ? getFileTypeLabel(searchquery.fileType) : ''}}</span>
              <span>{{ searchquery.belongteam ? '（归属团队）' + searchquery.belongteam:''}}</span>
              <span>{{ searchquery.publishTime ? '（发布时间）' + searchquery.publishTime:''}}</span>
            </div>
            
            <el-table v-loading="loading" :data="mysearchfilelist" height="300" style="width: 100%">
              <el-table-column prop="fileId" label="文档ID" align="center"> </el-table-column>
              <el-table-column prop="fileName" label="文档名" align="center"> </el-table-column>
              <el-table-column prop="updateBy" label="上传人" align="center"> </el-table-column>
              <el-table-column prop="fileType" label="文件类型" align="center"> 
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.dms_file_type" :value="scope.row.fileType"/>
                </template>
              </el-table-column>
              <el-table-column prop= "belongteam" label="归属团队" align="center"> </el-table-column>
              <el-table-column prop="publishTime" label="发布时间" align="center"> </el-table-column>
              <el-table-column label="操作" >
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleDownload(scope.row)"
                  >下载</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
    </el-row>
  </div>
</template>
 
<script>
import {userhomepagebasicinfo,listlatestfileinfo,getdeptfilenum,getmostpopularfileinfo} from "@/api/system/homepage";
import {listFavorites,delFavorites} from "@/api/system/favorites";
import { listDmsfileupload} from "@/api/system/dmsfileupload";
import { addRecords} from "@/api/system/records";
import {deptTreeSelect} from "@/api/system/dmsfileupload";
import {listSearches} from "@/api/system/searches";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import * as echarts from 'echarts' 
export default {
  name: "Index",
  dicts: ['dms_file_type'],
  components: { Treeselect },
  data() {
    return {
      // 部门树选项
      deptOptions: undefined,
      // 显示搜索条件
      showSearch: false,
      query:{},
      userbasicnum:{},
      latestfilelist: [],
      popularfilelist: [],
      deptfilenum:[],
      favoritefilelist:[],
      mysearchfilelist:[],
      searchquery:[],
      myseachform:{},
    };
  },
  created() {
    this.getDeptTree();
    this.getcurrentuserbasicinfonum();
    this.getlatestfilelist();
    this.getpopularfilelist();
    this.getdeptpublishfilenum();
    this.getfavoritefilelist();
    this.getmysearchfilelist();
  },
  mounted(){
    setTimeout(() => {
      this.renderPieChart()//饼图
    }, 500)
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
        }
      );
    },
    //获取我的自定义条件下的文件
    async getmysearchfilelist(){
      try {
        this.myseachform.userId=this.$store.state.user.id;
        this.loading = true;
        // 第一个异步函数：listSearches
        const searchResponse = await listSearches(this.myseachform); 
        this.searchquery = searchResponse.rows[0];
        
        this.searchquery.queryuserId=this.$store.state.user.id;
        this.searchquery.querykind=1;
        this.searchquery.fileStatus=3;
        this.loading = true;
        const fileListResponse = await listDmsfileupload(this.searchquery)
        this.mysearchfilelist = fileListResponse.rows;
        this.loading = false;
      }catch (error) {
        console.error("An error occurred:", error);
        this.loading = false;
        }
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 根据文件类型获取对应的标签
    getFileTypeLabel(fileType) {
      const type = this.dict.type.dms_file_type.find(item => item.value == fileType);
      return type ? `（文件类型）${type.label}` : '';
    },
    // 表单重置
    reset() {
      this.query = {
        fileId: null,
        userId: null,
        collectTime: null,}
    },
    //获得用户的收藏文件列表
    getfavoritefilelist(){
      this.loading = true;
      this.reset();
      this.query.userId=this.$store.state.user.id;
      listFavorites(this.query).then(response => {
          this.favoritefilelist = response.rows;
          this.loading = false;
        }
      );
    },
    //取消文件收藏
    deletefavorite(row){
      this.reset();
      this.query.userId=this.$store.state.user.id;
      this.query.fileId=row.fileId;
      const self = this;
      this.$modal.confirm('是否确认取消收藏？').then(function(){
        return delFavorites(self.query);
      }).then(() => {
        this.getfavoritefilelist();
        this.$modal.msgSuccess("取消收藏成功");
      }).catch(() => {});
    },
    // 下载文件
    handleDownload(row) {
      // 新增下载记录
      const currentDate = new Date();
      const formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')} ${currentDate.getHours().toString().padStart(2, '0')}:${currentDate.getMinutes().toString().padStart(2, '0')}:${currentDate.getSeconds().toString().padStart(2, '0')}`;
      // 查找belongteam对应的deptId
      const deptId = this.getIdByLabel(this.deptOptions, row.belongteam)
      this.downloadrecord_form = {
        fileId: row.fileId,
        fileName: row.fileName,
        deptId:deptId,
        belongteam: row.belongteam,
        downloadUserid: this.$store.state.user.id,
        downloadUser: this.$store.state.user.name,
        downloadTime: formattedDate 
      };
      addRecords(this.downloadrecord_form);

      var name = row.fileName;
      var url = row.filePath;
      var suffix = url.substring(url.lastIndexOf("."), url.length);
      const a = document.createElement('a')
      a.setAttribute('download', name + suffix)
      a.setAttribute('target', '_blank')
      a.setAttribute('href', url)
      a.click()
    },
    // 根据belongteam文本查deptId
    getIdByLabel(deptOptions, targetLabel){
      function findId(options, label) {
        for (const option of options) {
          if (option.label === label) {
            return option.id;
          }
          if (option.children) {
            const foundId = findId(option.children, label);
            if (foundId) {
              return foundId;
            }
          }
        }
        return null;
      }
      return findId(deptOptions, targetLabel);
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    renderPieChart() {
      const chartData = this.deptfilenum.map(item => ({
        name: item.teamName,
        value: item.fileNum
      }))
      const myChart = echarts.init(document.getElementById('pie-chart'))
      const option = {
        title: {
          text: '',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          top: 10,
          left: 'center',
        },
        series: [
          {
            name: '文件数量',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: chartData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }

      // 使用配置项绘制饼状图
      myChart.setOption(option)
    }
  }
};
</script>
 
<style scoped lang="scss">
  ::v-deep .el-card__body {
    padding: 15px 15px 20px 20px;
    display: flex;
    justify-content: center;
  }
  //第一行五个小框大小
  // height: 120px;: 设置盒子的高度为 120 像素。
  // padding: 35px;: 设置盒子的内边距为 35 像素，意味着盒子内容与盒子边缘之间的间距为 35 像素。
  // margin: 15px;: 设置盒子的外边距为 15 像素，意味着盒子与其周围元素之间的间距为 15 像素。
  .box-div{
    width: 90%;
    height: 120px;
    padding: 35px;
    // margin: 25px 25px 15px 15px; /* 上右下左 */
    margin: 38px;
    // margin-bottom: 25px; /* 增加盒子之间的垂直间距 */
    // margin-top: 25px;
    margin-left: 38px;
    // margin-right: 25px;
  }
  //第一行背景框宽度，与第二行框的间距
  .cardDiv1{
    width: 100%;
    margin-bottom: 10px;
  }
  //第三行背景框宽度，与第二行框的间距
  .cardDiv2{
    width: 100%;
    margin-bottom: 20px;
  }
  // 页面整体边距
  .app-container {
    padding: 20px;
    background: #eeeeee50;
    //background: #eb040450;
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
    -webkit-box-orient: horizontal;
  }
  .webO{
    display: -webkit-box;
    -webkit-box-pack: start;
    -webkit-box-align: center;
    font-size: 30px;
    font-weight: 700;
    padding-right: 30px;
    padding-top: 0px;
    color: #fff;
  }
  .webT{
    display: -webkit-box;
    -webkit-box-pack: end;
    -webkit-box-align:center;
    font-size: 45px;
    font-weight: 700;
    padding-right: 1px;
    padding-top: 0px;
    color: #fff;
  }
  .card1{
    background: linear-gradient(90deg, rgba(113, 162, 241, 1) 100%, rgba(0, 130, 200, 1) 100%, rgba(0, 130, 200, 1) 100%, rgba(102, 125, 182, 1) 100%);
    border: none;
    border-radius: 10px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
  .card2{
    background: linear-gradient(90deg, rgba(99, 170, 160, 1) 100%, rgba(244, 174, 149, 1) 0%, rgba(226, 113, 140, 1) 100%, rgba(226, 113, 140, 1) 100%);
    border: none;
    border-radius: 10px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
  .card3{
    // background: linear-gradient(180deg, rgba(255, 153, 51, 1) 0%, rgba(255, 153, 51, 1) 0%, rgba(239, 203, 45, 1) 100%, rgba(239, 203, 45, 1) 100%);
    background: linear-gradient(90deg, rgba(99, 112, 170, 1) 100%, rgba(170, 137, 254, 1) 0%, rgba(204, 153, 204, 1) 100%, rgba(204, 153, 204, 1) 100%);
    border: none;
    border-radius: 10px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
  .card4{
    background: linear-gradient(90deg, rgba(99, 157, 170, 1) 100%, rgba(45, 169, 250, 1) 0%, rgba(102, 204, 255, 1) 100%, rgba(102, 204, 255, 1) 100%);
    border: none;
    border-radius: 10px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
  }
}
</style>
 