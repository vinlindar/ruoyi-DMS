<template>
    <h3>文档统计</h3>
</template>
<script>
import { getdeptfilenum,getclassifiedfilenum,getyearfilenum,getmonthfilenum,getdetpdownloadfilenum,getclassifieddownloadfilenum } from "@/api/system/statistics"
export default {
  name: "Index",
  dicts: ['dms_file_type'],
  data() {
    return {
      // 部门树选项
      deptOptions: undefined,
      // 显示搜索条件
      showSearch: false,
      query:{},
    };
  },
  created() {
    this.getallnum();
    console.log(this)
  },
  mounted(){
    setTimeout(() => {
      this.renderPieChart()//饼图
    }, 500)
  },
  methods: {
    // 获得各个分类下的文件数
    getallnum() {
      this.loading = true;
      // 逐个请求各个统计数据
      Promise.all([
        getdeptfilenum(),
        getclassifiedfilenum(),
        getdetpdownloadfilenum(),
        getclassifieddownloadfilenum(),
        getyearfilenum(),
        getmonthfilenum()
      ]).then(responses => {
        this.deptfilenum = responses[0].rows;
        this.classifiedfilenum = responses[1].rows;
        this.detpdownloadfilenum = responses[2].rows;
        this.classifieddownloadfilenum = responses[3].rows;
        this.yearfilenum = responses[4].rows;
        this.monthfilenum = responses[5].rows;
        this.loading = false;
      }).catch(error => {
        console.error("Error fetching data:", error);
        this.loading = false;
      });
    },
  }
}
</script>