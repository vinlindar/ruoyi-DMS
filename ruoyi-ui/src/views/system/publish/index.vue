<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="定稿人" prop="publishId">
        <el-select v-model="queryParams.publishId" placeholder="仅管理员可操作" :clearable="isAdmin">
            <el-option 
              v-for="user in PublisherList" 
              :key="user.userId" 
              :label="user.userName" 
              :value="parseInt(user.userId)"
              :disabled="!isAdmin"
            />
          </el-select>
      </el-form-item>
      <el-form-item label="定稿结果" prop="isPassed">
        <el-select v-model="queryParams.isPassed" placeholder="请选择定稿结果" clearable>
          <el-option
            v-for="dict in dict.type.dms_publish_result"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="定稿时间" prop="publishTime">
        <el-date-picker clearable
          v-model="queryParams.publishTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择定稿时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 文档操作栏位-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['system:publish:export']"
        >导出信息列表</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 文档信息列表展示-->
    <el-table v-loading="loading" :data="publishList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文件名" header-align="center" align="left" prop="fileName" width="600px" show-overflow-tooltip>
        <template slot-scope="scope">
          <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
            <span class="file-name">{{ scope.row.fileName }}</span>
          </router-link>
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
<!--       <el-table-column label="归属团队" align="center" prop="belongteam" width="200"/> -->
      <el-table-column label="提交人" align="center" prop="updateBy" />
      <el-table-column label="提交时间" align="center" prop="updateTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="定稿结果" align="center" prop="isPassed">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dms_publish_result" :value="scope.row.isPassed"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:publish:edit']"
          >定稿</el-button>
          <el-button
            size="small"
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

    <!-- 添加或修改定稿对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-table v-loading="loading" :data="filteredReviewRecords" @selection-change="handleSelectionChange" :row-class-name="rowClassName">
        <el-table-column label="评阅人" align="center">
            <template slot-scope="scope">
                {{ getReviewerById(scope.row.reviewerId) }}
            </template>
        </el-table-column>
        <el-table-column label="评阅意见" align="center" prop="comment"  width="400" />
        <el-table-column label="评阅时间" align="center" prop="reviewTime" />
      </el-table>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px" class="publishtable">
        <el-form-item label="定稿意见" prop="comment">
          <el-input v-model="form.comment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="定稿结果" prop="isPassed">
          <el-select v-model="form.isPassed" placeholder="请选择定稿结果">
            <el-option
              v-for="dict in modifiedDmsPublishResult"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布类型" prop="shareType">
          <el-radio-group v-model="form.shareType" @change="handleShareTypeChange">
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
            style="width: 400px;"
            placeholder="请选择团队范围" />
        </el-form-item>
        <el-form-item v-if="form.shareType === 'USER'" label="个人范围" prop="userIds">
          <treeselect 
            v-model="form.userIds" 
            :options="userdeptOptions" 
            :show-count="true" 
            :multiple="true" 
            style="width: 400px;"
            placeholder="请选择个人范围" />
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
import { listPublish, getPublish, delPublish, updatePublish } from "@/api/system/publish";
import { listReview } from "@/api/system/review";
import { listUserbypostId,getUsersByDeptId } from "@/api/system/user";
import { getPermissions } from "@/api/system/permissions";
import { deptTreeSelect} from "@/api/system/dmsfileupload";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Publish",
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
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 部门树选项
      deptOptions: undefined,
      // 用户树选择
      userdeptOptions: undefined,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 定稿表格数据
      publishList: [],
      //发布范围数据
      permissionList:[],
      // 弹出层标题
      title: "",
      // 定稿人列表
      PublisherList: undefined,
      // 文档评阅信息（当前和历史）
      reviewrecords:[],
      // 是否显示弹出层
      open: false,
      // 评阅查询参数
      reviewquery:{
        fileId:null,
      },
      // 定稿查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileId: null,
        publishId: this.$store.state.user.id,
        comment: null,
        isPassed: null,
        publishTime: null,
        isCurrent:1 //默认查询当前定稿信息
      },
      // 表单参数
      form: {
        shareType:null
      },
      // 表单校验
      rules: {
        shareType:[
          { required: true, message: "请选择发布类型", trigger: "blur" }
        ],
      }
    };
  },
  computed: {
    modifiedDmsPublishResult() {
      // 复制原始数组
      let result = [...this.dict.type.dms_publish_result];
      // 修改待定稿的label为“取消发布”
      result = result.map(item => 
        item.label === "待定稿" ? { ...item, label: "取消发布" } : item
      );
      return result;
    },
    filteredReviewRecords() {
      return this.reviewrecords.filter(record => 
        record.isCurrent === 1 || (record.comment)
      );
    }
  },
  created() {
    this.getDeptTree();
    this.getReviewerList();
    this.getPublisherList();
    this.getList();
  },
  methods: {
    /** 查询定稿列表 */
    getList() {
      this.loading = true;
      listPublish(this.queryParams).then(response => {
        this.publishList = response.rows;
        this.total = response.total;
        this.loading = false;
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
    getPublishNameById(userId) {
      if (userId === undefined || userId === null) {
        return "Unknown User";
    }
      const user = this.PublisherList.find(user => user.userId === userId);
      return user ? user.userName : userId.toString();
    },
    getReviewerById(userId) {
      if (userId === undefined || userId === null) {
        return "Unknown User";
      }
      const user = this.ReviewerList.find(user => user.userId === userId);
      return user ? user.userName : userId.toString();
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
        this.loadUsersForDepartments(this.deptOptions);
      });
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
    //改变发布类型
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        fileId: null,
        publishId: null,
        comment: null,
        isPassed: null,
        publishTime: null,
        isCurrent:1
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
      this.title = "添加定稿";
    },
    /** 定稿按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.currentid=row.id;
      this.reviewquery.fileId=row.fileId;
      listReview(this.reviewquery).then(response =>{
        this.reviewrecords = response.rows;
        this.loading = false;
        this.open = true;
      });
      getPublish(this.currentid).then(response => {
        this.form = response.data;
        if(this.form.isPassed==1){
          this.form.isPassed = 2;
        }
        this.open = true;
        this.title = "文档定稿";
        const fileId = this.form.fileId;
        getPermissions(fileId).then(response =>{
          this.permissionList = response.data;
          this.loading = false;
          this.form.shareType=this.permissionList[0].shareType;
          this.form.deptIds = this.permissionList.map(dept => dept.deptId);
          this.form.userIds = this.permissionList.map(user => user.userId);
        })
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.expandUserIds();
      const currentDate = new Date();
      const formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')} ${currentDate.getHours().toString().padStart(2, '0')}:${currentDate.getMinutes().toString().padStart(2, '0')}:${currentDate.getSeconds().toString().padStart(2, '0')}`;
      this.form.id = this.currentid;
      this.form.publishTime = formattedDate;
      this.$refs["form"].validate(valid => {
        if (valid) {
            updatePublish(this.form).then(response => {
              this.$modal.msgSuccess("定稿成功");
              this.open = false;
              this.getList();
            });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const fileIds = row.fileId || this.ids;
      this.$modal.confirm('是否确认删除文件编号为"' + fileIds + '"的数据项？').then(function() {
        return delPublish(fileIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/publish/export', {
        ...this.queryParams
      }, `publish_${new Date().getTime()}.xlsx`)
    },
    /**  当前评阅记录变色 */
    rowClassName({ row }) {
      return row.isCurrent === 1 ? 'current-row' : '';
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
  }
};
</script>
<style>
.review-card .review-info {
  display: flex;
  justify-content: space-between; /* 子元素水平间距平均分布 */
  align-items: flex-start; /* 子元素垂直居中 */
  font-size: 16px;
}
.custom-text {
  font-size: 16px;
}
.publishtable{
  margin-top: 20px;
}
</style>
