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

Vue.prototype.postRequest = postRequest
Vue.prototype.getRequest = getRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.deleteRequest = deleteRequest

Vue.prototype.post = post
Vue.prototype.get = get
Vue.prototype.put = put
Vue.prototype.del = del

// 全局前置导航守卫
router.beforeEach((to, from, next)=>{
  if (to.path==='/'){
    next()
  } else {
    initMenu(router,store);
    next();
  }
})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
