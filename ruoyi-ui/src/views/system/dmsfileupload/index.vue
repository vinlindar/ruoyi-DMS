<template>
  <div class="app-container">
    <!-- 顶部搜索框-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件ID" prop="fileId">
        <el-input
          v-model="queryParams.fileId"
          placeholder="请输入文件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件类型" prop="fileType">
        <el-select v-model="queryParams.fileType" placeholder="请选择文件类型" clearable>
          <el-option
            v-for="dict in dict.type.dms_file_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="文件状态" prop="fileStatus">
        <el-select v-model="queryParams.fileStatus" placeholder="请选择文件状态" clearable>
          <el-option
            v-for="dict in dict.type.dms_file_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="归属团队" prop="belongteam">
        <treeselect 
          v-model="form.deptId" 
          :options="deptOptions" 
          :show-count="true" 
          @select="handleSelect2"
          placeholder="请选择归属团队" />
      </el-form-item>
      <el-form-item label="创建者" prop="updateBy":clearable="isAdmin">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="仅管理员可操作"
          clearable
          @keyup.enter.native="handleQuery"
          :disabled="!isAdmin"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 文档操作栏位-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:dmsfileupload:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:dmsfileupload:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="single"
          @click="handleDelete"
          v-hasPermi="['system:dmsfileupload:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-search"
          size="mini"
          :disabled="single"
          @click="handlereviewlist"
        >查看评阅意见</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-search"
          size="mini"
          :disabled="single"
          @click="handlePublishlist"
        >查看定稿意见</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:dmsfileupload:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 文档信息展示-->
    <el-table v-loading="loading" :data="dmsfileuploadList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文件ID" align="center" prop="fileId" />
      <el-table-column label="文件名" align="center" prop="fileName" />
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="评阅人" align="center" prop="reviewer" />
      <el-table-column label="定稿人" align="center">
        <template slot-scope="scope">
          {{ getPublishNameById(scope.row.publishId) }}
        </template>
      </el-table-column>
      <el-table-column label="文件类型" align="center" prop="fileType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dms_file_type" :value="scope.row.fileType"/>
        </template>
      </el-table-column>
      <el-table-column label="文件大小" align="center" prop="fileSize" />
      <el-table-column label="文件状态" align="center" prop="fileStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dms_file_status" :value="scope.row.fileStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="归属团队" align="center" prop="belongteam" />
      <el-table-column label="文件描述" align="center" prop="description" />
      <el-table-column label="创建者" align="center" prop="updateBy" />
      <el-table-column label="创建时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dmsfileupload:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dmsfileupload:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页功能代码-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 新增或修改文件信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件名" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名" />
        </el-form-item>
        <el-form-item label="文件上传">
          <el-upload
              ref="upload"
              :limit="1"
              :action="upload.url"
              :headers="upload.headers"
              :file-list="upload.fileList"
              :on-progress="handleFileUploadProgress"
              :on-success="handleFileSuccess"
              :auto-upload="false"
              :before-upload="beforeUpload">
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <el-button style="margin-left: 10px;" size="small" type="success" :loading="upload.isUploading" @click="submitUpload">上传到服务器</el-button>
              <div slot="tip" class="el-upload__tip">只能上传单文件，且不超过50M</div>
            </el-upload>
          </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="评阅人" prop="reviewer">
          <el-select v-model="form.reviewerIds" placeholder="请选择评阅人" multiple>
            <el-option 
              v-for="user in ReviewerList" 
              :key="user.userId" 
              :label="user.userName" 
              :value="user.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="定稿人" prop="publishId">
          <el-select v-model="form.publishId" placeholder="请选择定稿人" :multiple="false">
            <el-option 
              v-for="user in PublisherList" 
              :key="user.userId" 
              :label="user.userName" 
              :value="user.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="文件类型" prop="fileType">
          <el-select v-model="form.fileType" placeholder="请选择文件类型">
            <el-option
              v-for="dict in dict.type.dms_file_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="归属团队" prop="deptId">
          <treeselect 
          v-model="form.deptId" 
          :options="deptOptions" 
          :show-count="true" 
          placeholder="请选择归属团队" />
        </el-form-item>
        <el-form-item label="文件描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容，最多500字，包括建议发布范围" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 评阅信息展示-->
    <el-dialog title="评阅意见" :visible.sync="openreview" width="500px" append-to-body>
      <el-card v-for="(review, index) in ReviewList" :key="index" class="review-card">
        <div class="review-info">
          <p class="reviewer-text">评阅人:</p>
          <p slot="header" class="reviewer">{{ review.reviewerName }}</p>
          <p>评阅结果: </p>
          <p><dict-tag :options="getReviewResultText()" :value="review.isPassed" /></p>
        </div>
        <p>评阅信息: {{ review.comment }}</p>
        <el-divider></el-divider>
      </el-card>
    </el-dialog>

    <!-- 定稿信息展示-->
    <el-dialog title="定稿意见" :visible.sync="openpublish" width="500px" append-to-body>
      <el-card class="review-card">  
        <div class="review-info">
          <p class="reviewer-text">定稿人:</p>
          <p>{{ getPublishNameById(this.PublishList.publishId) }}</p>
          <p>定稿结果: </p>
          <p><dict-tag :options="getPublishResultText()" :value="this.PublishList.isPassed" /></p>
        </div>
          <p>评阅信息: {{ this.PublishList.comment }}</p>
        <el-divider></el-divider>
      </el-card>
    </el-dialog>
    
  </div>
</template>

<script>
import { listDmsfileupload, getDmsfileupload, delDmsfileupload, addDmsfileupload, updateDmsfileupload,deptTreeSelect  } from "@/api/system/dmsfileupload";
import { listUserbypostId } from "@/api/system/user";
import { addReview, delReview,listReview}from "@/api/system/review";
import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {delPublish,getPublish} from "@/api/system/publish";

export default {
  name: "Dmsfileupload",
  dicts: ['dms_file_type', 'dms_file_status','dms_review_result','dms_publish_result'],
  components: { Treeselect },
  data() {
    return {
      //是否为管理员
      isAdmin: this.$store.state.user.name === 'admin',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中文件的状态
      selectfileStatus: null,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文件信息表格数据
      dmsfileuploadList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 部门名称
      deptName: undefined,
      // 评审人列表
      ReviewerList: undefined,
      // 定稿人列表
      PublisherList: undefined,
      // 评审意见列表
      ReviewList: undefined,
      // 定稿意见列表
      PublishList: {},
      // 是否显示新增修改弹出层
      open: false,
      // 是否显示评阅意见层
      openreview: false,
      // 是否显示定稿意见层
      openpublish:false,
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
        fileStatus: null,
        belongteam: null,
        description: null,
        updateBy: this.$store.state.user.name,
        updateTime: null,
        publishId: null,
        //区分文档浏览的查询
        querykind: 2,
        // 用户权限控制需要
        queryuseId:this.$store.state.user.userId,
      },
      // 查询评阅
      reviewquery: {
        pageNum: 1,
        pageSize: 10,
        fileId: null,
        reviewerId: null,
        comment: null,
        isPassed: null
      },
      // 表单参数
      form: {},
      //评审表参数
      reviewform:{
        fileId:null,
        reviewerId:null,
        comment:null,
      },
      // 表单校验
      rules: {
        fileName: [
          { required: true, message: "文件名不能为空", trigger: "blur" }
        ],
        reviewerIds: [
          { required: true, message: "评阅人不能为空", trigger: "blur" }
        ],
        publishId: [
          { required: true, message: "定稿人不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "归属团队不能为空", trigger: "blur" }
        ],
      },
      upload: {
      // 是否禁用上传
      isUploading: false,
      // 设置上传的请求头部
      headers: { Authorization: "Bearer " + getToken() },
      // 上传的地址
      url: process.env.VUE_APP_BASE_API + "/common/upload",
      // 上传的文件列表
      fileList: []
    }
    };
  },
  created() {
    // 打开页面的触发事件
    this.getDeptTree(); //获得部门树
    this.getReviewerList(); //获得评阅人名单
    this.getPublisherList();//获得定稿人名单
    this.getList();
  },
  methods: {
    /** 查询文件信息列表 */
    getList() {
      this.loading = true;
      listDmsfileupload(this.queryParams).then(response => {
        this.dmsfileuploadList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        this.form.belongteam = val.label
    },
    // 新建/修改上传的部门选择事件
    handleSelect2(val) {
      // 通过 Treeselect 实例获取选中的label值
        this.queryParams.belongteam = val.label
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
    /** 根据定稿人ID查姓名 */
    getPublishNameById(userId) {
      if (userId === undefined || userId === null) {
        return "Unknown User";
    }
      const user = this.PublisherList.find(user => user.userId === userId);
      return user ? user.userName : userId.toString();
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
        description: null,
        updateBy:null,
        updateTime: null,
        publishId: null,
        deptId:null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.fileId)
      // 如果选中的行不为空，将第一个选中行的 fileStatus 赋值给 this.selectfileStatus
      if (selection.length > 0) {
        this.selectfileStatus = selection[0].fileStatus;
      } else {
        // 如果没有选中行，可以设置默认值或者清空 this.selectfileStatus
        this.selectfileStatus = null; // 或者设置为默认值
      }
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文件信息";
      this.upload.fileList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const fileId = row.fileId || this.ids
      const fileStatus = row.fileStatus || this.selectfileStatus;
      if(fileStatus !== 1 && fileStatus !== 4) {
        this.$modal.msgError("文件待发布或已发布，请联系定稿人或管理员处理。");
        return;
      }
      getDmsfileupload(fileId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件信息";
        this.upload.fileList = [{ name: this.form.fileName, url: this.form.filePath }];
      });
    },
    /** 查看评审意见 */
    handlereviewlist(){
      this.openreview = true;
      const fileId = this.ids
      this.reviewquery.fileId = fileId[0]
      listReview(this.reviewquery).then(response => {
          // 提取用户ID和用户名信息
          this.ReviewList = response.rows;
          this.reviewtotal = response.total;
          this.loading = false;
        });
    },
    /** 查看定稿意见 */
    handlePublishlist(){
      const fileId = this.ids
      getPublish(fileId).then(response => {
        this.openpublish = true;
          this.PublishList = response.data;
          this.loading = false;
          console.log(this)
        })
        .catch(error => {
            // 处理错误，例如打印错误消息或采取其他适当的措施

            // 清除 loading 状态或执行其他处理逻辑
            this.loading = false;
        });
    },
    /** 提交按钮 */
    // 调整逻辑到control层次
    submitForm() {
      this.$refs["form"].validate(valid => {
        const currentDate = new Date();
        const formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')} ${currentDate.getHours().toString().padStart(2, '0')}:${currentDate.getMinutes().toString().padStart(2, '0')}:${currentDate.getSeconds().toString().padStart(2, '0')}`;
        this.form.updateTime = formattedDate;
        // 将选中的 reviewerIds 转换为对应的用户名数组
        const reviewerNames = this.form.reviewerIds.map(userId => {
            const user = this.ReviewerList.find(u => u.userId === userId);
            return user ? user.userName : '';});
        this.form.reviewer = reviewerNames.join('、'); // 拼接评阅人文本，存入form.reviewer
        this.form.fileStatus = 1;// 重置文件状态
        // 根据form.deptId 赋值form.belongteam
        this.form.belongteam = this.getLabelById(this.deptOptions, this.form.deptId);
        if (valid) {
          if (this.form.fileId != null) {
            updateDmsfileupload(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            //生成随机fileID,并赋值
            this.form.fileId = this.generateFileId();
            //仅在新建文件时候获取用户名，修改不操作
            var currentusername = this.$store.state.user.name;
            this.form.updateBy = currentusername;
            addDmsfileupload(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 下载操作 */
    handleDownload(row) {
      var name = row.fileName;
      var url = row.filePath;
      var suffix = url.substring(url.lastIndexOf("."), url.length);
      const a = document.createElement('a')
      a.setAttribute('download', name + suffix)
      a.setAttribute('target', '_blank')
      a.setAttribute('href', url)
      a.click()
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.reset();
      const fileIds = row.fileId || this.ids;
      // 为避免null的情况，filestatus范围从1开始
      const fileStatus = row.fileStatus || this.selectfileStatus;
      if(fileStatus !== 1 && fileStatus !== 4) {
        this.$modal.msgError("文件待发布或已发布，请联系定稿人或管理员处理。");
        return;
      }
      this.$modal.confirm('是否确认删除文件信息编号为"' + fileIds + '"的数据项？').then(() => {
          //删除上传文件信息
          return delDmsfileupload(fileIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/dmsfileupload/export', {
        ...this.queryParams
      }, `dmsfileupload_${new Date().getTime()}.xlsx`)
    },
      // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      this.form.filePath = response.url;
      this.msgSuccess(response.msg);
    },
    // 文件上传前的大小判断逻辑
    beforeUpload(file){
    const fileSize = file.size; // 文件大小，单位为字节
    // 判断文件大小是否超过50M
    if (fileSize > 50 * 1024 * 1024) {
      this.$message.error('文件大小不能超过50M');
      // 返回 false 阻止上传
      return false;
    }
    // 将文件大小转换为合适的单位 （KB 或 MB）
    const fileSizeFormatted = this.formatFileSize(fileSize);
    // 存储文件大小到 form 对象中
    this.form.fileSize = fileSizeFormatted;
    // 返回 true 允许上传
    return true;
    },
    // 辅助方法：将文件大小转换为合适的单位（KB 或 MB）
    formatFileSize(size) {
      const kilobyte = 1024;
      const megabyte = kilobyte * kilobyte;

      if (size < kilobyte) {
        return size + ' B';
      } else if (size < megabyte) {
        return (size / kilobyte).toFixed(2) + ' KB';
      } else {
        return (size / megabyte).toFixed(2) + ' MB';
      }
    },
    // 辅助方法：生成唯一文件ID
    generateFileId() {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');
      const hour = String(now.getHours()).padStart(2, '0');
      // 生成一个随机的五位数
      const randomNum = String(Math.floor(10000 + Math.random() * 90000));
      // 将组件连接起来形成文件ID
      const fileId = `${year}${month}${day}${hour}${randomNum}`;
      return fileId;
    },
    getReviewResultText() {
      return this.dict.type.dms_review_result;
    },
    getPublishResultText() {
      return this.dict.type.dms_publish_result;
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
  },
};
</script>
<style>
.vue-treeselect{
    height: 22px;
    width: 220px;
}
.review-card .review-info {
  display: flex;
  justify-content: space-between; /* 子元素水平间距平均分布 */
  align-items: flex-start; /* 子元素垂直居中 */
}
</style>