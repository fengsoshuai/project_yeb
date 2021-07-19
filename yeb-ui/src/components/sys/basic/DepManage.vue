<template>
  <div class="deptPanel">
    <el-input
        class="filterTextInput"
        placeholder="请输入部门名称进行搜索..."
        size="small"
        v-model="filterText">
    </el-input>
    <el-tree
        class="filter-tree"
        :data="deptData"
        :props="defaultProps"
        :filter-node-method="filterNode"
        ref="tree">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ data.name }}</span>
        <span>
          <el-button
              class="nodeOperation-plus"
              type="primary"
              icon="el-icon-circle-plus"
              @click="() => showAdd(data)">添加
          </el-button>
          <el-button
              class="nodeOperation-delete"
              type="danger"
              icon="el-icon-delete-solid"
              @click="() => remove(data)">删除
          </el-button>
        </span>
      </span>
    </el-tree>

    <el-dialog title="添加部门" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="deptForm">
        <el-form-item label="上级部门" label-width="120px">
          <el-input v-model="deptParentName" autocomplete="off" disabled class="deptFormInput"></el-input>
        </el-form-item>
        <el-form-item label="部门名称" label-width="120px">
          <el-input v-model="deptForm.name" placeholder="请输入部门名称..." autocomplete="off" class="deptFormInput"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancelAdding">取 消</el-button>
        <el-button size="small" type="primary" @click="append">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "DepManage",
  data(){
    return {
      deptData: [],
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      filterText: '',
      deptParentName: '',
      deptForm: {name: '', parentId: -1},
      dialogFormVisible: false,
    }
  },
  watch: {
    // 这里的 filter 是调用 filterNode
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 初始化部门数据
    initDept(){
      this.getRequest('/system/basic/department/').then(resp => {
        if(resp.code === 200){
          this.deptData = resp.object
        }
      })
    },
    // 过滤节点，返回true表示显示节点
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    // 展示添加页面
    showAdd(data){
      this.dialogFormVisible = true;
      this.deptParentName = data.name;
      this.deptForm.parentId = data.id;
    },
    // 添加部门
    append(){
      if(this.deptForm.name !== ''){
        this.putRequest('/system/basic/department/', this.deptForm).then(resp => {
          if(resp.code === 200){
            this.dialogFormVisible = false;
            this.initAddForm();
            this.addDeptNode(this.deptData, resp.object);
          }
        })
      } else {
        this.$message.error("请先输入要添加的部门名称！");
      }
    },
    initAddForm(){
      this.deptForm = {
        name: '', parentId: -1
      }
      this.deptParentName = ''
    },
    cancelAdding(){
      this.dialogFormVisible = false;
      this.deptParentName = '';
      this.deptForm.parentId = -1;
    },
    // 删除部门
    remove(data){
      if(data.isParent){
        this.$message.warning('【'+data.name+'】部门存在子部门，不能删除！');
        return
      }
      this.$confirm('此操作将永久删除【'+data.name+'】部门, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/department/' + data.id).then(resp => {
          if(resp && resp.code === 200){
            this.removeDeptNode(null, this.deptData, data.id);
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 增加数据成功后，在树结点手动增加一个节点
    addDeptNode(deptData, object) {
      for (let i = 0; i < deptData.length; i++) {
        let dept = deptData[i];
        if(dept.id === object.parentId){
          dept.children = dept.children.concat(object)
          if(dept.children.length > 0){
            dept.isParent = true
          }
          return
        } else {
          this.addDeptNode(dept.children, object);
        }
      }
    },
    // 删除后
    removeDeptNode(dep, deptData, id){
      for (let i = 0; i < deptData.length; i++) {
        let dept = deptData[i];
        if(dept.id === id){
          deptData.splice(i, 1);
          if(deptData.length === 0){
            dep.isParent = false
          }
          return
        } else {
          this.removeDeptNode(dept, dept.children, id);
        }
      }
    }
  },
  mounted() {
    this.initDept();
  }
}
</script>

<style scoped>
.deptPanel {
  width: 700px;
  height: 720px;
  overflow-y: scroll;
}
.filterText {
  margin-bottom: 8px;
}
.filterTextInput{
  width: 50%;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
.nodeOperation-plus, .nodeOperation-delete {
  padding: 2px;
}
.deptFormInput {
  width: 240px;
}
</style>