<template>
  <div>
    <!--添加权限-->
    <div class="addPermission">
      <el-input size="small" placeholder="请输入角色英文名..." v-model="permissionInputValue.name" class="addPermissionInput">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input size="small" placeholder="请输入角色中文名..." v-model="permissionInputValue.nameZh" class="addPermissionInput" @keydown="addPermission"></el-input>
      <!--添加按钮-->
      <el-button size="small" icon="el-icon-plus" type="primary" @click="addPermission">添加</el-button>
    </div>

    <!--权限列表-->
    <div class="mainCollapse">
      <el-collapse accordion v-model="activeName" @change="changeCollapse">
        <el-collapse-item :title="role.nameZh" :key="index" :name="role.id" v-for="(role,index) in roles">
          <el-card class="box-card" shadow="hover">
            <div slot="header" class="clearfix">
              <span>可访问资源</span>
              <el-button style="float: right;padding: 3px 0; color: #f11f4a" type="text" icon="el-icon-delete" @click="doDelete(role)"></el-button>
            </div>
            <!--树形展示权限对应的菜单-->
            <div>
              <el-tree :data="treeMenus" :props="treeMenusProps"
                       node-key="id"
                       :default-checked-keys="selectedMenus"
                       :default-expanded-keys="selectedMenus"
                       ref="tree"
                       :key="index"
                       show-checkbox>
              </el-tree>
              <div class="treeOperationButton">
                <el-button size="mini" @click="cancelEdit">取消修改</el-button>
                <el-button size="mini" type="primary" @click="doUpdate(role.id, index)">确认修改</el-button>
              </div>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "PermissionManage",
  data(){
    return {
      permissionInputValue: {
        name : '', nameZh : ''
      },
      activeName: '-1',
      roles: [],
      treeMenus: [],
      treeMenusProps: {
        children: 'children',
        label: 'name'
      },
      selectedMenus: []
    }
  },
  methods: {
    // 添加权限
    addPermission(){
      if(this.permissionInputValue.name && this.permissionInputValue.nameZh){
        this.postRequest('/system/basic/permission/role', this.permissionInputValue).then(resp => {
          if(resp.code === 200){
            this.initPermissions()
            this.permissionInputValue = {}
          }
        })
      } else {
        this.$message.error('请先填写权限英文名、权限中文名！')
      }
    },

    // 初始化数据
    initPermissions(){
      this.getRequest('/system/basic/permission/').then(resp => {
        this.roles = resp
      })
    },
    // 点击手风琴：打开和关闭
    changeCollapse(roleId){
      if(roleId){
        this.getSelectedMenus(roleId)
        this.initMenus()
      }
    },
    initMenus(){
      this.getRequest('/system/basic/permission/menus').then(resp => {
        if(resp.code === 200){
          this.treeMenus = resp.object
        }
      })
    },
    getSelectedMenus(roleId){
      this.getRequest('/system/basic/permission/menu/' + roleId).then(resp => {
        if(resp.code === 200){
          this.selectedMenus = resp.object
        }
      })
    },
    // 更新指定权限树
    doUpdate(roleId, index){
      // 获取当前的树
      let currentTree = this.$refs.tree[index]
      let keys = currentTree.getCheckedKeys(true)
      let url = '/system/basic/permission/?rid=' + roleId
      let midsParam = ''
      keys.forEach(mid => midsParam += '&mids=' + mid)
      this.putRequest(url + midsParam).then(resp => {
        if(resp.code === 200){
          this.initPermissions()
          this.activeName = '-1'
        }
      })
    },
    cancelEdit(){
      this.activeName = '-1'
    },
    // 删除角色
    doDelete(role){
      this.$confirm('此操作将永久删除该【'+role.nameZh+'】角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/permission/' + role.id).then(resp => {
          if(resp && resp.code === 200){
            this.initPermissions();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  },
  mounted() {
    this.initPermissions()
  }
}
</script>

<style scoped>
.addPermission {
  display: flex;
  justify-content: space-between;
  width: 700px;
}
.addPermissionInput {
  width: 300px;
}
.mainCollapse {
  margin-top: 8px;
  width: 700px;
}

.treeOperationButton {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
}
</style>