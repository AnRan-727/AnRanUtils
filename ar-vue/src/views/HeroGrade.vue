<template>
  <div>
    <el-page-header title="返回" :content="name+' 战力'" @back="goBack">
    </el-page-header>
    <div style="max-width: 700px; margin: 0 auto;" v-loading="loading">
      <el-card class="box-card" shadow="hover" v-for="(v,k) in heroGrade" :key="k" body-style="padding: 0px;border-radius：50px;">
        <div slot="header" class="clearfix">
          <span>{{ k }}</span>
        </div>
        <div class="text item">
          <el-table
            :data="v"
            border
            style="width: 100%">
            <el-table-column
              prop="grade"
              label="级别"
              width="70"
            >
            </el-table-column>
            <el-table-column
              prop="region"
              label="地区">
            </el-table-column>
            <el-table-column
              prop="points"
              label="分数"
              width="70"
            >
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeroGrade',
  data () {
    return {
      name: '',
      heroGrade: null,
      loading: true
    }
  },
  created () {
    this.getHeroGrade()
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    getHeroGrade () {
      const _this = this
      const name = _this.$route.query.name
      _this.name = name
      _this.$axios.get('/hero/getHeroGrade', {
        params: {
          name: name
        }
      }).then(res => {
        _this.heroGrade = res.data.data
        _this.loading = false
      }).catch(resp => {
        _this.loading = false
        this.$alert('获取英雄战力失败！', '提示')
      })
    }
  }
}
</script>

<style scoped>
.box-card {
  width: 300px;
  margin: 15px;
  display: inline-block;
}
.clearfix:after {
  clear: both
}
</style>
