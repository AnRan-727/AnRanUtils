import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    menuList: [],
    permList: []
  },
  mutations: {
    setMenuList (state, menus) {
      state.menuList = menus
    },
    setPermList (state, perms) {
      state.permList = perms
    }
  },
  actions: {

  }
})
