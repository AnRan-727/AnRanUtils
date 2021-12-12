import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import HeroList from '@/views/HeroList'
import HeroGrade from '@/views/HeroGrade'
import ApiInfo from '@/views/ApiInfo'
import Login from '@/views/Login'
import Main from '@/views/Main'
import Index from '@/views/Index'
import User from '@/views/sys/User'
import Role from '@/views/sys/Role'
import Menu from '@/views/sys/Menu'

import axios from 'axios'
import store from '../store'

Vue.use(Router)

const routes = [
  {
    path: '/',
    component: Login
  },
  {
    path: '/main',
    component: Main,
    children: [
      {
        path: '/index',
        name: 'Index',
        component: Index
      },
      {
        path: '/sys/users',
        name: 'SysUsers',
        component: User
      },
      {
        path: '/sys/userCenter',
        name: 'UserCenter',
        component: () => import('@/views/sys/UserCenter')
      },
      {
        path: '/sys/roles',
        name: 'SysRole',
        component: Role
      },
      {
        path: '/sys/menus',
        name: 'SysMenu',
        component: Menu
      }
    ]
  },
  {
    path: '/home',
    component: Home
  },
  {
    path: '/apiInfo',
    component: ApiInfo
  },
  {
    path: '/heroList',
    component: HeroList
  },
  {
    path: '/heroGrade',
    component: HeroGrade
  }
]

const router = new Router({
  mode: 'history',
  base: process.env.BABEL_ENV,
  routes
})

router.beforeEach((to, from, next) => {
  axios.get('/sys/menu/nav', {
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(res => {
    console.log(res.data.data)
    // 拿到menuList 路由权限
    // TODO set不进去菜单
    store.commit('setMenuList', res.data.data.nav)
    // 拿到用户权限 操作权限
    store.commit('setPermList', res.data.data.authorities)
    console.log(store.state.menus.menuList)
  })
  next()
})

export default router
