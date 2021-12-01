import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Test from '@/views/Test'
import ApiInfo from '@/views/ApiInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Home
    },
    {
      path: '/apiInfo',
      component: ApiInfo
    },
    {
      path: '/test',
      component: Test
    }
  ]
})
