<template>
  <div>
    <el-table
      :data="tableData"
      style="margin: 0 auto;width: 90%"
      :default-sort = "{prop: 'heroid', order: 'descending'}"
    >
      <el-table-column
        prop="title"
        label="英雄名称"
        width="150"
        sortable>
      </el-table-column>
      <el-table-column
        prop="pinyin"
        label="拼音"
        width="150"
        sortable>
      </el-table-column>
      <el-table-column
        prop="faceimg"
        width="150"
        label="英雄头像">
        <template slot-scope="scope">
          <img :src="scope.row.faceimg">
        </template>
      </el-table-column>
      <el-table-column
        prop="occupation"
        label="类型"
        width="150"
        sortable>
      </el-table-column>
      <el-table-column
        prop="infourl"
        label="详情">
      </el-table-column>
    </el-table>
    <br>
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
  name: 'HeroList',
  data () {
    return {
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 5
    }
  },
  created () {
    this.getHeroList()
  },
  methods: {
    getHeroList (page, pageSize) {
      const _this = this
      _this.$axios.get('/hero/getHeroList', {
        params: {
          page: page,
          pageSize: pageSize
        }
      }).then(res => {
        _this.tableData = res.data.data.list
        _this.currentPage = res.data.data.pageNum
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.pageSize
      }).catch(resp => {
        _this.$alert('请求失败！！！', '温馨提示')
      })
    },
    page (currentPage) {
      this.getHeroList(currentPage, this.pageSize)
    },
    handleSizeChange (size) {
      this.getHeroList(this.currentPage, size)
    }
  }
}
</script>

<style scoped>

</style>
