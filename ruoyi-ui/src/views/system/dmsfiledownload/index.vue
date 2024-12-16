<template>
  <div class="app-container2">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24" class="search-side">
        <h3><strong>按归属团队浏览</strong></h3>
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
        <h3><strong>按文档分类浏览</strong></h3>
        <div class="head-container">
          <el-tree
            :data="dict.type.dms_file_type"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            node-key="value"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick2"
          />
        </div>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-col>
    </el-row>
      <!--文件数据-->
    <el-row  :gutter="20" >
      <el-col class="custom-col">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="文件名或关键词" prop="fileName" label-width="150px !important">
            <el-input
              v-model="queryParams.fileName"
              placeholder="请输入文件名或关键词"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="提交人" prop="updateBy" label-width="110px">
            <el-input
              v-model="queryParams.updateBy"
              placeholder="请输入提交人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
<!--      <el-form-item label="定稿人" prop="publishId" label-width="110px">
            <el-select v-model="queryParams.publishId" placeholder="请选择定稿人" :multiple="false" clearable>
              <el-option 
                v-for="user in PublisherList" 
                :key="user.userId" 
                :label="user.userName" 
                :value="user.userId"
              />
            </el-select>
          </el-form-item> -->
          <el-form-item label="文件分类" prop="fileType" label-width="110px">
            <el-select v-model="queryParams.fileType" placeholder="请选择文件分类" clearable>
              <el-option
                v-for="dict in dict.type.dms_file_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="文件状态" prop="fileStatus" label-width="110px">
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
          <el-form-item label="归属团队" prop="belongteam" label-width="110px">
            <treeselect 
              v-model="form.deptId"
              :options="deptOptions"
              :show-count="true"
              @select="handleSelect"
              placeholder="请输入归属团队"
            />
          </el-form-item>
          <el-form-item label="发布时间" prop="publishTime" label-width="110px">
            <el-date-picker clearable
              v-model="queryParams.publishTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择发布时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            <!-- <el-button icon="el-icon-edit" size="mini" @click="handlemyseachesQuery">加入我的自定义</el-button> -->
            <el-button
                  type="primary"
                  plain
                  icon="el-icon-edit"
                  size="mini"
                  :disabled="single"
                  @click="handleaddfavorite"
                >收藏文档</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="dmsfileuploadList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="文件名" header-align="center" align="left" prop="fileName" width="600px" show-overflow-tooltip>
            <template slot-scope="scope">
              <router-link :to="'/file/filedetail/' + scope.row.fileId" class="link-type">
                <span class="file-name">{{ scope.row.fileName }}</span>
              </router-link>
            </template>
          </el-table-column>
          <el-table-column label="文件分类" align="center" prop="fileType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.dms_file_type" :value="scope.row.fileType"/>
            </template>
          </el-table-column>
          <el-table-column label="归属团队" align="center" prop="belongteam" width="200"/>
          <el-table-column label="提交人" align="center" prop="updateBy" />
          <el-table-column label="定稿人" align="center">
            <template slot-scope="scope">
              {{ getPublishNameById(scope.row.publishId) }}
            </template>
          </el-table-column>
<!--           <el-table-column label="文件大小" align="center" prop="fileSize" /> -->
          <el-table-column label="发布时间" align="center" prop="publishTime" width="130">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
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
import {addSearches} from "@/api/system/searches";
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
      // 定稿人列表
      PublisherList: undefined,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文件信息表格数据
      dmsfileuploadList: [],
      //判断是否可以下载数据
      accesslist :[],
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
        //区分文档浏览的查询(1.权限浏览查询；2.无权限浏览查询；其余.无权限上传查询) 
        querykind: 2,
        // 用户权限控制需要
        queryuserId:this.$store.state.user.id,
        queryuserDept:null,
      },
      queryParams2: {
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
    this.getPublisherList();
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
    //新增用户自定义文件查询条件
    handlemyseachesQuery(){
      this.queryParams.userId=this.$store.state.user.id;
      addSearches(this.queryParams).then(response => {
              this.$modal.msgSuccess("新增成功");
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
    getPublishNameById(userId) {
      const user = this.PublisherList.find(user => user.userId == userId);
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
        publishId:null,
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
      this.resetForm("queryForm");
      this.queryParams.belongteam = data.label;
      this.handleQuery();
    },
    // 节点单击事件2
    handleNodeClick2(data) {
      this.resetForm("queryForm");
      this.queryParams.fileType = data.value;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.fileId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleDownload(row) {
      // 判断用户是否有下载权限
      this.queryParams2.fileId = row.fileId;
      this.loading = true;
      listDmsfileupload(this.queryParams2).then(response => {
          this.accesslist = response.rows;
          this.loading = false;
          // 如果返回结果为 null，则提示用户无下载权限
          if (!response.rows || response.rows.length === 0) {
            this.$message.error('无下载权限');
            return;
          }
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
    }).catch(() => {
        this.loading = false;
        this.$message.error('下载文件出错，请稍后重试');
      });
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
    width: 200px;
}
.el-col-4{
width:250px;
}
.app-container2{
  display: flex;
}
.search-side{
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content:flex-start;
  align-items: center;
  height: 100%; /* Ensure the col takes the full height of its parent */
  text-align: center;
}
.head-container {
  width: 100%; /* Ensure the content takes the full width of its container */
  text-align: left; /* Reset text alignment for the content if needed */
}
.el-tree-node__label{
  font-size: 16px; /* Adjust the size as needed */
}
.custom-col{
  margin-top: 30px;
}
</style>
