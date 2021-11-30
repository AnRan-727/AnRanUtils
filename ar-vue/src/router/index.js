import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Test from '@/views/Test'
import ApiList from '@/views/ApiList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/',
      name: 'ApiList',
      component: ApiList
    },
    {
      path: '/',
      name: 'Test',
      component: Test
    }
  ]
})
