<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="文件ID" prop="fileId">
        <el-input
          v-model="queryParams.fileId"
          placeholder="请输入文件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="文件名" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归属团队" prop="deptId">
        <treeselect 
          v-model="queryParams.deptId"
          :options="deptOptions"
          :show-count="true"
          placeholder="请输入归属团队"
        />
      </el-form-item>
      <el-form-item label="下载人" prop="downloadUser">
        <el-input
          v-model="queryParams.downloadUser"
          placeholder="请输入下载人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下载时间" prop="downloadTime">
        <el-date-picker clearable
          v-model="queryParams.downloadTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择下载时间">
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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:records:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:records:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="文件ID" align="center" prop="fileId" /> -->
      <el-table-column label="文件名" align="center" prop="fileName" width="200px" class-name="file-name-column">
        <template slot-scope="scope">
          <router-link :to="'/file/filedetai1/' + scope.row.fileId" class="link-type">
            <span class="file-name">{{ scope.row.fileName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="归属团队" align="center" prop="belongteam" />
      <el-table-column label="下载人" align="center" prop="downloadUser" />
      <el-table-column label="下载时间" align="center" prop="downloadTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.downloadTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
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

  </div>
</template>

<style scoped>
.file-name-column .file-name {
  max-width: 200px; /* 设置最大宽度 */
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 超出部分隐藏 */
  text-overflow: ellipsis; /* 显示省略号 */
  display: inline-block; /* 确保 ellipsis 生效 */
}
</style>

<script>
import { listRecords, getRecords, delRecords, addRecords, updateRecords } from "@/api/system/records";
import {deptTreeSelect  } from "@/api/system/dmsfileupload";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Records",
  components: { Treeselect },
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
      // 下载记录表格数据
      recordsList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileId: null,
        fileName: null,
        deptId: null,
        belongteam: null,
        downloadUserid: null,
        downloadUser: null,
        downloadTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        fileId: [
          { required: true, message: "文件ID不能为空", trigger: "blur" }
        ],
        fileName: [
          { required: true, message: "文件名不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getDeptTree();
    this.getList();
  },
  methods: {
    /** 查询下载记录列表 */
    getList() {
      this.loading = true;
      console.log(this);
      listRecords(this.queryParams).then(response => {
        this.recordsList = response.rows;
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        fileId: null,
        fileName: null,
        deptId: null,
        belongteam: null,
        downloadUserid: null,
        downloadUser: null,
        downloadTime: null
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
      this.title = "添加下载记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecords(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改下载记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRecords(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecords(this.form).then(response => {
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
      const ids = row.Id || this.ids;
      this.$modal.confirm('是否确认删除该下载记录？').then(function() {
        return delRecords(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/records/export', {
        ...this.queryParams
      }, `records_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>
<style>
.vue-treeselect{
    height: 22px;
    width: 220px;
}
</style>