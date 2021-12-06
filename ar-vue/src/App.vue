<template>
  <div id='app'>
    <div class="layout">
      <el-container>
        <!-- 顶部 -->
        <el-header>
          Api工具箱
        </el-header>
        <!-- 内容 -->
        <el-main>
          <router-view></router-view>
        </el-main>
        <!-- 页脚 -->
        <el-footer>
          <div style="margin-top: 20px;">
            <span>Copyright © 2021 Api工具箱</span>
          </div>
        </el-footer>
      </el-container>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
}

</script>

<style>

.el-header {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  text-align: center;
  font-size: 20px;
  line-height: 60px;
}

.el-main {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  text-align: center;
  font-size: 20px;

}

.el-footer {
  align-content: center;
  text-align: center;
  font-size: 10px;
}

.layout {
  font-family: 'FangSong', Georgia, '黑体', 'KaiTi', 'DFKai-SB', sans-serif;
  font-weight: bold;
  line-height: 30px;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.el-container {
  height: 100%;
}

html, body, #app {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
}
</style>
