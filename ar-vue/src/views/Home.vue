<template>
  <div class="layout">
    <el-container>
      <!-- 顶部 -->
      <el-header>
        Api工具箱
      </el-header>
      <!-- 内容 -->
      <el-main>
        <el-card class="box-card">
          <div v-for="o in 2" :key="o" class="text item">
            {{'列表内容 ' + o }}
          </div>
        </el-card>
      </el-main>
      <!-- 页脚 -->
      <el-footer>
        <span>Copyright © 2021 Api工具箱</span>
      </el-footer>
    </el-container>
  </div>
</template>

<script>

export default {
  name: 'Home'
}
</script>

<style scoped>
.el-header{
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  text-align: center;
  font-size: 20px;
  line-height: 60px;
}
.el-main{
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  text-align: center;
  font-size: 20px;
}
.el-footer{
  align-content: center;
  text-align: center;
  font-size: 10px;
  padding-top: 20px;
}
.layout{
  position:absolute;
  top:0;
  left:0;
  width: 100%;
  height:100%;
}
.el-container{
  height:100%;
}
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 200px;
}
</style>
