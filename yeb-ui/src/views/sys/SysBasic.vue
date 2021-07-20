<template>
  <div>
    <el-tabs type="border-card" v-model="activeName">
      <el-tab-pane name="DepManage">
        <span slot="label"><i class="el-icon-ship"></i>部门管理</span>
        <DepManage></DepManage>
      </el-tab-pane>
      <el-tab-pane label="职位管理" name="PosManage">
        <PosManage></PosManage>
      </el-tab-pane>
      <el-tab-pane label="职称管理" name="JoblevelManage">
        <JoblevelManage></JoblevelManage>
      </el-tab-pane>
      <!--<el-tab-pane label="奖惩规则" name="EcManage">
        <EcManage></EcManage>
      </el-tab-pane>-->
      <el-tab-pane label="权限组" name="PermissionManage">
        <PermissionManage></PermissionManage>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

import DepManage from "@/components/sys/basic/DepManage";
// import EcManage from "@/components/sys/basic/EcManage";
import JoblevelManage from "@/components/sys/basic/JoblevelManage";
import PermissionManage from "@/components/sys/basic/PermissionManage";
import PosManage from "@/components/sys/basic/PosManage";

export default {
  name: "SysBasic",
  data(){
    return {
      // 当前显示标签
      activeName: 'DepManage'
    }
  },
  components: {
    DepManage, /*EcManage,*/ JoblevelManage, PermissionManage, PosManage
  }


}
</script>

<style scoped>

</style>