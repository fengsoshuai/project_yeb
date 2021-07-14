import Vue from "vue";
import Vuex from 'vuex'

Vue.use(Vuex)
/**
 * Vuex 存储路由
 */
export default new Vuex.Store({
    state: {
        routes: [],
        currentAdmin: JSON.parse(window.sessionStorage.getItem('currentUser'))
    },

    // 同步修改 state
    mutations: {
        initRoutes(state, data){
            state.routes = data
        },
        initAdmin(state, admin){
            state.currentAdmin = admin;
        }
    },

    // 异步修改 state
    actions: {}
})