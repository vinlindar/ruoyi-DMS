<template>
  <div class="app-container home">
    <!-- 图片走马灯 -->
    <el-row  type="flex" justify="space-around">
      <el-card class="box-card cardDiv2" style="margin-right: 5px">
        <!-- 图片走马灯 -->
        <el-carousel class="image_carousel" height="400px">
          <el-carousel-item v-for="(image, index) in images"  :key="index" class="carousel-item">
            <img :src=getImageUrl(image.path) width="100%" >
            <div class="image-title">
              <div class="text-container">
                <p show-overflow-tooltip>{{ image.title }}</p>
                <p show-overflow-tooltip>{{ image.description }}</p>
              </div>
              <div class="link-container">
                <p>{{ parseTime(image.creatTime, '{y}-{m}-{d}') }}</p>
                <router-link to="/news/list" class="more-link">更多&gt;&gt;</router-link>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </el-card>
      <el-card class="box-card cardDiv2" style="margin-left: 5px">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px"><b>近期发布</b></span>
            </div>
            <el-table :data="latestfilelist" height=350 style="width: 100%">
              <el-table-column prop="fileName" label="文件名" align="center" width="300" show-overflow-tooltip> 
                <template slot-scope="scope">
                   <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
                    <span>{{  scope.row.fileName }}</span>
                  </router-link>
                </template>
              </el-table-column>
              <el-table-column prop= "belongteam" label="归属团队" align="center"> </el-table-column>
              <el-table-column prop="publishTime" label="发布时间" align="center"> 
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
    </el-row>

    <el-row type="flex" justify="space-around" class="row-bg" >
        <el-card class="box-card cardDiv2" style="margin-right: 5px">
          <div slot="header" class="clearfix">
            <span><b>下载排行</b></span>
          </div>
          <el-table :data="popularfilelist" height="300" >
            <el-table-column prop="fileName" label="文件名" align="center" width="300" show-overflow-tooltip> 
                <template slot-scope="scope">
                   <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
                    <span>{{  scope.row.fileName }}</span>
                  </router-link>
                </template>
            </el-table-column>
            <el-table-column prop= "belongteam" label="归属团队" align="center"> </el-table-column>
            <el-table-column prop= "downloadCount" label="下载次数" align="center"> </el-table-column>
          </el-table>
        </el-card>
        <el-card class="box-card cardDiv2" style="margin-left: 5px">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px"><b>我的收藏</b></span>
            </div>
            <el-table v-loading="loading" :data="favoritefilelist" height="300" style="width: 100%">
              <el-table-column prop="fileName" label="文件名" align="center" width="300" show-overflow-tooltip> 
                <template slot-scope="scope">
                   <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
                    <span>{{  scope.row.fileName }}</span>
                  </router-link>
                </template>
              </el-table-column>
              <el-table-column prop="collectTime" label="收藏时间" align="center"> 
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.collectTime, '{y}-{m}-{d}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="deletefavorite(scope.row)"
                  >取消收藏</el-button>
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
      this.$modal.confirm('是否确认取消收藏？').then(function(){
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
    margin-left: 20px;
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
  width: 100%;
  margin-right:10px;
}
.image-title {
  display: flex;
  align-items: center;
  position: absolute;
  bottom: 0px;
  width: 100%;
  padding: 5px 10px;
  color: white;
  background-color: rgba(0, 0, 0, 0.7);
  font-size: 16px;
  white-space: nowrap;
}
.text-container{
  flex:0 0 80%;
}
.text-container p{
  margin-top: 0;
  margin-bottom: 0;
}
.link-container p{
  margin-top: 0;
  margin-bottom: 0;
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
  flex:0 0 20%;
  text-align: right;
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
 