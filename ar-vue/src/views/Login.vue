<template>
  <el-row type="flex" class="row-bg" justify="center">
    <el-col :xl="6" :lg="7">
      <h3>AnRanUtils工具箱</h3>
      <el-image style="width: 250px;height: 200px;" src="https://3zi.cn/wp-content/uploads/2021/09/cbcee75a9403c5107457878fed4b563c.gif"/>
      <p style="font-size: 15px;font-weight: bold">
        用户名：admin 密码：admin
      </p>
    </el-col>
    <el-col :span="1">
      <el-divider direction="vertical"/>
    </el-col>
    <el-col :span="6">
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px">
        <el-form-item label="用户名" prop="username" style="width: 380px;">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" style="width: 380px;">
          <el-input v-model="loginForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code" style="width: 380px;">
          <el-input v-model="loginForm.code" style="width: 152px;float: left;"></el-input>
          <el-image :src="captchaImg" class="captchaImg"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginForm')">立即创建</el-button>
          <el-button @click="resetForm('loginForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: 'admin',
        password: 'admin',
        code: '12',
        token: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'}
        ]
      },
      captchaImg: null
    }
  },
  created () {
    this.getCaptcha()
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/login', this.loginForm).then(res => {
            const jwt = res.headers['authorization']
            this.$store.commit('SET_TOKEN', jwt)
            this.$router.push('/main')
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    getCaptcha () {
      this.$axios.get('/captcha').then(res => {
        this.loginForm.token = res.data.data.token
        this.captchaImg = res.data.data.captchaImg
      })
    }
  }
}
</script>

<style scoped>

.el-row {
  background-color: #FEFEFE;
  height: 100%;
  display: flex;
  align-items: center;
  text-align: center;
}

.el-divider {
  height: 300px;
}

.captchaImg {
  float: left;
  margin-left: 8px;
  border-radius: 5px;
}

</style>
