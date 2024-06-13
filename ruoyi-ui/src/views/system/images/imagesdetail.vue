<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="imagesList" width="100%" >
      <el-table-column label="新闻标题" align="center" prop="title" width="400" show-overflow-tooltip/>
      <el-table-column label="详细描述" align="center" prop="description"  width="600"/>
      <el-table-column label="图片预览" align="center" prop="path" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.path" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="新闻时间" align="center" prop="creatTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.creatTime, '{y}-{m}-{d}') }}</span>
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

<script>
import { listImages} from "@/api/system/images";

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
      form: {},
      // 表单校验
      rules: {
      }
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
    getImageUrl(path) {
      // 返回完整的图片URL，确保路径正确
      return process.env.VUE_APP_BASE_API + path;
    },
  }
};
</script>
