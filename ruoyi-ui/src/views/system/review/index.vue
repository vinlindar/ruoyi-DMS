<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件ID" prop="fileId">
        <el-input
          v-model="queryParams.fileId"
          placeholder="请输入文件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="评阅结果" prop="isPassed">
        <el-select v-model="queryParams.isPassed" placeholder="请选择评阅结果" clearable>
          <el-option
            v-for="dict in dict.type.dms_review_result"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:review:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 文档信息展示-->
    <el-table v-loading="loading" :data="reviewList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文件ID" align="center" prop="fileId" />
      <el-table-column label="文件名" align="center" prop="fileName" />
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="所有评阅人" align="center" prop="reviewer" />
      <el-table-column label="当前评阅人" align="center" prop="reviewerName" />
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
      <el-table-column label="上传者" align="center" prop="updateBy" />
      <el-table-column label="提交时间" align="center" prop="updateTime" />
      <el-table-column label="评阅结果" align="center" prop="isPassed" >
        <template slot-scope="scope">
            <dict-tag :options="dict.type.dms_review_result" :value="scope.row.isPassed"/>
          </template>
        </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:review:edit']"
          >评阅</el-button>
          <el-button
            size="mini"
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评阅意见" prop="comment">
          <el-input v-model="form.comment" type="textarea" placeholder="最多500字" />
        </el-form-item>
        <el-form-item label="评阅结果" prop="isPassed">
          <el-select v-model="form.isPassed" placeholder="请选择评阅结果">
            <el-option
              v-for="dict in dict.type.dms_review_result"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReview, getReview, delReview, addReview, updateReview } from "@/api/system/review";
import { listUserbypostId } from "@/api/system/user";

export default {
  name: "Review",
  dicts: ['dms_file_type', 'dms_file_status','dms_review_result'],
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
      // 文档评阅表格数据
      reviewList: [],
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
        fileId: null,
        reviewerId: this.$store.state.user.id,
        comment: null,
        isPassed: null
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
        isPassed: null
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const fileId = row.fileId
      const reviewerId = row.reviewerId
      const fileStatus = row.fileStatus || this.selectfileStatus;
      if(fileStatus == 3) {
        this.$modal.msgError("文件已发布，不支持评阅。");
        return;
      }
      getReview(fileId,reviewerId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "文档评阅";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.fileId != null) {
            const currentDate = new Date();
            const formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')} ${currentDate.getHours().toString().padStart(2, '0')}:${currentDate.getMinutes().toString().padStart(2, '0')}:${currentDate.getSeconds().toString().padStart(2, '0')}`;
            this.form.reviewTime = formattedDate;
            console.log(this.form.reviewTime)
            updateReview(this.form).then(response => {
              this.$modal.msgSuccess("评阅成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReview(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
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
