<!-- 团队已发布文档管理（修改（文档管理员）/删除（超管））-->
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
    <el-table v-loading="loading" :data="dmsfileuploadList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="fileName" label="文件名" header-align="center" align="left" width="600" show-overflow-tooltip> 
        <template slot-scope="scope">
          <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
            <span>{{  scope.row.fileName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="评阅人" align="center" prop="reviewer" show-overflow-tooltip/>
      <el-table-column label="定稿人" align="center">
        <template slot-scope="scope">
          {{ getPublishNameById(scope.row.publishId) }}
        </template>
      </el-table-column>
      <el-table-column label="文件分类" align="center" prop="fileType" show-overflow-tooltip>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dms_file_type" :value="scope.row.fileType"/>
        </template>
      </el-table-column>
      <el-table-column label="文件状态" align="center" prop="fileStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dms_file_status" :value="scope.row.fileStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="归属团队" align="center" prop="belongteam"  width="200"/>
      <el-table-column label="提交时间" align="center" prop="updateTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dmsfileupload:edit']"
          >修改</el-button>
          <el-button
            size="small"
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
    <!-- 修改文件信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-row>
        <!-- 左侧表单 -->
        <el-col :span="12" >
          <el-form ref="form" :model="form" :rules="rules" label-width="110px">
            <el-form-item label="文件名" prop="fileName">
              <el-input v-model="form.fileName" placeholder="请输入文件名" />
            </el-form-item>
            <el-form-item label="关键词" prop="keywords">
              <el-input v-model="form.keywords" placeholder="请输入关键词"/>
            </el-form-item>
            <el-form-item label="文件上传">
              <el-upload
                  ref="upload"
                  :action="upload.url"
                  :headers="upload.headers"
                  :file-list="upload.fileList"
                  :on-progress="handleFileUploadProgress"
                  :on-success="handleFileSuccess"
                  :on-change="handleChange"
                  :auto-upload="true"
                  :before-upload="beforeUpload">
                  <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                  <div slot="tip" class="el-upload__tip">仅支持上传单个文件,且不超过50M</div>
                </el-upload>
              </el-form-item>
            <el-form-item label="文件分类" prop="fileType">
              <el-select v-model="form.fileType" placeholder="请选择文件分类" style="width: 270px;">
                <el-option
                  v-for="dict in dict.type.dms_file_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="文件描述" prop="description">
              <el-input v-model="form.description" type="textarea" :autosize="{minRows: 4, maxRows: 4}"  placeholder="不超过500字符,包括建议发布范围" />
            </el-form-item>
            <el-form-item label="发布类型" prop="shareType">
              <el-radio-group v-model="form.shareType" @change="handleShareTypeChange" >
                <el-radio label="DEPT">团队</el-radio>
                <el-radio label="USER">个人</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item v-if="form.shareType === 'DEPT'" label="团队范围" prop="deptIds">
              <treeselect 
                v-model="form.deptIds" 
                :options="deptOptions" 
                :show-count="true" 
                :multiple="true" 
                style="width: 270px;"
                placeholder="请选择团队范围" />
            </el-form-item>
            <el-form-item v-if="form.shareType === 'USER'" label="个人范围" prop="userIds">
              <treeselect 
                v-model="form.userIds" 
                :options="userdeptOptions" 
                :show-count="true" 
                :multiple="true" 
                style="width: 270px;"
                placeholder="请选择个人范围" />
            </el-form-item>
          </el-form> 
        </el-col>
        <el-col :span="12">
          <div style="text-align: center; height: 100%; display: flex; align-items: center; justify-content: center; margin-left: 20px;">
            <el-image :src="imageurl" alt="展示图片" style="max-width: 100%; max-height: 100%;"/>
          </div>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDmsfileupload, getDmsfileupload, delDmsfileupload, updateDmsfileupload,deptTreeSelect,manageDmsfile } from "@/api/system/dmsfileupload";
import {  updatePublish } from "@/api/system/publish";
import { listUserbypostId,getUsersByDeptId  } from "@/api/system/user";
import { getUserProfile } from "@/api/system/user";
import { getPermissions } from "@/api/system/permissions";
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
        fileStatus: 3, //仅展示已发布的文档
        belongteam: null,
        description: null,
        updateBy: null,
        updateTime: null,
        publishId: null,
        //区分文档浏览的查询(1.浏览查询；其余.全部查询) 目前都可以看到，不使用1
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
      // 表单校验
      rules: {
        fileName: [
          { required: true, message: "文件名不能为空", trigger: "blur" },
          { max:100, message: "文件名不能超过100字符", trigger: "blur" }
        ],
        description:[
        { max:500, message: "详细描述不能超过500字符", trigger: "blur" }
        ],
        shareType: [
        { required: true, message: '请选择发布类型', trigger: 'change' }
        ],
        deptIds:[
        { required: true, message: '团队范围不能为空', trigger: 'change', 
          validator: (rule, value, callback) => {
            if (this.form.shareType === 'DEPT' && (!value || value.length === 0)) {
              callback(new Error('团队范围不能为空'));
            } else {
              callback();
            }}}
        ],
        userIds: [
        { required: true, message: '个人范围不能为空', trigger: 'change', 
          validator: (rule, value, callback) => {
            if (this.form.shareType === 'USER' && (!value || value.length === 0)) {
              callback(new Error('个人范围不能为空'));
            } else {
              callback();
            }}}
        ]
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
    imageurl: '/filetype.png',
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
    /**限制上传文件1个 */
    handleChange(file, fileList) {
      // 如果 fileList 中文件数量大于 1，删除多余的文件
      if (fileList.length > 1) {
        fileList.splice(0, 1); // 移除第一个文件，即保留最新上传的文件
      }
    },
    /** 获得登录用户的归属部门 */
    getUserTeam(){
      this.loading = true;
      return getUserProfile().then(response => {
        this.userdetail = response.data;
        this.queryParams.belongteam=this.userdetail.dept.deptName;
        this.form.deptId = this.userdetail.dept.deptId;
        this.loading = false;
      })
    },
    /** 查询文件信息列表 */
    getList() {
      this.loading = true;
      return listDmsfileupload(this.queryParams).then(response => {
        this.dmsfileuploadList = response.rows;
        this.total = response.total;
        this.loading = false;
      })
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
        this.loadUsersForDepartments(this.deptOptions);
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
        this.queryParams.belongteam = val.label;
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
    /** 修改按钮 */
    handleUpdate(row) {
      this.reset();
      const fileId = row.fileId || this.ids[0]
      const fileStatus = row.fileStatus || this.selectfileStatus;
      getDmsfileupload(fileId).then(response => {
        this.form = response.data;
        this.form.deptId = this.getIdByLabel(this.deptOptions, this.form.belongteam);
        this.title = "修改文件信息";
        this.upload.fileList = [{ name: this.form.fileName, url: this.form.filePath }];
        getPermissions(fileId).then(response =>{
        this.permissionList = response.data;
        this.loading = false;
        if (this.permissionList.length > 0) {
          this.form.shareType=this.permissionList[0].shareType;
          this.$set(this.form, 'deptIds', this.permissionList.map(dept => dept.deptId));
          this.$set(this.form, 'userIds', this.permissionList.map(user => user.userId));
        }
        this.open = true;
      })
      });
    },
    /** 提交按钮 */
    submitForm() {
       // 调整逻辑到control层次
      this.$refs["form"].validate(async valid => {
        if (!valid) {
          return;
        }
        const uploadFile = this.$refs.upload.uploadFiles[0];
        if (!uploadFile) {
            this.$message.error('请选择文件');
            return;
        }
        if (this.upload.isUploading) {
          this.$message.error('文件上传中')
          return;
        }
        // 根据form.deptId 赋值form.belongteam
        this.form.belongteam = this.getLabelById(this.deptOptions, this.form.deptId);
        try{
          this.form.isPassed = 2;
          console.log(this.form)
          //修改基本信息
            manageDmsfile(this.form).then(response => {
              updatePublish(this.form);
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              });
          //重置发布范围

          }catch (error) {
              this.$modal.msgError("修改失败：" + error.message);
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
      this.$modal.confirm('是否确认删除文件信息编号为"' + fileIds + '"的数据项？,删除后无法恢复！').then(() => {
          //删除上传文件信息
          return delDmsfileupload(fileIds);
      }).then(() => {
        //删除服务器对应文件

        this.getList();
        this.$modal.msgSuccess("删除成功");
      });
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
      if (response && response.code === 200) {
        this.form.filePath = response.url; // 假设成功响应中包含文件路径信息
        this.msgSuccess(response.msg); // 显示成功消息
      } else {
        this.$message.error('文件上传失败'+response.msg);
        this.form.filePath = ''; 
      }
    },
    // 文件上传前的大小判断逻辑
    beforeUpload(file){
      this.upload.isUploading = true;
      const fileSize = file.size; // 文件大小，单位为字节
      // 判断文件大小是否超过100M
      if (fileSize > 100 * 1024 * 1024) {
        this.$message.error('文件大小不能超过100M');
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
    // 辅助方法：获得label对应ID
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
    // 辅助方法：获得ID对应label
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
    // 改变发布类型
    handleShareTypeChange() {
      // 清空相应的范围数据
      if (this.form.shareType === 'DEPT') {
        this.$set(this.form, 'userIds', []);
      } else if (this.form.shareType === 'USER') {
        this.$set(this.form, 'deptIds', []);
      }
    },
    /** 展开 userIds 中的团队ID为用户ID */
    expandUserIds() {
      const userIds = new Set();
      // 递归遍历 userdeptOptions，找到所有用户ID
      const traverse = (nodes) => {
        nodes.forEach(node => {
          if (node.type === 'dept' && node.children) {
            traverse(node.children);
          } else if (node.type === 'user') {
            userIds.add(node.id);
          }
        });
      };
      // 将 form.userIds 中的所有 ID 进行处理
      this.form.userIds.forEach(id => {
        const node = this.findNodeById(this.userdeptOptions, id);
        if (node) {
          if (node.type === 'dept' && node.children) {
            // 如果是团队ID，展开其内所有用户ID
            traverse([node]);
          } else if (node.type === 'user') {
            // 如果是用户ID，直接添加
            userIds.add(id);
          }
        }
      });
      // 更新 form.userIds 仅包含用户ID
      this.form.userIds = Array.from(userIds);
    },
    /** 根据ID在树结构中找到对应的节点 */
    findNodeById(nodes, id) {
      for (let node of nodes) {
        if (node.id === id) {
          return node;
        }
        if (node.children) {
          const found = this.findNodeById(node.children, id);
          if (found) {
            return found;
          }
        }
      }
      return null;
    },
  },
};
</script>
<style>
.vue-treeselect{
    height: 22px;
    width: 220px;
}
</style>