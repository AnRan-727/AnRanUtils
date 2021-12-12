const Mock = require('mockjs')

const Random = Mock.Random

let Result = {
  code: 200,
  msg: '操作成功！',
  data: null
}

Mock.mock('/captcha', 'get', () => {
  Result.data = {
    token: Random.string(32),
    captchaImg: Random.dataImage('120x40', 'p7n5w')
  }
  return Result
})

Mock.mock('/login', 'post', () => {
/*  Result.code = 400
  Result.msg = '验证码错误' */
  return Result
})

Mock.mock('/sys/userInfo', 'get', () => {
  Result.data = {
    id: '1',
    username: 'test',
    avatar: 'http://thirdwx.qlogo.cn/mmopen/Q3auHgzwzM5XuAjKNnOJlna9pic7FwCVflhzmHxcWN5ticGZkXXbq8md8NSRGFdXh4eVc0uzTkMw1C9Fc8WAThia2zBPyibniaxTtnOSNNY7T0N8/132?t=22:40:58'
  }
  return Result
})

Mock.mock('/logout', 'post', () => {
  return Result
})

Mock.mock('/sys/menu/nav', 'get', () => {
  let nav = [
    {
      name: 'SysManga',
      title: '系统管理',
      icon: 'el-icon-s-operation',
      component: '',
      path: '',
      children: [
        {
          name: 'SysUser',
          title: '用户管理',
          icon: 'el-icon-s-custom',
          path: '/sys/users',
          component: 'sys/User',
          children: []
        }
      ]
    },
    {
      name: 'SysTools',
      title: '系统工具',
      icon: 'el-icon-s-tools',
      component: '',
      path: '',
      children: [
        {
          name: 'SysDict',
          title: '数字字典',
          icon: 'el-icon-s-order',
          component: '',
          path: '/sys/dicts',
          children: []
        }
      ]
    }
  ]
  let authorities = []
  Result.data = {
    nav: nav,
    authorities: authorities
  }
  return Result
})
