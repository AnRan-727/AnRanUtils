<template>
  <div>
    <el-card class="box-card" shadow="hover" body-style="border-radius：50px;" v-for="(v,i) in dataArray" :key="i" @click.native="toApiInfo(dataArray[i],$event)">
      <div class="text item">
        {{ dataArray[i].name }}
        <br>
        {{ dataArray[i].path }}
      </div>
    </el-card>
    <el-pagination
      @size-change=handleSizeChange
      @current-change=page
      layout="total, sizes, prev, pager, next, jumper"
      :current-page="currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data () {
    return {
      dataArray: [],
      currentPage: 1,
      total: 0,
      pageSize: 5
    }
  },
  created () {
    this.page(1)
  },
  methods: {
    toApiInfo (v, e) {
      this.$router.push({path: v.view, query: {id: v.id}})
    },
    page (currentPage) {
      this.getApiList(currentPage, this.pageSize)
    },
    handleSizeChange (size) {
      this.getApiList(this.currentPage, size)
    },
    getApiList (page, pageSize) {
      const _this = this
      _this.$axios.get('/getApiList', {
        params: {
          page: page,
          pageSize: pageSize
        }
      }).then(res => {
        _this.dataArray = res.data.data.list
        _this.currentPage = res.data.data.pageNum
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.pageSize
      }).catch(resp => {
        _this.$alert('获取Api列表失败！！！', '温馨提示')
      })
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
