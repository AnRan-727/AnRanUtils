<template>
  <div>
    <el-page-header title="返回" content="王者荣耀" @back="goBack">
    </el-page-header>
    <br>
    <div>
      <el-input
        placeholder="英雄类型"
        prefix-icon="el-icon-search"
        style="max-width: 205px;margin-bottom: 5px;"
        @input="onTypeInput"
        v-model="type">
      </el-input>
      <el-input
        placeholder="英雄名称"
        prefix-icon="el-icon-search"
        style="max-width: 205px"
        @input="onNameInput"
        v-model="name">
      </el-input>
    </div>
    <el-card class="box-card" shadow="hover" body-style="border-radius：50px;" v-for="(v,i) in heroData" :key="i" @click.native="toHeroGrade(v.title)">
      <div class="text item">
        <span style="font-size:10px;">{{ v.title }}</span>
        <br>
        <el-avatar shape="square" :src="v.faceimg"></el-avatar>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'HeroList',
  data () {
    return {
      name: '',
      type: '',
      heroData: []
    }
  },
  created () {
    this.getHeroList()
  },
  methods: {
    getHeroList () {
      const _this = this
      _this.$axios.get('/hero/getHeroList').then(res => {
        _this.heroData = res.data.data
      }).catch(resp => {
        _this.$alert('请求失败！！！', '温馨提示')
      })
    },
    onNameInput () {
      const _this = this
      _this.$axios.get('/hero/getHeroByName', {
        params: {
          name: _this.name
        }
      }).then(res => {
        _this.heroData = res.data.data
      }).catch(resp => {
        _this.$alert('请求失败！！！', '温馨提示')
      })
    },
    onTypeInput () {
      const _this = this
      _this.$axios.get('/hero/getHeroByType', {
        params: {
          type: _this.type
        }
      }).then(res => {
        _this.heroData = res.data.data
      }).catch(resp => {
        _this.$alert('请求失败！！！', '温馨提示')
      })
    },
    goBack () {
      this.$router.go(-1)
    },
    toHeroGrade (v) {
      const _this = this
      _this.$router.push({path: '/heroGrade', query: {name: v}})
    }
  }
}
</script>

<style scoped>

.item {
  margin-top: -20px;
  margin-bottom: -10px;
}

.box-card {
  width: 90px;
  display: inline-block;
  margin: 10px;
}
</style>
