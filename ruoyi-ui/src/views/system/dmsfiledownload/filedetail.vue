<template>
  <div class="file-detail-container">
    <div class="file-detail-left">
      <h2 class="file-name">文档名: {{ filedetail.fileName }}</h2>
        <div class="file-info">
          <table class="info-table">
            <tr><td class="label">文档ID:</td><td class="value">{{ this.filedetail.fileId }}</td></tr>
            <tr><td class="label">关键词:</td><td class="value">{{ this.filedetail.keywords }}</td></tr>
            <tr><td class="label">文档状态:</td><td  class="value">
              <dict-tag :options="getFileStatusText()" :value="this.filedetail.fileStatus" />
            </td></tr>
            <tr><td class="label">提交人:</td><td  class="value">{{ this.filedetail.updateBy }}</td></tr>
            <tr><td class="label">评阅人:</td><td  class="value">{{ this.filedetail.reviewer }}</td></tr>
            <tr><td class="label">定稿人:</td><td  class="value">{{ getPublishNameById(this.filedetail.publishId) }}</td></tr>
            <tr><td class="label">文档分类:</td><td  class="value">{{ getFileTypeLabel(this.filedetail.fileType) }}</td></tr>
            <tr><td class="label">归属团队:</td><td  class="value">{{ this.filedetail.belongteam }}</td></tr>
            <tr><td class="label">文件大小:</td><td  class="value">{{ this.filedetail.fileSize }}</td></tr>
            <tr><td class="label">提交时间:</td><td  class="value">{{ this.filedetail.updateTime }}</td></tr>
            <tr><td class="label">发布时间:</td><td  class="value">{{ this.filedetail.publishTime }}</td></tr>
            <tr><td class="label">下载次数:</td><td  class="value">{{ this.filedetail.downloadNum}}</td></tr>
            <tr><td class="label">文档描述:</td><td  class="value">{{ this.filedetail.description }}</td></tr>
          </table>
        </div>
        <div class="download-button">
          <el-button type="primary" @click="handleDownload">点击下载</el-button>
        </div>
    </div>
    <div class="file-detail-right">
      <div class="review-comments">
        <h3>评阅记录</h3>
        <ul>
          <el-card v-for="(review, index) in ReviewList" :key="index" style="margin-bottom: 10px;" :class="{ 'current-review': review.isCurrent === 1 }" 
          v-if="review.isCurrent === 1 || (review.comment)">
            <div class="review-info">
              <p class="reviewer-text">评阅人:{{ getReviewNameById(review.reviewerId) }}</p>
              <p class="reviewer-text">评阅状态: <dict-tag :options="getReviewStatusText()" :value="review.status" /></p>
              <p>评阅时间: {{ review.reviewTime }}</p>
            </div>
            <p>评阅意见: {{ review.comment }}</p>
          </el-card>
        </ul>
      </div>
      <div class="publish-comments">
        <h3>定稿记录</h3>
        <ul>
          <el-card v-for="(publish, index) in PublishList" :key="index" style="margin-bottom: 10px;" :class="{ 'current-review': publish.isCurrent === 1 }" 
          v-if="publish.isCurrent === 1 || (publish.comment)">
            <div class="review-info">
              <p class="reviewer-text">定稿人:{{ getPublishNameById(publish.publishId) }}</p>
              <p class="reviewer-text">定稿结果: <dict-tag :options="getPublishResultText()" :value="publish.isPassed" /></p>
              <p>定稿时间: {{ publish.publishTime }}</p>
            </div>
            <p>定稿意见: {{ publish.comment }}</p>
          </el-card>
        </ul>
      </div>
      <div >
        <h3>发布范围</h3>
        <ul>
          <el-card v-for="(item, index) in Permissionlist" :key="index" class="review-card">
            <div class="review-info"> 
              <p class="reviewer-text"> {{ item.deptId }},{{ getLabelById(deptOptions, item.deptId) }}</p>
            </div> 
          </el-card>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { listDmsfileupload} from "@/api/system/dmsfileupload";
import { listPublish} from "@/api/system/publish";
import {listReview}from "@/api/system/review";
import { getPermissions } from "@/api/system/permissions";
import { listUserbypostId } from "@/api/system/user";
import { addRecords,getDownloadNumbyfileId} from "@/api/system/records";
import {deptTreeSelect,getDmsfileupload} from "@/api/system/dmsfileupload";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dmsfiledownload",
  dicts: ['dms_file_type', 'dms_file_status','dms_review_status','dms_publish_result'],
  components: { Treeselect },
  data() {
    return {
      //是否为管理员
      isAdmin: this.$store.state.user.name === 'admin',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 定稿人列表
      PublisherList: undefined,
      // 评审人列表
      ReviewerList: undefined,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文件信息表格数据
      dmsfileuploadList: [],
      //文件收藏表格
      favorite:{},
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 部门名称
      deptName: undefined,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileId: null,
        fileName: null,
        filePath: null,
        author: null,
        reviewer: null,
        fileType: null,
        fileSize: null,
        // 固定为已发布文档
        fileStatus: null,
        belongteam: null,
        description: null,
        updateBy: null,
        updateTime: null,
        publishId: null,
        //区分文档上传的查询
        querykind: 1,
        // 用户权限控制需要
        queryuserId:this.$store.state.user.id,
        queryuserDept:null,
      },
      ReviewList:{},
      PublishList:{},
      Permissionlist:{},
      filedetail:{},
      // 查询评阅
      reviewquery: {
        fileId:null
      },
      publishquery: {
        fileId:null
      },
      // 下载记录表单
      downloadrecord_form:{},
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
      },
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getDeptTree();
    this.getPublisherList();
    this.getReviewerList(); //获得评阅人名单
    const fileId = this.$route.params && this.$route.params.fileId;
    this.getfiledetail(fileId);
  },
  methods: {
    /** 查询文件信息列表 */
  getfiledetail(fileId) {
    this.loading = true; // 开始加载状态
    const fileDetailPromise = getDmsfileupload(fileId);
    const reviewPromise = listReview({ fileId: fileId });
    const publishPromise = listPublish({ fileId: fileId });
    const permissionsPromise = getPermissions(fileId);
    const downloadNumPromise = getDownloadNumbyfileId(fileId);
    Promise.all([fileDetailPromise, reviewPromise, publishPromise, permissionsPromise, downloadNumPromise])
      .then(([fileDetailResponse, reviewResponse, publishResponse, permissionsResponse, downloadNumResponse]) => {
        this.filedetail = fileDetailResponse.data;
        // 处理评审意见
        this.ReviewList = reviewResponse.rows;
        this.reviewtotal = reviewResponse.total;
        // 处理定稿意见
        this.PublishList = publishResponse.rows;
        this.Publishtotal = publishResponse.total;
        if (publishResponse.rows.length > 0) {
          this.filedetail.publishTime = publishResponse.rows[0].publishTime;
        }
        // 处理文档发布范围
        this.Permissionlist = permissionsResponse.data;
        // 处理下载次数
        this.filedetail.downloadNum = downloadNumResponse.data;
        this.loading = false; // 结束加载状态
      })
      .catch(error => {
        this.loading = false; // 结束加载状态
        console.error("Error fetching file details:", error);
      });
    },
        /**  查询评阅人下拉列表 */
    getReviewerList() {
      this.loading = true;
      const postID = 2;
      listUserbypostId(postID).then(response => {
          // 提取用户ID和用户名信息
          this.ReviewerList = response.data;
          this.reviewertotal = response.length;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选条件的部门选择事件
    handleSelect(val) {
      // 通过 Treeselect 实例获取选中的label值
        this.queryParams.belongteam = val.label
    },
    /**  查询定稿人下拉列表 */
    getPublisherList() {
      this.loading = true;
      const postID = 1;
      listUserbypostId(postID).then(response => {
          // 提取用户ID和用户名信息
          this.PublisherList = response.data;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        fileId: null,
        fileName: null,
        filePath: null,
        author: null,
        reviewer: null,
        fileType: null,
        fileSize: null,
        fileStatus: null,
        belongteam: null,
        publishId:null,
        description: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.belongteam = data.label;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.fileId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleDownload() {
      // 判断文件状态，若为已发布，则校验用户下载权限，新增下载记录，反之则直接下载
      if(this.filedetail.fileStatus == 3){
        // 判断用户是否有下载权限
        this.queryParams.fileId = this.$route.params && this.$route.params.fileId;
        this.loading = true;
        listDmsfileupload(this.queryParams).then(response => {
          this.dmsfileuploadList = response.rows;
          this.loading = false;
          // 如果返回结果为 null，则提示用户无下载权限
          if (!response.rows || response.rows.length === 0) {
            this.$message.error('无下载权限');
            return;
          }
          const currentDate = new Date();
          const formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')} ${currentDate.getHours().toString().padStart(2, '0')}:${currentDate.getMinutes().toString().padStart(2, '0')}:${currentDate.getSeconds().toString().padStart(2, '0')}`;
          // 查找belongteam对应的deptId
          const deptId = this.getIdByLabel(this.deptOptions, this.filedetail.belongteam)
          this.downloadrecord_form = {
            fileId: this.filedetail.fileId,
            fileName: this.filedetail.fileName,
            deptId:deptId,
            belongteam: this.filedetail.belongteam,
            downloadUserid: this.$store.state.user.id,
            downloadUser: this.$store.state.user.name,
            downloadTime: formattedDate
          };
          addRecords(this.downloadrecord_form);
          var name = this.filedetail.fileName;
          var url = this.filedetail.filePath;
          var suffix = url.substring(url.lastIndexOf("."), url.length);
          const a = document.createElement('a')
          a.setAttribute('download', name + suffix)
          a.setAttribute('target', '_blank')
          a.setAttribute('href', url)
          a.click()
      }).catch(() => {
        this.loading = false;
        this.$message.error('下载文件出错，请稍后重试');
      });
    }else{
          // 文件状态不是已发布，直接下载
          var name = this.filedetail.fileName;
          var url = this.filedetail.filePath;
          var suffix = url.substring(url.lastIndexOf("."), url.length);
          const a = document.createElement('a');
          a.setAttribute('download', name + suffix);
          a.setAttribute('target', '_blank');
          a.setAttribute('href', url);
          a.click();
        }
    },
    getFileTypeLabel(fileType){
      const dictItem = this.dict.type.dms_file_type.find(item => item.value ===  String(fileType));
      return dictItem ? dictItem.label : fileType;
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
    getLabelById(deptOptions, targetId){
      function findLabel(options, id) {
        for (const option of options) {
          if (option.id === id) {
            return option.label;
          }
          if (option.children) {
            const foundLabel = findLabel(option.children, id);
            if (foundLabel) {
              return foundLabel;
            }
          }
        }
        return null;
      }
      return findLabel(deptOptions, targetId);
    },
    getReviewStatusText() {
      return this.dict.type.dms_review_status;
    },
    getFileStatusText(){
      return this.dict.type.dms_file_status;
    },
    /** 根据定稿人ID查姓名 */
    getPublishNameById(userId) {
      if (userId === undefined || userId === null) {
        return "Unknown User";
    }
      const user = this.PublisherList.find(item => item.userId === userId);
      return user ? user.userName : userId.toString();
    },
    /** 根据评阅人ID查姓名 */
    getReviewNameById(userId) {
      if (userId === undefined || userId === null) {
        return "Unknown User";
    }
      const user = this.ReviewerList.find(user => user.userId === userId);
      return user ? user.userName : userId.toString();
    },
    getPublishResultText() {
      return this.dict.type.dms_publish_result;
    },
  }
};
</script>
<style scoped>
.file-detail-container {
  display: flex;
  justify-content: space-between;
}
.file-detail-left {
  width: 40%;
  padding: 20px;
  border-right: 1px solid #ddd;
}
.file-name {
    word-wrap: break-word; /* 在单词内部换行 */
    white-space: normal; /* 在空白处换行 */
}
.file-detail-right {
  width: 60%;
  height: calc(100vh - 80px); /* Adjust based on your header/footer height */
  overflow-y: auto;
  padding: 20px;
}
.info-table{
  line-height: 1.6
}
.download-button {
  text-align: right;
  margin-top: 20px;
}
.review-info {
  display: flex;
  justify-content: space-between; /* 子元素水平间距平均分布 */
  align-items: flex-start; /* 子元素垂直居中 */
  font-size: 16px;
}
.file-name {
  margin-bottom: 20px;
}
.file-info {
  padding-left: 20px;
}
.reviewer-text {
  display: flex;
  align-items: flex-start;
}
.label {
  font-weight: bold; /* 设置标签文本加粗 */
  white-space: nowrap; /* 防止标签文本换行 */
  vertical-align: top;
}
.value {
    max-width: 80%; /* 设置合适的最大宽度 */
    word-wrap: break-word; /* 或者 word-break: break-all; */
}
.current-review {
  background-color: #f0f8ff; /* 设置淡蓝色背景 */
}
</style>
