<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评阅人" prop="reviewerId">
        <el-select v-model="queryParams.reviewerId" placeholder="仅管理员可操作" :clearable="isAdmin">
            <el-option 
              v-for="user in ReviewerList" 
              :key="user.userId" 
              :label="user.userName" 
              :value="parseInt(user.userId)"
              :disabled="!isAdmin"
            />
          </el-select>
      </el-form-item>
      <el-form-item label="评阅状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择评阅状态" clearable>
          <el-option
            v-for="dict in dict.type.dms_review_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['system:review:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 文档信息展示-->
    <el-table v-loading="loading" :data="reviewList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文件名" align="center" prop="fileName" width="600">
        <template slot-scope="scope">
          <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
            <span>{{ scope.row.fileName }}</span>
          </router-link>
        </template>
      </el-table-column>/>
      <el-table-column label="文件分类" align="center" prop="fileType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dms_file_type" :value="scope.row.fileType"/>
        </template>
      </el-table-column>
      <el-table-column label="文件状态" align="center" prop="fileStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dms_file_status" :value="scope.row.fileStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="归属团队" align="center" prop="belongteam" />
      <el-table-column label="上传者" align="center" prop="updateBy" />
      <el-table-column label="提交时间" align="center" prop="updateTime" />
      <el-table-column label="评阅状态" align="center" prop="status" >
        <template slot-scope="scope">
            <dict-tag :options="dict.type.dms_review_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:review:edit']"
          >评阅</el-button>
          <el-button
            type="text"
            icon="el-icon-edit"
            @click="handleDownload(scope.row)"
          >下载</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改文档评阅对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <div class="review-system">
        <div class="container">
          <div class="left" >
            <h3>评阅记录</h3>
          </div>
          <div class="right">
            <el-table v-loading="loading" :data="reviewrecords" @selection-change="handleSelectionChange" :row-class-name="rowClassName">
              <el-table-column label="评阅人" align="center">
                <template slot-scope="scope">
                  {{ getReviewerById(scope.row.reviewerId) }}
                </template>
              </el-table-column>
              <el-table-column label="评阅意见" align="center" prop="comment"  width="400" />
              <el-table-column label="评阅时间" align="center" prop="reviewTime" />
            </el-table>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="left">
          <h3>我的评阅</h3>
        </div>
        <div class="right">
          <div class="review-input">
            <el-form ref="form" :model="form" :rules="rules" label-width="110px">
              <el-form-item label="评阅意见" prop="comment" style="margin-bottom: 0px;">
                <el-input v-model="form.comment" type="textarea" placeholder="请输入内容,重复评阅覆盖本轮评阅意见" style="width:500px;"/>
              </el-form-item>
            </el-form>
            <div class="button-group">
              <el-button type="primary" @click="submitForm">确 定</el-button>
              <el-button @click="cancel">取 消</el-button>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReview, getReview, delReview, addReview, updateReview } from "@/api/system/review";
import { listUserbypostId } from "@/api/system/user";

export default {
  name: "Review",
  dicts: ['dms_file_type', 'dms_file_status','dms_review_status'],
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 当前文档评阅表格数据
      reviewList: undefined,
      // 文档评阅信息（当前和历史）
      reviewrecords:undefined,
      //
      queryrecords:{
        fileId:null,
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 评审人列表
      ReviewerList: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        statue:null,
        fileId: null,
        reviewerId: this.$store.state.user.id,
        comment: null,
        isCurrent:1 //默认查询当前评阅信息
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getReviewerList();
    this.getList();
  },
  methods: {
    /** 查询文档评阅列表 */
    getList() {
      this.loading = true;
      listReview(this.queryParams).then(response => {
        this.reviewList = response.rows;
        console.log(this.reviewList)
        this.total = response.total;
        this.loading = false;
      });
    },
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
    /**  当前评阅记录变色 */
    rowClassName({ row }) {
      return row.isCurrent === 1 ? 'current-row' : '';
    },
        /**  查询评阅人下拉列表 */
    getReviewerList() {
      this.loading = true;
      const postID = 2;
      listUserbypostId(postID).then(response => {
          // 提取用户ID和用户名信息
          this.ReviewerList = response.data;
          this.reviewertotal = response.length;
          console.log( this)
          this.loading = false;
        }
      );
    },
    getReviewerById(userId) {
      if (userId === undefined || userId === null) {
        return "Unknown User";
    }
      const user = this.ReviewerList.find(user => user.userId === userId);
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
        reviewerId: null,
        comment: null,
        status:null,
        isCurrent:1,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文档评阅";
    },
    /** 评阅按钮操作 */
    handleUpdate(row) {
      this.reset();
      console.log(row);
      this.loading = true;
      this.currentid=row.id;
      this.currentfileid=row.fileId;
      this.queryrecords.fileId=row.fileId; //获取文件ID
      listReview(this.queryrecords).then(response => {
        this.reviewrecords = response.rows;
        this.total = response.total;
        this.loading = false;
        this.open = true;
        this.title = "文档评阅";
      });
    },
    /** 提交按钮 */
    submitForm() {
      console.log(this);
      this.$refs["form"].validate(valid => {
        if (valid) {
            const currentDate = new Date();
            const formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')} ${currentDate.getHours().toString().padStart(2, '0')}:${currentDate.getMinutes().toString().padStart(2, '0')}:${currentDate.getSeconds().toString().padStart(2, '0')}`;
            this.form.reviewTime = formattedDate;
            this.form.id = this.currentid;
            this.form.fileId = this.currentfileid;
            this.form.status = 2;
            updateReview(this.form).then(response => {
              this.$modal.msgSuccess("评阅成功");
              this.open = false;
              this.getList();
            });
          } 
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const fileIds = row.fileId || this.ids;
      this.$modal.confirm('是否确认删除文档评阅编号为"' + fileIds + '"的数据项？').then(function() {
        return delReview(fileIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport(row) {
      const fileIds = row.fileId || this.ids;
      console.log(this);
      this.download('system/review/export', {
        ...this.queryParams
      }, `review_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
.el-table__body tr.current-row > td.el-table__cell {
    background-color: #e8f4ff; /* 淡蓝色背景 */
}
.review-system {
  display: flex;
  flex-direction: column;
}
.container {
  display: flex;
  border-bottom: 1px solid #000;
  padding-bottom: 10px;
  margin-bottom: 10px;
}
.left {
  flex: 0 0 80px; /* 设置左侧容器固定宽度为50px */
  display: flex;
  justify-content: center; /* 居中内容 */
  align-items: center; /* 垂直居中 */
}
.right {
  flex: 1;
  border-left: 1px solid #000;
  padding-left: 10px;
}
.review-input {
  display: flex;
  align-items: center;
}
.button-group {
  margin-left: 20px;
}
</style>
