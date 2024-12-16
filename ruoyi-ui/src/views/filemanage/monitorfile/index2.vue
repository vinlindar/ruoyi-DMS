<!-- 团队待评阅/定稿文档管理（仅查看提醒）-->
<template>
    <div class="app-container">
      <!-- 顶部搜索框-->
      <el-form :model="queryParams" ref="queryParams" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="文件名或关键词" prop="fileName" label-width="150px !important">
          <el-input
            v-model="queryParams.fileName"
            placeholder="请输入文件名或关键词"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="文件分类" prop="fileType">
          <el-select v-model="queryParams.fileType" placeholder="请选择文件分类" clearable>
            <el-option
              v-for="dict in dict.type.dms_file_type"
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
            placeholder="请选择归属团队"
            :disabled="!isAdmin"  />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <!-- 文档信息展示-->
      <el-table v-loading="loading" :data="unpublishedFileList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="fileName" label="文件名" header-align="center" align="left" width="600" show-overflow-tooltip> 
          <template slot-scope="scope">
            <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
              <span>{{  scope.row.fileName }}</span>
            </router-link>
          </template>
        </el-table-column>
        <el-table-column label="提交人" align="center" prop="updateby" show-overflow-tooltip/>
        <el-table-column label="提交时间" align="center" prop="updateTime" width="130">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="评阅人" align="center" prop="reviewer" show-overflow-tooltip/>
        <el-table-column label="定稿人" align="center">
          <template slot-scope="scope">
            {{ getPublishNameById(scope.row.publishId) }}
          </template>
        </el-table-column>
        <el-table-column label="文件状态" align="center" prop="fileStatus">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.dms_file_status" :value="scope.row.fileStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="归属团队" align="center" prop="belongteam"  width="200"/>
      </el-table>
      <!-- 分页功能代码-->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </template>
<script>
import { deptTreeSelect,listUnpublishedFile } from "@/api/system/dmsfileupload";
import { listUserbypostId,getUsersByDeptId  } from "@/api/system/user";
import { getUserProfile } from "@/api/system/user";
import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Dmsfileupload",
  dicts: ['dms_file_type', 'dms_file_status','dms_review_result','dms_publish_result'],
  components: { Treeselect },
  data() {
    return {
      user: {},
      //是否为管理员
      isAdmin: this.$store.state.user.name == 'admin',
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
      unpublishedFileList: [],
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
      ReviewList: {},
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
        keywords:null,
        filePath: null,
        author: null,
        reviewer: null,
        fileType: null,
        fileSize: null,
        fileStatus: null, 
        belongteam: null,
        description: null,
        updateBy: null,
        updateTime: null,
        publishId: null,
        querykind: 0,
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
      form: {
        deptId:null,
        shareType:null,
        deptIds: [],
        userIds: [],
      },
      //评审表参数
      reviewform:{
        fileId:null,
        reviewerId:null,
        comment:null,
      },
      rules: {
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
      },
    };
  },
  created() {
      this.getUserTeam().then(()  => {
        // 在 getUserTeam 完成后执行 getList
        return this.getList();
      });
      this.getDeptTree(); // 获得部门树
      this.getReviewerList(); // 获得评阅人名单
      this.getPublisherList(); // 获得定稿人名单
  },
  methods: {
    /** 获得登录用户的归属部门 */
    getUserTeam(){
      this.loading = true;
      return getUserProfile().then(response => {
        this.userdetail = response.data;
        if(this.userdetail.deptId!=100){
        this.queryParams.belongteam=this.userdetail.dept.deptName;
      }
        this.form.deptId = this.userdetail.dept.deptId;
        this.loading = false;
      })
    },
    /** 查询文件信息列表 */
    getList() {
      this.loading = true;
      return listUnpublishedFile(this.queryParams).then(response => {
        this.unpublishedFileList = response.rows;
        this.total = response.total;
        this.loading = false;
      })
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
        this.loadUsersForDepartments(this.deptOptions);
      });
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
    // 新建/修改上传的部门选择事件
    handleSelect2(val) {
      // 通过 Treeselect 实例获取选中的label值
        this.queryParams.belongteam = val.label;
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
    /** 根据定稿人ID查姓名 */
    getPublishNameById(userId) {
      if (userId === undefined || userId === null) {
        return "Unknown User";
    }
      const user = this.PublisherList.find(user => user.userId === userId);
      return user ? user.userName : userId.toString();
    },
    /** 查询部门用户下拉树结构 */
    loadUsersForDepartments(departments) {
      const departmentPromises = departments.map(dept => {
        return this.loadUsersForDepartment(dept);
      });

      Promise.all(departmentPromises).then(results => {
        this.userdeptOptions = results;
      });
    },
    loadUsersForDepartment(department) {
      return getUsersByDeptId(department.id).then(userResponse => {
        const userOptions = userResponse.data
        .filter(user => user.userId !== 1&& user.userId !== 101) //过滤admin和无用户
        .map(user => ({
          label: user.userName,
          id: user.userId,
          type:'user'
        }));
        if (department.children && department.children.length) {
          const childrenPromises = department.children.map(childDept => this.loadUsersForDepartment(childDept));
          return Promise.all(childrenPromises).then(childrenResults => {
            return {
              label: department.label,
              id: department.id,
              type:'dept',
              children: userOptions.concat(childrenResults)
            };
          });
        } else {
          return {
            label: department.label,
            id: department.id,
            type:'dept',
            children: userOptions
          };
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        fileName: null,
        keywords:null,
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
      const currentBelongteam = this.queryParams.belongteam; 
      this.resetForm("queryParams");
      this.queryParams.belongteam = currentBelongteam; 
      this.handleQuery();
    },
  }
}
</script>
<style>
.vue-treeselect{
    height: 22px;
    width: 220px;
}
</style>