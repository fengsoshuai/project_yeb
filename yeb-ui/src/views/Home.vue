<template>
  <div>
    <el-container>
      <el-header class="homeHeader">
        <div class="title">云办公系统</div>
        <el-dropdown class="userInfo" @command="commandHandler">
          <span class="el-dropdown-link">
            {{currentUser.name}}
            <i><el-avatar size="small" :src="currentUser.userFace"></el-avatar></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
            <el-dropdown-item command="setting">设置</el-dropdown-item>
            <el-dropdown-item command="logout">注销登陆</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu unique-opened router>
            <el-submenu :index="index+''" v-for="(item, index) in routes"
                        v-if="!item.hidden" :key="index">
              <template slot="title"><i v-bind:class="item.iconCls" style="color: cornflowerblue; margin-right: 0.4rem;"></i>{{item.name}}</template>
                <el-menu-item :index="children.path" v-for="(children, jndex) in item.children" :key="jndex">{{children.name}}</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path !== '/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="homeContent" v-if="this.$router.currentRoute.path === '/home'">
            <div class="tips">
              欢迎来到云办公管理系统！
            </div>
            <el-calendar>
              <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
              <template
                  slot="dateCell"
                  slot-scope="{date, data}">
                <p :class="data.isSelected ? 'is-selected' : ''">
                  {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : ''}}
                </p>
              </template>
            </el-calendar>
          </div>
          <router-view class="homeRouterView"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Home",
  data(){
    return {
      // currentUser: JSON.parse(window.sessionStorage.getItem("currentUser"))
    }
  },
  computed: {
    routes(){
      return this.$store.state.routes
    },
    currentUser(){
      return this.$store.state.currentAdmin;
    }
  },
  methods: {
    // 退出登陆
    logoutCommand(){
      this.$confirm('此操作将注销当前登陆用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.post('/logout')
        window.sessionStorage.removeItem("currentUser")
        window.sessionStorage.removeItem("tokenStr")
        // 返回登陆页
        this.$router.replace('/')
        // 清空菜单
        this.$store.commit("initRoutes", []);
      }).catch(()=>{});
    }
    ,commandHandler(command){
      if(command === 'logout'){
        this.logoutCommand();
      }
    }
  }
}
</script>

<style scoped>
  .homeHeader {
    display: flex;
    align-items: center;
    background: #558CEFFF;
    justify-content: space-between;
    box-sizing: border-box;
    padding: 0 1.2rem;
  }

  .homeHeader .title {
    color: white;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    font-size: x-large;
  }
  .userInfo {
    display: flex;
    align-content: center;
  }
  .el-dropdown-link {
    display: flex;
    align-items: center;
    width: 106px;
    justify-content: space-between;
    color: white;
  }

  .homeContent .tips{
    text-align: center;
    font-size: 30px;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    color: #558CEFFF;
    margin-bottom: 15px;
    margin-top: 15px;
  }
  .is-selected {
    color: #1989FA;
  }
  .homeRouterView {
    margin-top: 20px;
  }
</style>