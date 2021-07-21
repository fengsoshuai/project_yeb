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
          <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-delete" class="remove-button" @click="removeAdmin(admin)"></el-button>
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
                  @change="enabledChange(admin)"
                  inactive-text="禁用">
              </el-switch>
            </div>
            <div class="admin-info-role">用户角色：
              <el-tag class="admin-role-tag" size="small" v-for="(role, ind) in admin.roles" :key="ind">{{role.nameZh}}</el-tag>
              <el-popover
                  placement="right"
                  title="角色列表"
                  width="200"
                  trigger="click"
                  @show="showPopRoles(admin)"
                  @hide="hidePopRoles(admin)"
                  content="">
                <el-select v-model="selectedRoles" placeholder="请选择..." multiple>
                  <el-option
                      v-for="(role, index) in allRoles"
                      :key="index"
                      :label="role.nameZh"
                      :value="role.id">
                  </el-option>
                </el-select>
                <el-button slot="reference" type="text" icon="el-icon-more"></el-button>
              </el-popover>
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
      adminList: [],
      allRoles: [],
      selectedRoles: []
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
    removeAdmin(admin){
      this.$confirm('此操作将永久删除管理员【'+admin.name+'】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/admin/' + admin.id).then(resp => {
          if(resp && resp.code === 200){
            this.initAdminList();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 更改启用状态
    enabledChange(admin){
      let instance = {id: admin.id, enabled: admin.enabled}
      this.put('/system/admin/', instance)
    },
    initAllRoles(){
      this.getRequest('/system/admin/roles').then(resp => {
        if(resp.code === 200){
          this.allRoles = resp.object;
        }
      })
    },
    // 点击显示角色
    showPopRoles(admin){
      this.initAllRoles()
      // 初始化
      this.selectedRoles = []
      let roles = admin.roles
      this.selectedRoles = roles.map(role => role.id)
    },
    // 点击隐藏角色：包含更新角色
    hidePopRoles(admin){
      let isUpdateRole = false
      let roles = []
      Object.assign(roles, admin.roles)
      // 角色个数发生变化
      if(roles.length !== this.selectedRoles.length){
        isUpdateRole = true
      } else {
        let ids = admin.roles.map(role => role.id).sort(_ => _).toString()
        let idsTemp = this.selectedRoles.sort(_ => _).toString();
        isUpdateRole = ids !== idsTemp
      }

      // 角色发生变化
      if(isUpdateRole){
        let rids = "";
        this.selectedRoles.forEach(rid => rids += '&rids=' + rid);
        this.putRequest('/system/admin/role?adminId=' + admin.id + rids).then(resp => {
          if(resp.code === 200){
            this.initAdminList()
          }
        })
      }
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