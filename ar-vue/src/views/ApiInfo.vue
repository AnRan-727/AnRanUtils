<template>
  <div>
    <h1>
      跳转成功、这是api接口信息页面
      <br>
      ID: {{ id }}
    </h1>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="date" label="日期"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'ApiInfo',
  data () {
    return {
      id: '',
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }]
    }
  },
  created () {
    this.getParams()
  },
  methods: {
    getParams () {
      const id = this.$route.query.id
      this.$axios.get('/qrcode/getApiDetail', {
        params: {
          id: id
        }
      }).then(res => {
        console.log(res.data.data)
        this.id = res.data.data.id
      })
    }
  }
}
</script>

<style scoped>
h1 {
  font-weight: normal;
}
</style>
