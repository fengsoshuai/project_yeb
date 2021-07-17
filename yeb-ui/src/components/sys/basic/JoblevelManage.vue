<template>
  <div>
    <!--添加职称-->
    <div class="addJoblevel">
      <el-input class="addJoblevelInput"
                placeholder="输入职称名..."
                size="small"
                suffix-icon="el-icon-plus"
                v-model="joblevelInputValue.name">
      </el-input>
      <!--下拉选择职称等级-->
      <el-select size="small" v-model="joblevelInputValue.titleLevel" filterable placeholder="请选择职称..." class="addJoblevelSelect">
        <el-option
            v-for="item in titleLevels"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <!--添加按钮-->
      <el-button size="small" icon="el-icon-plus" type="primary" @click="addJoblevel">添加</el-button>
    </div>

    <!--职称列表，表格数据-->
    <div class="jobLevelData">
      <el-table
          :data="tableData"
          height="620"
          width="70%"
          size="small"
          border
          style="width: 100%"
          :header-cell-style="cellStyle"
          :cell-style="cellHeaderStyle"
          @selection-change="handleSelectJoblevel">
        <el-table-column
            type="selection"
            width="45">
        </el-table-column>
        <el-table-column
            prop="id"
            label="职称编号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="name"
            label="职称名称"
            width="280">
        </el-table-column>
        <el-table-column
            prop="titleLevel"
            label="职称等级"
            width="280">
        </el-table-column>
        <el-table-column
            prop="enabled"
            label="是否启用"
            width="180">
          <template slot-scope="scope">
            <div class="enableSwitch">
              <el-switch
                  v-model="scope.row.enabled"
                  :active-value="true"
                  :inactive-value="false"
                  active-color="#13ce66"
                  inactive-color="#ff4949" @change="changeEnabled(scope.row)">
              </el-switch>
              <el-tag
                  size="mini"
                  v-if="scope.row.enabled"
                  :key="scope.row.id"
                  type="success"
                  effect="dark">
                已启用
              </el-tag>
              <el-tag
                  size="mini"
                  v-if="!scope.row.enabled"
                  :key="scope.row.id"
                  type="danger"
                  effect="dark">
                未启用
              </el-tag>
            </div>
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

    <el-button class="batchDeleteButton" size="small" type="danger" @click="batchDeletion" :disabled="selectedJoblevel.length === 0">批量删除</el-button>
    <!--编辑职称弹出框-->
    <el-dialog
        title="编辑职称"
        :visible.sync="editDialogVisible"
        width="30%"
        :before-close="handleEditClose">
      <div>
        <el-form :model="editForm">
          <el-form-item label="职称名称" label-width="120px">
            <el-input v-model="editForm.name" size="small" class="editNameInput"></el-input>
          </el-form-item>
          <el-form-item label="职称等级" label-width="120px">
            <el-select size="small" v-model="editForm.titleLevel" filterable class="editTitleLevelSelect">
              <el-option
                  v-for="item in titleLevels"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
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
  name: "JoblevelManage",
  data(){
    return {
      // 增加职称
      joblevelInputValue: {
        name: '', titleLevel: ''
      },
      // 职称下拉条的数据
      titleLevels: [
        '正高级','副高级','中级','初级','员级'
      ],
      // 原始数据
      tableData: [],
      // 多选
      selectedJoblevel: [],
      // 更改是否禁用
      changeEnableValue: { enabled: ''},
      // 更新表单
      editForm: {
        name: '', titleLevel: ''
      },
      editDialogVisible: false,
    }
  },
  methods: {
    // 添加职称
    addJoblevel(){
      if(this.joblevelInputValue.titleLevel && this.joblevelInputValue.name){
        this.postRequest('/system/basic/joblevel/', this.joblevelInputValue).then(resp => {
          if(resp.code === 200){
            this.initJoblevels()
            this.joblevelInputValue = {}
          }
        })
      } else {
        this.$message.error('请输入并选择要添加的职信息！')
      }
    },
    // 初始化数据
    initJoblevels(){
      this.getRequest('/system/basic/joblevel/').then(resp => {
        if(resp.code === 200 && resp.message === 'success'){
          this.tableData = resp.object;
        }
      })
    },
    // 多选
    handleSelectJoblevel(selectedIndex){
      this.selectedJoblevel = selectedIndex
    },
    // 表格内容居中
    cellStyle(){
      return 'text-align: center;'
    },
    // 表格头部居中
    cellHeaderStyle(){
      return 'text-align: center;'
    },
    // 更新是否启用
    changeEnabled(row){
      Object.assign(this.changeEnableValue, row)
      this.changeEnableValue.createDate = ''
      this.put('/system/basic/joblevel/', this.changeEnableValue)
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
      if(this.editForm.name && this.editForm.titleLevel){
        this.putRequest('/system/basic/joblevel/', this.editForm).then(resp => {
          if(resp && resp.code === 200){
            this.initJoblevels();
            this.editDialogVisible = false
          }
        })
      } else {
        this.$message.error('请输入要更新的职称信息！')
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
    handleDelete(index, row){
      /*if(row.enabled){
        this.$message.error('请先禁用该职称！')
        return;
      }*/
      this.$confirm('此操作将永久删除该【'+row.name+'】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/joblevel/' + row.id).then(resp => {
          if(resp && resp.code === 200){
            this.initJoblevels();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 批量删除
    batchDeletion(){
      this.$confirm('此操作将永久删除【'+this.selectedJoblevel.length+'】条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = ''
        this.selectedJoblevel.forEach(item => ids += 'ids=' + item.id + '&');
        this.deleteRequest('/system/basic/joblevel/?' + ids.substring(0, ids.length - 1)).then(resp => {
          if(resp && resp.code === 200){
            this.initJoblevels();
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
    this.initJoblevels()
  }
}
</script>

<style scoped>
.addJoblevelInput {
  width: 300px;
}
.addJoblevel {
  display: flex;
  justify-content: space-between;
  width: 600px;
}
.jobLevelData{
  margin-top: 20px;
}
.editNameInput, .editTitleLevelSelect {
  width: 200px;
}
.batchDeleteButton {
  margin-top: 8px;
  margin-bottom: 8px;
}
.enableSwitch {
  display: flex;
  justify-content: space-between;
  width: 60%;
  margin-left: 20%;
}
</style>