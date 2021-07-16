<template>
  <div>
    <!--输入框和按钮-->
    <div class="addPosition">
      <el-input class="addPositionInput"
          placeholder="添加职位..."
          size="small"
          suffix-icon="el-icon-plus"
          v-model="positionInputValue.name">
      </el-input>
    <!--添加按钮-->
      <el-button size="small" icon="el-icon-plus" type="primary" @click="addPosition">添加</el-button>
    </div>
    <!--表格数据-->
    <div class="positionData">
      <el-table
          :data="tableData"
          height="620"
          border
          style="width: 100%"
          :header-cell-style="cellStyle"
          :cell-style="cellHeaderStyle"
          @selection-change="handleSelectPositions">
        <el-table-column
            type="selection"
            width="45">
        </el-table-column>
        <el-table-column
            prop="id"
            label="职位编号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="name"
            label="职位名称"
            width="280">
        </el-table-column>
        <el-table-column
            prop="enabled"
            label="是否启用"
            width="180">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.enabled"
                :active-value="true"
                :inactive-value="false"
                active-color="#13ce66"
                inactive-color="#ff4949" @change="changeEnabled(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建时间"
            width="200">
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="showEditDialog(scope.row.id, scope.row)">编辑</el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row.id, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-button class="batchDeleteButton" size="small" type="danger" @click="batchDeletion" :disabled="selectedPosition.length === 0">批量删除</el-button>
    <el-dialog
        title="编辑职位"
        :visible.sync="editDialogVisible"
        width="30%"
        :before-close="handleEditClose">
      <div>
        <el-form :model="editForm">
          <el-form-item label="职位名称" label-width="120px">
            <el-input v-model="editForm.name" size="small" class="editNameInput"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="editDialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="handlerEdit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PosManage",
  data(){
    return {
      // 添加职称名称
      positionInputValue: {
        name: ''
      },
      // 原始数据
      tableData: [],
      // 多选
      selectedPosition: [],
      // 展示更新对话框
      editDialogVisible: false,
      // 编辑数据：职位名称
      editForm: { name: '' },
      // 更改是否禁用
      changeEnableValue: { enabled: ''}
    }
  },
  methods: {
    // 初始化数据
    initPositions(){
      this.getRequest('/system/basic/pos/').then(resp => {
        if(resp.code === 200 && resp.message === 'success'){
          this.tableData = resp.object;
        }
      })
    },
    // 添加
    addPosition(){
      if(this.positionInputValue.name){
        this.postRequest('/system/basic/pos/', this.positionInputValue).then(resp => {
          if(resp.code === 200){
            this.initPositions()
          }
        })
      } else {
        this.$message.error('请输入要添加的职位名称！')
      }
    },
    // 编辑框展示
    showEditDialog(index, row) {
      // 对象复制，呈现到表单
      Object.assign(this.editForm, row)
      this.editForm.createDate = ''
      this.editDialogVisible = true
    },
    // 更新数据
    handlerEdit(){
      if(this.editForm.name){
        this.putRequest('/system/basic/pos/', this.editForm).then(resp => {
          if(resp && resp.code === 200){
            this.initPositions();
            this.editDialogVisible = false
          }
        })
      } else {
        this.$message.error('请输入要更新的职位名称！')
      }
    },
    // 编辑框关闭
    handleEditClose(done){
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该【'+row.name+'】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/pos/' + row.id).then(resp => {
          if(resp && resp.code === 200){
            this.initPositions();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 禁用
    changeEnabled(row) {
      Object.assign(this.changeEnableValue, row)
      this.changeEnableValue.createDate = ''
      this.put('/system/basic/pos/', this.changeEnableValue)
    },
    // 多选
    handleSelectPositions(selectedIndex){
      this.selectedPosition = selectedIndex
    },
    // 批量删除
    batchDeletion(){
      this.$confirm('此操作将永久删除【'+this.selectedPosition.length+'】条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = ''
        this.selectedPosition.forEach(item => ids += 'ids=' + item.id + '&');
        this.deleteRequest('/system/basic/pos/?' + ids.substring(0, ids.length - 1)).then(resp => {
          if(resp && resp.code === 200){
            this.initPositions();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 表格内容居中
    cellStyle(){
      return 'text-align: center;'
    },
    // 表格头部居中
    cellHeaderStyle(){
      return 'text-align: center;'
    }
  },
  mounted() {
    this.initPositions();
  }
}
</script>

<style scoped>
.addPosition {
  display: flex;
  justify-content: space-between;
  width: 385px;
}
.addPositionInput {
  width: 300px;
}
.positionData {
  margin-top: 20px;
}
.editNameInput {
  width: 200px;
}
.batchDeleteButton {
  margin-top: 8px;
  margin-bottom: 8px;
}
</style>