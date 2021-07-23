<template>
  <div>
    <div class="emp-search">
      <el-input class="emp-search-input" size="small" prefix-icon="el-icon-search" v-model="empName" placeholder="请输入员工名进行搜索..."></el-input>
      <el-button class="emp-search-button" size="small" type="primary" icon="el-icon-search" @click="searchEmp">搜索</el-button>
      <el-button type="primary" size="small">
        <i class="fa fa-angle-double-down" aria-hidden="true"></i>
        高级搜索
      </el-button>
    </div>

    <div></div>
  </div>
</template>

<script>
export default {
  name: "EmpBasic",
  data(){
    return {
      empName: ''
    }
  },

  methods: {
    searchEmp(){},

    initEmpList(){}
  },
  mounted() {
    this.initEmpList()
  }
}
</script>

<style scoped>
.emp-search-input {
  width: 400px;
}
.emp-search {
  display: flex;
  width: 485px;
  justify-content: space-between;
}
</style>