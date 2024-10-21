<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="新闻标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入新闻标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布时间" prop="creatTime">
        <el-date-picker clearable
          v-model="queryParams.creatTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发布时间">
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
          v-hasPermi="['system:images:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:images:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:images:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="imagesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="新闻标题" align="center" prop="title" width="400" show-overflow-tooltip/>
      <el-table-column label="详细描述" align="center" prop="description"  width="600" show-overflow-tooltip/>
      <el-table-column label="图片预览" align="center" prop="path" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.path" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="发布人" align="center" prop="creatBy"/>
      <el-table-column label="发布时间" align="center" prop="creatTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.creatTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否展示" align="center" prop="isShow" :formatter="formatIsShow" width="100"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:images:edit']"
          >修改</el-button>
          <el-button
            size="small"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:images:remove']"
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

    <!-- 添加或修改新闻照片对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="上传图片" prop="path">
          <image-upload v-model="form.path" :limit="1"/>
        </el-form-item>
        <el-form-item label="新闻标题" prop="title">
          <el-input v-model="form.title" placeholder="不超过100字" />
        </el-form-item>
        <el-form-item label="详细描述" prop="description">
          <el-input v-model="form.description"  
              type="textarea"  
              :autosize="{minRows: 4, maxRows: 4}" 
              placeholder="不超过2000字" />
        </el-form-item>
        <el-form-item label="是否展示" prop="isShow">
          <el-switch v-model="form.isShow" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="发布时间" prop="creatTime">
          <el-date-picker clearable
            v-model="form.creatTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间">
          </el-date-picker>
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
import { listImages, getImages, delImages, addImages, updateImages } from "@/api/system/images";

export default {
  name: "Images",
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
      // 新闻照片表格数据
      imagesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        path: null,
        title: null,
        description: null,
        creatTime: null,
        isShow: null
      },
      // 表单参数
      form: { },
      // 表单校验
      rules: {
        path:[
          { required: true, message: "照片不能为空", trigger: "blur" },
        ],
        title: [
          { required: true, message: "新闻标题不能为空", trigger: "blur" },
          { max: 100, message: "新闻标题不能超过100字符", trigger: "blur" }
        ],
        description: [
          { required: true, message: "新闻详情不能为空", trigger: "blur" },
          { max: 2000, message: "详细描述不能超过2000字符", trigger: "blur" }
        ],
        creatTime:[
          { required: true, message: "日期不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询新闻照片列表 */
    getList() {
      this.loading = true;
      listImages(this.queryParams).then(response => {
        this.imagesList = response.rows;
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
        id: null,
        path: null,
        title: null,
        description: null,
        creatTime: null,
        isShow: 1
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加新闻照片";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getImages(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改新闻照片";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.form.creatBy = this.$store.state.user.name;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateImages(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addImages(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除新闻照片编号为"' + ids + '"的数据项？').then(function() {
        return delImages(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/images/export', {
        ...this.queryParams
      }, `images_${new Date().getTime()}.xlsx`)
    },
    formatIsShow(row, index) {
      return row.isShow === 1 ? '是' : '否';
    },
  },
};
</script>
