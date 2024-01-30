<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件ID" prop="FileID">
        <el-input
          v-model="queryParams.FileID"
          placeholder="请输入文件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名" prop="FileName">
        <el-input
          v-model="queryParams.FileName"
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
      <el-form-item label="文件类型" prop="FileType">
        <el-select v-model="queryParams.FileType" placeholder="请选择文件类型" clearable>
          <el-option
            v-for="dict in dict.type.dms_file_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="归属团队" prop="Team">
        <el-input
          v-model="queryParams.Team"
          placeholder="请输入归属团队"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传者" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入上传者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择上传时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['dms:fileupload:add']"
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
          v-hasPermi="['dms:fileupload:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dms:fileupload:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['dms:fileupload:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fileuploadList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文件ID" align="center" prop="FileID" />
      <el-table-column label="文件名" align="center" prop="FileName" />
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="审稿人" align="center" prop="ReviewerID" />
      <el-table-column label="文件类型" align="center" prop="FileType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dms_file_type" :value="scope.row.FileType"/>
        </template>
      </el-table-column>
      <el-table-column label="文件状态" align="center" prop="Status" />
      <el-table-column label="归属团队" align="center" prop="Team" />
      <el-table-column label="创建者" align="center" prop="updateBy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dms:fileupload:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dms:fileupload:remove']"
          >删除</el-button>
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

    <!-- 添加或修改文件上传对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件名" prop="FileName">
          <el-input v-model="form.FileName" placeholder="请输入文件名" />
        </el-form-item>

        <el-form-item label="上传文件" prop="field103">
          <el-upload ref="field103" :file-list="field103fileList" :action="field103Action"
            :before-upload="field103BeforeUpload">
            <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传不超过 100MB 的文件</div>
          </el-upload>
        </el-form-item>

        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="文件类型" prop="FileType">
          <el-select v-model="form.FileType" placeholder="请选择文件类型">
            <el-option
              v-for="dict in dict.type.dms_file_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="归属团队" prop="Team">
          <el-input v-model="form.Team" placeholder="请输入归属团队" />
        </el-form-item>
        <el-form-item label="文件描述" prop="Description">
          <el-input v-model="form.Description" type="textarea" placeholder="请输入内容" />
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
import { listFileupload, getFileupload, delFileupload, addFileupload, updateFileupload } from "@/api/dms/fileupload";

export default {
  name: "Fileupload",
  dicts: ['dms_file_type'],
  data() {
    return {
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
      // 文件上传表格数据
      fileuploadList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        FileID: null,
        FileName: null,
        author: null,
        ReviewerID: null,
        FileType: null,
        FileSize: null,
        Status: null,
        Team: null,
        Description: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {
        field103: null,
      },
      // 表单校验
      rules: {
        FileName: [
          { required: true, message: "文件名不能为空", trigger: "blur" }
        ],
        author: [
          { required: true, message: "作者不能为空", trigger: "blur" }
        ],
        FileType: [
          { required: true, message: "文件类型不能为空", trigger: "change" }
        ],
        Team: [
          { required: true, message: "归属团队不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ]
      },
      field103Action: 'https://jsonplaceholder.typicode.com/posts/',
      field103fileList: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询文件上传列表 */
    getList() {
      this.loading = true;
      listFileupload(this.queryParams).then(response => {
        this.fileuploadList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        FileID: null,
        FileName: null,
        FilePath: null,
        author: null,
        ReviewerID: null,
        FileType: null,
        FileSize: null,
        Status: null,
        Team: null,
        Description: null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.FileID)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文件上传";
    },
    /** 检查文件大小是否超过 100MB */
    field103BeforeUpload(file) {
      let isRightSize = file.size / 1024 / 1024 < 100
      if (!isRightSize) {
        this.$message.error('文件大小超过 100MB')
      }
      return isRightSize
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const FileID = row.FileID || this.ids
      getFileupload(FileID).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件上传";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.FileID != null) {
            updateFileupload(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFileupload(this.form).then(response => {
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
      const FileIDs = row.FileID || this.ids;
      this.$modal.confirm('是否确认删除文件上传编号为"' + FileIDs + '"的数据项？').then(function() {
        return delFileupload(FileIDs);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('dms/fileupload/export', {
        ...this.queryParams
      }, `fileupload_${new Date().getTime()}.xlsx`)
    }
  }
};

</script>
