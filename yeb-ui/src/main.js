import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 引入 element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
// 使用 element-ui
Vue.use(ElementUI)

// 以插件形式使用 api.js 中的 axios 请求
import {postRequest, getRequest, putRequest, deleteRequest} from "@/utils/api";
Vue.prototype.postRequest = postRequest
Vue.prototype.getRequest = getRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.deleteRequest = deleteRequest

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
