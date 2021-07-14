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
          <router-view/>
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
</style>