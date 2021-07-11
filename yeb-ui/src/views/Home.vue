<template>
  <div>
    <el-container>
      <el-header>云办公系统</el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu unique-opened router>
            <el-submenu index="1" v-for="(item, index) in routes"
                        v-if="!item.hidden" :key="index">
              <template slot="title"><i class="el-icon-location"></i>{{item.name}}</template>
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
  computed: {
    routes(){
      return this.$store.state.routes
    }
  }
}
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}
</style>