import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login'
import Home from '@/views/Home'
import EmpBasic from "@/views/emp/EmpBasic";
import SysAdmin from "@/views/sys/SysAdmin";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    hidden: true
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/sys',
        name: 'SysAdmin',
        component: SysAdmin
      },
      {
        path: '/emp',
        name: 'EmpBasic',
        component: EmpBasic
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
