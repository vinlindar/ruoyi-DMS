<template>
  <div>
    <!-- 文件预览 -->
    <div v-if="previewUrl" class="preview-container">
      <div v-if="isLoading" class="loading-overlay">
        <div class="loading-spinner"></div>
        <p class="loading-text">正在加载...</p>
      </div>
      <iframe :src="previewUrl" style="width: 100%; height: 100vh;" frameborder="0" @load="onIframeLoad"></iframe>
    </div>

    <!-- 文件加载失败的提示 -->
    <div v-if="!previewUrl" class="no-preview">
      <p>预览文件失败，请稍后再试。</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      previewUrl: this.$route.query.previewUrl || '',  // 使用传递的 previewUrl 作为初始值
      isLoading: true,
    };
  },
  mounted() {
  // 确认参数是否正确传递
  },
  methods: {
    // 处理iframe加载完成事件
    onIframeLoad() {
      this.isLoading = false;  // 文件加载完成后隐藏加载提示
    },
  }
};
</script>

<style scoped>
.preview-container {
  margin-top: 20px;
  position: relative;
}

.no-preview {
  margin-top: 20px;
  text-align: center;
  font-size: 18px;
  color: #888;
}
.loading-overlay{
  position: absolute;
  top: 0px;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
}
.loading-spinner{
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 2s linear infinite;
}
.loading-text {
  margin-top: 10px;
  font-size: 16px;
  color: #555;
  font-weight: 600;
}
</style>
