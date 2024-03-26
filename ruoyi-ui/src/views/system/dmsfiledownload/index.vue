<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            node-key="id"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--文件数据-->
      <el-col :span="20" :xs="24">
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
            <el-select v-model="queryParams.fileStatus" placeholder="仅管理员可操作" clearable:disabled="!isAdmin">
              <el-option
                v-for="dict in dict.type.dms_file_status"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
                :disabled="!isAdmin"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="归属团队" prop="belongteam">
            <treeselect 
              v-model="queryParams.belongteam"
              :options="deptOptions"
              :show-count="true"
              @select="handleSelect"
              placeholder="请输入归属团队"
            />
          </el-form-item>
          <el-form-item label="创建者" prop="updateBy">
            <el-input
              v-model="queryParams.updateBy"
              placeholder="请输入创建者"
              clearable
              @keyup.enter.native="handleQuery"
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
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleaddfavorite"
          >收藏文档</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

        <el-table v-loading="loading" :data="dmsfileuploadList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="文件ID" align="center" prop="fileId" />
          <el-table-column label="文件名" align="center" prop="fileName" />
          <el-table-column label="作者" align="center" prop="author" />
          <el-table-column label="审稿人" align="center" prop="reviewer" />
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
          <el-table-column label="创建者" align="center" prop="updateBy" />
          <el-table-column label="创建时间" align="center" prop="updateTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" >
            <template slot-scope="scope">
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
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listDmsfileupload} from "@/api/system/dmsfileupload";
import { listUserbypostId } from "@/api/system/user";
import { addRecords} from "@/api/system/records";
import {deptTreeSelect} from "@/api/system/dmsfileupload";
import {addFavorites} from "@/api/system/favorites";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dmsfiledownload",
  dicts: ['dms_file_type', 'dms_file_status'],
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
        fileStatus: 3,
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
    this.getPublishList();
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
        this.queryParams.belongteam = val.label
    },
    //新增收藏文件
    handleaddfavorite(){
      const currentDate = new Date();
      const formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')} ${currentDate.getHours().toString().padStart(2, '0')}:${currentDate.getMinutes().toString().padStart(2, '0')}:${currentDate.getSeconds().toString().padStart(2, '0')}`;
      this.favorite.userId=this.$store.state.user.id;
      this.favorite.fileId=this.ids[0];
      this.favorite.collectTime=formattedDate;
      addFavorites(this.favorite).then(response => {
              this.$modal.msgSuccess("收藏成功");});
    },
    /**  查询定稿人下拉列表 */
    getPublishList() {
      this.loading = true;
      const postID = 1;
      listUserbypostId(postID).then(response => {
          // 提取用户ID和用户名信息
          this.PublishList = response.data;
          this.loading = false;
        }
      );
    },
    getPublishNameById(userId) {
      const user = this.PublishList.find(user => user.userId === userId);
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
    handleDownload(row) {
      // 新增下载记录
      const currentDate = new Date();
      const formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')} ${currentDate.getHours().toString().padStart(2, '0')}:${currentDate.getMinutes().toString().padStart(2, '0')}:${currentDate.getSeconds().toString().padStart(2, '0')}`;
      // 查找belongteam对应的deptId
      const deptId = this.getIdByLabel(this.deptOptions, row.belongteam)
      this.downloadrecord_form = {
        fileId: row.fileId,
        fileName: row.fileName,
        deptId:deptId,
        belongteam: row.belongteam,
        downloadUserid: this.$store.state.user.id,
        downloadUser: this.$store.state.user.name,
        downloadTime: formattedDate 
      };
      addRecords(this.downloadrecord_form);

      var name = row.fileName;
      var url = row.filePath;
      var suffix = url.substring(url.lastIndexOf("."), url.length);
      const a = document.createElement('a')
      a.setAttribute('download', name + suffix)
      a.setAttribute('target', '_blank')
      a.setAttribute('href', url)
      a.click()
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
  }
};
</script>
<style>
.vue-treeselect{
    height: 22px;
    width: 220px;
}
</style>
