<template>
  <div>
    <el-page-header title="返回" :content="name+'战力'" @back="goBack">
    </el-page-header>
  </div>
</template>

<script>
export default {
  name: 'HeroGrade',
  data () {
    return {
      name: ''
    }
  },
  created () {
    const name = this.$route.query.name
    this.name = name
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    }
  }
}
</script>

<style scoped>

</style>
