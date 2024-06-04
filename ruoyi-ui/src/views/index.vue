<template>
  <div class="app-container home">
    <!--我的已办/代办 -->
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
    <!-- 图片走马灯 -->
    <el-row  type="flex" justify="space-around" class="row-bg">
      <el-card class="box-card cardDiv3">
        <!-- 图片走马灯 -->
        <el-carousel class="image_carousel" height="400px">
          <el-carousel-item v-for="(image, index) in images"  :key="index" class="carousel-item">
            <img :src=getImageUrl(image.path) width="100%" >
            <div class="image-title">{{ image.title }}</div>
          </el-carousel-item>
        </el-carousel>
        <div class="news_detail">
          <div class="news_header">
            <h2> 
              <i class="icon el-icon-camera"></i>
              <strong>头条新闻</strong>
            </h2>
            <router-link to="/news/list" class="more-link">更多&gt;&gt;</router-link>
          </div>
          <el-table v-loading="loading" :data="images" :show-header="false">
            <el-table-column width="20">
                <template slot-scope="scope">
                  <div class="circle"></div>
                </template>
            </el-table-column>
            <el-table-column align="left" prop="title"></el-table-column>
            <el-table-column align="right" prop="creatTime" width="100px" >
              <template slot-scope="scope">
                <span :style="{ fontSize: '14px' }">{{ scope.row.creatTime }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </el-row>

    <el-row type="flex" justify="space-around" class="row-bg" >
      <el-col :span="16">
          <el-card class="box-card cardDiv2">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px"><b>近期文档</b></span>
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
              <span style="margin-right: 30px"><b>我的置顶</b></span>
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
              <span style="margin-right: 30px"><b>我的自定义搜索</b></span>
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
import {userhomepagebasicinfo,listlatestfileinfo,getdeptfilenum,getmostpopularfileinfo,getlistimages} from "@/api/system/homepage";
import { listImages } from "@/api/system/images";
import {listFavorites,delFavorites} from "@/api/system/favorites";
import { listDmsfileupload} from "@/api/system/dmsfileupload";
import { addRecords} from "@/api/system/records";
import {deptTreeSelect} from "@/api/system/dmsfileupload";
import {listSearches} from "@/api/system/searches";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
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
      images:[],
      newsquery:{
        isShow:1,
      }
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
    this.fetchImages();
  },
  mounted(){
  },
  methods: {
    // 获得新闻照片
    fetchImages() {
      this.loading = true;
      listImages(this.newsquery).then(response => {
          this.images = response.rows;
          this.loading = false;
        })
        .catch(error => {
          console.error("There was an error fetching the images!", error);
          this.loading = false;
        });
    },
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
      this.$modal.confirm('是否确认取消置顶？').then(function(){
        return delFavorites(self.query);
      }).then(() => {
        this.getfavoritefilelist();
        this.$modal.msgSuccess("取消成功");
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
        downloadTime: formattedDate,
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
    getImageUrl(path) {
      // 返回完整的图片URL，确保路径正确
      return process.env.VUE_APP_BASE_API + path;
    },
    onPageChange(){

    },
  }
}
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
    margin: 20px;
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
  .cardDiv3{
    width: 1847px;
    margin-bottom: 10px;
  }
  //第三行背景框宽度，与第二行框的间距
  .cardDiv2{
    width: 100%;
    margin-bottom: 10px;
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
  .clearfix{
    font-size:18px;
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
  .box-div {
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
    height: 100%; /* 确保父容器的高度 */
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
    height: 50px;
  }
  .card2{
    background: linear-gradient(90deg, rgba(99, 170, 160, 1) 100%, rgba(244, 174, 149, 1) 0%, rgba(226, 113, 140, 1) 100%, rgba(226, 113, 140, 1) 100%);
    border: none;
    border-radius: 10px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
    height: 50px;
  }
  .card3{
    // background: linear-gradient(180deg, rgba(255, 153, 51, 1) 0%, rgba(255, 153, 51, 1) 0%, rgba(239, 203, 45, 1) 100%, rgba(239, 203, 45, 1) 100%);
    background: linear-gradient(90deg, rgba(99, 112, 170, 1) 100%, rgba(170, 137, 254, 1) 0%, rgba(204, 153, 204, 1) 100%, rgba(204, 153, 204, 1) 100%);
    border: none;
    border-radius: 10px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
    height: 50px;
  }
  .card4{
    background: linear-gradient(90deg, rgba(99, 157, 170, 1) 100%, rgba(45, 169, 250, 1) 0%, rgba(102, 204, 255, 1) 100%, rgba(102, 204, 255, 1) 100%);
    border: none;
    border-radius: 10px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.349019607843137);
    font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
    height: 50px;
  }
}
.image_carousel{
  width: 50%;
  margin-right:10px;
}
.image-title {
  position: absolute;
  bottom: 0px;
  width: 100%;
  padding: 5px 10px;
  color: white;
  background-color: rgba(0, 0, 0, 0.7);
  font-size: 16px;
  white-space: nowrap;
}
.news_detail{
  width: 50%;
  margin-left: 10px;
}
.news_header {
  display: flex;
  align-items: center; /* 垂直居中 */
  height: 50px; /* 固定高度 */
  background-color: rgba(0, 0, 128);
}
.news_header h2{
  margin-top: 0;
  margin-bottom: 0;
  color: white;
  align-items: center;
  margin-right: auto
}
.more-link{
  font-size: 14px;
  color: white; /* 蓝色链接的颜色 */
  text-decoration: none; /* 去掉下划线 */
  margin-left: 10px;
  margin-right: 10px;
}
.table_date{
  color: white;
  font-size: 12px;
}
.circle {
  width: 10px;
  height: 10px;
  border: 2px solid red;
  border-radius: 50%;
  margin: 0 auto; /* 可以根据需要调整位置 */
}
.icon {
    margin-left: 10px;
    margin-right: 10px; /* 图标与文本间距 */
  }
</style>
 