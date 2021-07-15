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
      <el-button size="small" icon="el-icon-plus" type="primary" @click="addPosition">添加</el-button>
    </div>
    <!--表格数据-->
    <div class="positionData">
      <el-table
          :data="tableData"
          height="650"
          border
          style="width: 100%"
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
                @click="handleEdit(scope.row.id, scope.row)">编辑</el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row.id, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>

export default {
  name: "PosManage",
  data(){
    return {
      positionInputValue: {
        name: ''
      },
      tableData: [],
      selectedPosition: []
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
    // 编辑
    handleEdit(index, row) {
      console.log(index, row);
    },
    // 删除
    handleDelete(index, row) {
      console.log(index, row);
    },
    // 禁用
    changeEnabled(row) {
      console.log(row);
    },
    // 多选
    handleSelectPositions(selectedIndex){
      this.selectedPosition = selectedIndex
      console.log(this.selectedPosition)
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
</style>