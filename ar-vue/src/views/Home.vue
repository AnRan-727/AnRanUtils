<template>
  <div>
    <el-card class="box-card" shadow="hover" body-style="border-radius：50px;" v-for="(v,i) in dataArray" :key="i" @click.native="toApiInfo(dataArray[i],$event)">
      <div class="text item">
        {{ dataArray[i].name }}
        <br>
        {{ dataArray[i].path }}
      </div>
    </el-card>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data () {
    return {
      dataArray: []
    }
  },
  created () {
    this.$axios.get('/qrcode/getApiList').then(res => {
      this.dataArray = res.data.data
    }).catch(resp => {
      this.$alert('获取Api列表失败！！！', '温馨提示')
    })
  },
  methods: {
    toApiInfo (v, e) {
      this.$router.push({path: v.view, query: {id: v.id}})
    }
  }
}
</script>

<style scoped>

.text {
  font-size: 14px
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 150px;
  display: inline-block;
  margin: 10px;
}
</style>
