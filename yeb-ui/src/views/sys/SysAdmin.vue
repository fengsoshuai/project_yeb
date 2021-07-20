<template>
  <div>
    <!--搜索-->
    <div class="adminSearch">
      <el-input v-model="keywords" size="small" prefix-icon="el-icon-search" placeholder="请输入管理员的名字..." class="adminSearchInput"></el-input>
      <el-button size="small" type="primary" icon="el-icon-search" @click="doSearch">搜索</el-button>
    </div>

    <div class="admin-card-container">
      <div v-if="this.adminList.length === 0">
        <el-empty :image-size="400" description="没有搜索到数据！"></el-empty>
      </div>
      <el-card class="admin-card" v-for="(admin, index) in adminList" :key="index">
        <div slot="header" class="clearfix">
          <span class="admin-card-title">{{admin.name}}</span>
          <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-delete" class="remove-button" @click="removeAdmin(admin.id)"></el-button>
        </div>
        <div class="">
          <!--头像-->
          <div class="admin-avatar-container">
            <img class="admin-avatar" :src="admin.userFace" :title="admin.name" :alt="admin.name"/>
          </div>

          <!--基本信息-->
          <div class="admin-info">
            <div class="admin-info-div">用户名： {{admin.name}}</div>
            <div class="admin-info-div">手机号码： {{admin.phone}}</div>
            <div class="admin-info-div">电话号码： {{admin.telephone}}</div>
            <div class="admin-info-div">地址： {{admin.address}}</div>
            <div class="admin-info-stat">用户状态：
              <el-switch
                  v-model="admin.enabled"
                  active-color="#13ce66"
                  inactive-color="red"
                  active-text="启用"
                  inactive-text="禁用">
              </el-switch>
            </div>
            <div class="admin-info-role">用户角色：
              <el-tag class="admin-role-tag" size="small" v-for="(role, ind) in admin.roles" :key="ind">{{role.nameZh}}</el-tag>
            </div>
            <!--备注-->
            <div class="admin-info-remark">
              备注：{{admin.remark}}
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "SysAdmin",
  data(){
    return {
      keywords: '',
      adminList: []
    }
  },
  methods: {
    // 搜索
    doSearch(){
      this.initAdminList()
    },
    // 初始化数据
    initAdminList(){
      this.getRequest('/system/admin/?keywords=' + this.keywords).then(resp => {
        if(resp){
          this.adminList = resp;
        }
      })
    },
    // 删除管理员
    removeAdmin(adminId){

    }
  },

  mounted() {
    this.initAdminList()
  }
}
</script>

<style scoped>
.adminSearch {
  display: flex;
  justify-content: center;
  margin-top: 15px;
}
.adminSearchInput {
  width: 300px;
  margin-right: 10px;
}
.admin-card {
  width: 370px;
  margin-top: 20px;
}
.admin-card-container {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin-top: 20px;
}
.remove-button {
  color: red;
}
.admin-avatar {
  width: 72px;
  height: 72px;
  border-radius: 72px;
}
.admin-avatar-container {
  display: flex;
  justify-content: center;
}
.admin-info {
  font-size: 14px;
  margin-top: 12px;
}
.el-switch {
  font-size: small;
}
.admin-role-tag {
  margin: 0 6px 6px 0;
}
.admin-info-div, .admin-info-stat, .admin-info-remark {
  margin: 12px 0;
}
.admin-info-stat {
  display: flex;
  align-items: center;
}
</style>