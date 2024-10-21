<template>
  <div class="app-container home">
    <!-- 通知栏 -->
    <div class="announcement" v-if="noticeList.length > 0">
      <el-carousel height="40px" direction="vertical">
        <el-carousel-item v-for="(notice, index) in noticeList" :key="index">
          <div class="announcement">
            <strong>{{ notice.noticeTitle }}</strong>:{{ notice.noticeContent }}
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>
    <!-- 图片走马灯 -->
    <el-row  type="flex" justify="space-around" class="row-bg1" >
      <el-card class="box-card cardDiv2" style="margin-right: 5px">
        <!-- 图片走马灯 -->
        <el-carousel class="image_carousel" height="400px" >
          <el-carousel-item v-for="(image, index) in images"  :key="index">
            <div class="image-container">
              <img :src=getImageUrl(image.path) class="carousel-image">
            </div>
            <div class="image-title">
              <div class="text-container">
                <p class="single-line">{{ image.title }}</p>
                <p class="single-line">{{ image.description }}</p>
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
              <el-table-column prop="fileName" label="文件名" header-align="center" align="left" width="400" show-overflow-tooltip> 
                <template slot-scope="scope">
                   <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
                    <span>{{  scope.row.fileName }}</span>
                  </router-link>
                </template>
              </el-table-column>
              <el-table-column prop= "updateBy" label="提交人" align="center"  show-overflow-tooltip> </el-table-column>
              <el-table-column prop="publishTime" label="发布时间" align="center"  show-overflow-tooltip> 
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
            <el-table-column prop="fileName" label="文件名" header-align="center" align="left" width="400" show-overflow-tooltip> 
                <template slot-scope="scope">
                   <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
                    <span>{{  scope.row.fileName }}</span>
                  </router-link>
                </template>
            </el-table-column>
            <el-table-column prop= "updateBy" label="提交人" align="center" show-overflow-tooltip> </el-table-column>
            <el-table-column prop= "downloadCount" label="下载次数" align="center"> </el-table-column>
          </el-table>
        </el-card>
        <el-card class="box-card cardDiv2" style="margin-left: 5px">
            <div slot="header" class="clearfix">
              <span style="margin-right: 30px"><b>我的收藏</b></span>
            </div>
            <el-table v-loading="loading" :data="favoritefilelist" height="300" style="width: 100%">
              <el-table-column prop="fileName" label="文件名" header-align="center" align="left"  width="400" show-overflow-tooltip> 
                <template slot-scope="scope">
                   <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
                    <span>{{  scope.row.fileName }}</span>
                  </router-link>
                </template>
              </el-table-column>
              <el-table-column prop="collectTime" label="收藏时间" align="center" show-overflow-tooltip> 
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.collectTime, '{y}-{m}-{d}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
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
import { listNotice} from "@/api/system/notice";
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
      noticeList:[],
      searchquery:[],
      myseachform:{},
      NoticeParams:{
        pageNum: 1,
        pageSize: 10,
        noticeTitle: undefined,
        createBy: undefined,
        status: 0
      },
      images:[],
      newsquery:{
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
    this.getnoticelist();
    this.fetchImages();
  },
  methods: {
    // 提示代办事项
    notification(userbasicnum){
      const h = this.$createElement;
      let delay = 0;
      if (userbasicnum.waitModifyNum > 0) {
        setTimeout(()=>{
        this.$notify({
          title: '代办事项',
          message: h('a', {attrs: { href: 'system/dmsfileupload' }, style: 'color: teal;font-size: 16px'},
           ['您有',h('span',{style:'color:red; font-size: 18px'},userbasicnum.waitModifyNum),'个文档待修改']),
          position: 'top-right',
          duration: 0, 
        });
      },delay);
      delay+=500;
      }
      if (userbasicnum.waitReviewNum > 0) {
        setTimeout(()=>{
        this.$notify({
          title: '待办事项',
          message: h('a', {attrs: { href: 'system/review' }, style: 'color: teal;font-size: 16px' }, 
          ['您有',h('span',{style:'color:red; font-size: 18px'},userbasicnum.waitReviewNum),'个文档待评阅']),
          position: 'top-right',
          duration: 0, 
        });
        },delay);
        delay+=500;
      }
      if (userbasicnum.waitPublishNum > 0) {
        setTimeout(()=>{
        this.$notify({
          title: '代办事项',
          message: h('a', {attrs: { href: 'system/publish' }, style: 'color: teal;font-size: 16px' }, 
          ['您有',h('span',{style:'color:red; font-size: 18px'},userbasicnum.waitPublishNum),'个文档待定稿']),
          position: 'top-right',
          duration: 0, 
        });
        },delay);
        delay+=500;
      }
    },
    // 获得新闻照片
    fetchImages() {
      this.loading = true;
      listImages(this.newsquery).then(response => {
          // 先过滤出可展示的照片
          const filteredImages = response.rows;
          // 按 creatTime 升序排序
          filteredImages.sort((a, b) => new Date(b.creatTime) - new Date(a.creatTime));
          // 取前 5 个记录
          this.images = filteredImages.slice(0, 5);
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
          this.notification(this.userbasicnum);
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
    // 获得通知信息
    getnoticelist(){
      this.loading = true;
      listNotice(this.NoticeParams).then(response => {
        this.noticeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
  //第三行背景框宽度，与第二行框的间距
  .cardDiv2{
    width: 100%;
    margin-bottom: 10px;
  }
  // 页面整体边距
  .app-container {
    padding: 20px;
    padding-top: 0px;
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
}
.row-bg1{
  padding-top:5px
}
.image_carousel{
  width: 100%;
  margin-right:10px;
  position: relative;
}
.image-container{
  position: relative;
  width: 100%;
}
.carousel-image {
  width: 100%;
  height: 350px;
  object-fit: cover; /* 确保图片按照比例填充容器 */
}
.image-title {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 0px 10px;
  color: black;
  background-color: rgba(0, 0, 0, 0.2);
  font-size: 16px;
  white-space: nowrap;
}
.text-container{
  flex-grow: 1;
  margin-right: 10px;
  overflow: hidden;
}
.text-container p{
  margin-top: 0;
  margin-bottom: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.link-container{
  flex-shrink: 0;
}
.link-container p{
  margin-top: 0;
  margin-bottom: 0;
}
.single-line{
  width:100%;
}
.more-link{
  font-size: 14px;
  color: black; /* 蓝色链接的颜色 */
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
.announcement{
  background-color: #ff9800; 
  color: white; 
  text-align: center; 
  font-size: 18px;
  padding: 5px; 
  top: 0; 
  width: 100%; 
  margin-top: 5px;
}
</style>
 