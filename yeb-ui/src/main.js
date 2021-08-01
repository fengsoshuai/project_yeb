import Vue from 'vue'
import App from './App.vue'
import router from './router'

import store from './store'

// 引入 element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
// 使用 element-ui
Vue.use(ElementUI)

// 以插件形式使用 api.js 中的 axios 请求
import {postRequest, getRequest, putRequest, deleteRequest} from "@/utils/api";
import {post, get, put, del} from "@/utils/api";
import {initMenu} from "@/utils/meuns";
import {downLoadRequest} from "@/utils/download";

import 'font-awesome/css/font-awesome.css'

Vue.prototype.postRequest = postRequest
Vue.prototype.getRequest = getRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.deleteRequest = deleteRequest
Vue.prototype.downLoadRequest = downLoadRequest

Vue.prototype.post = post
Vue.prototype.get = get
Vue.prototype.put = put
Vue.prototype.dele = del

// 全局前置导航守卫
router.beforeEach((to, from, next)=>{
  // 用户已经登陆
  if(window.sessionStorage.getItem("tokenStr")){
    initMenu(router, store);
    next();
    // 获取当前登陆用户信息
    if(!window.sessionStorage.getItem("currentUser")){
      return getRequest('/admin/info').then(resp => {
        if(resp){
          store.commit("initAdmin", resp);
          window.sessionStorage.setItem("currentUser", JSON.stringify(resp))
          next();
        }
        next();
      })
    }
    next();
  } else {
    if(to.path === '/'){
      next();
    } else {
      next('/?redirect=' + to.path);
    }
  }
  next()
})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
