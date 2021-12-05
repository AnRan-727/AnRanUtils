<template>
  <div style="width: 80%;margin: 0 auto;">
    <div class="el-card-div">
      <el-card shadow="hover" border-radius='130'>
        <el-descriptions title="接口信息" :column="2" :colon="false" border>
          <el-descriptions-item label="接口名称：">
            {{ name }}
          </el-descriptions-item>
          <el-descriptions-item label="请求方式：">
            {{ request }}
          </el-descriptions-item>
          <el-descriptions-item label="请求地址：">
            {{ path }}
          </el-descriptions-item>
          <el-descriptions-item label="支持格式：">
            {{ format }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>

    <div class="el-card-div">
      <el-card shadow="hover" style="text-align: left">
        <span style="font-size: 16px;">接口参数</span>
        <el-table :data="paramData">
          <el-table-column prop="param" label="参数名称"></el-table-column>
          <el-table-column prop="check" label="是否必填"></el-table-column>
          <el-table-column prop="type" label="参数类型"></el-table-column>
          <el-table-column prop="name" label="参数说明"></el-table-column>
        </el-table>
      </el-card>
    </div>

    <div class="el-card-div">
      <el-card shadow="hover" style="text-align: left">
        <span style="font-size: 16px;">返回说明</span>
        <el-table :data="resultData">
          <el-table-column prop="col" label="字段名称"></el-table-column>
          <el-table-column prop="type" label="字段类型"></el-table-column>
          <el-table-column prop="dest" label="字段说明"></el-table-column>
        </el-table>
      </el-card>
    </div>

    <div class="el-card-div">
      <el-card shadow="hover" style="text-align: left">
        <span style="font-size: 16px;">测试运行</span>
        <el-form :model="formData" v-if="path === '/qrcode/url'" :inline="true" :rules="formRules" class="form-inline">
          <el-form-item label="图片链接" prop="url">
            <el-input v-model="formData.url" placeholder="图片链接"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
          </el-form-item>
        </el-form>
        <el-form :model="formData" v-if="path === '/qrcode/upload'" :inline="true" :rules="formRules" class="form-inline">
          <el-form-item label="选择二维码">
              <el-upload
                class="upload-demo"
                ref="imgFile"
                action=""
                name = 'file'
                accept=".JPG, .PNG, .JPEG,.jpg, .png, .jpeg"
                :auto-upload = false
                :limit=1
                :on-change="beforeUploadImg"
                :http-request="httpUpload"
              >
                <el-button type="primary"><i class="el-icon-upload"></i> 选择二维码图片</el-button>
                <div class="el-upload__tip" slot="tip">只能上传jpg/png/jpeg文件，且不超过3M</div>
              </el-upload>
            </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onUpload()">提交</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>

  </div>
</template>

<script>
export default {
  name: 'ApiInfo',
  data () {
    return {
      id: '',
      name: '',
      request: '',
      format: '',
      path: '',
      paramData: [],
      resultData: [],
      formData: {
        url: '',
        file: {}
      },
      formRules: {
        url: [
          {required: true, message: '请输入网络图片链接！', trigger: 'blur'}
        ]
      }
    }
  },
  created () {
    this.getParams()
  },
  methods: {
    onSubmit () {
      let _this = this
      let param = new URLSearchParams()

      for (let key in _this.formData) {
        param.append(key, _this.formData[key])
      }
      this.$axios.post('/qrcode/url', param).then(res => {
        this.$alert(res.data, '请求返回')
      })
    },
    getParams () {
      const id = this.$route.query.id
      this.$axios.get('/qrcode/getApiDetail', {
        params: {
          id: id
        }
      }).then(res => {
        const data = res.data.data
        this.id = data.id
        this.name = data.name
        this.request = data.request
        this.format = data.format
        this.path = data.path
        this.paramData = data.param
        this.resultData = data.result
      }).catch(reason => {
        this.$alert('请求失败！！！', '温馨提示')
      })
    },
    beforeUploadImg (file) {
      let types = ['image/jpeg', 'image/jpg', 'image/png', 'image/JPEG', 'image/PNG', 'image/JPG']
      const isImg = types.includes(file.raw.type)
      if (!isImg) {
        this.$message.error('上传图片只能是 JPG、JPEG、PNG 格式!')
        return false
      }
      const lsLtSize = file.raw.size / 1024 / 1024 < 3
      if (!lsLtSize) {
        this.$message.error('上传图片大小不能超过 3MB!')
        return false
      }
    },
    httpUpload (file) {
      let formParam = new FormData()
      formParam.append('file', file.file)

      this.formData.file = file.file
      this.$axios.post('/qrcode/upload', formParam, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
        this.$alert(res.data, '请求返回')
      })
    },
    onUpload () {
      this.$refs.imgFile.submit()
    }
  }
}
</script>

<style scoped>
h1 {
  font-weight: normal;
}

.el-card-div {
  margin: 10px;
}
</style>
