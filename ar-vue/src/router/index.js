import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import HeroList from '@/views/HeroList'
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
      path: '/heroList',
      component: HeroList
    }
  ]
})
