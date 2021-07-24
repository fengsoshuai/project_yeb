<template>
  <div>
    <!--功能区-->
    <div class="functional-area">
      <div class="emp-search">
        <el-input class="emp-search-input" clearable @clear="initEmpList" @keydown.enter.native="initEmpList" size="small" prefix-icon="el-icon-search" v-model="empName" placeholder="请输入员工名进行搜索..."></el-input>
        <el-button class="emp-search-button" size="small" type="primary" icon="el-icon-search" @click="searchEmp">搜索</el-button>
        <el-button type="primary" size="small">
          <i class="fa fa-angle-double-down" aria-hidden="true"></i>
          高级搜索
        </el-button>
        <el-button size="small" type="success" icon="el-icon-refresh" @click="refreshEmpList">刷新</el-button>
      </div>
      <!--导入、导出、添加员工-->
      <div>
        <el-button type="success" size="small"><i class="fa fa-level-down" aria-hidden="true"></i>导入</el-button>
        <el-button type="success" size="small"><i class="fa fa-level-up" aria-hidden="true"></i>导出</el-button>
        <el-button type="primary" icon="el-icon-plus" size="small" @click="showAddDialog">添加员工</el-button>
      </div>
    </div>

    <!--员工列表-->
    <div class="emp-table-container">
      <el-table
          :data="empList"
          border
          stripe
          size="small"
          style="width: 100%; margin-bottom: 8px;"
          hide-on-single-page
          max-height="675"
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          :header-cell-style="cellHeaderStyle"
          :cell-style="cellStyle">
        <el-table-column
            type="selection"
            width="45">
        </el-table-column>
        <el-table-column
            type="index"
            fixed
            label="#"
            width="40">
        </el-table-column>
        <el-table-column
            prop="workId"
            label="工号"
            fixed
            width="90">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            fixed
            width="120">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别"
            width="60">
        </el-table-column>
        <el-table-column
            prop="birthday"
            label="生日"
            width="90">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            label="婚姻状况"
            width="80">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            label="民族"
            width="80">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            label="籍贯"
            width="120">
        </el-table-column>
        <el-table-column
            prop="politicsStatus.name"
            label="政治面貌"
            width="120">
        </el-table-column>
        <el-table-column
            prop="department.name"
            label="部门"
            width="100">
        </el-table-column>
        <el-table-column
            prop="joblevel.name"
            label="职称"
            width="100">
        </el-table-column>
        <el-table-column
            prop="position.name"
            label="职位"
            width="100">
        </el-table-column>
        <el-table-column
            prop="email"
            label="邮箱"
            width="200">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话号码"
            width="100">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            label="聘用形式"
            width="100">
        </el-table-column>
        <el-table-column
            prop="tiptopDegree"
            label="最高学历"
            width="80">
        </el-table-column>
        <el-table-column
            prop="school"
            label="毕业院校"
            width="180">
        </el-table-column>
        <el-table-column
            prop="specialty"
            label="所属专业"
            width="180">
        </el-table-column>
        <el-table-column
            prop="conversionTime"
            label="转正日期"
            width="90">
        </el-table-column>
        <el-table-column
            prop="beginDate"
            label="入职日期"
            width="90">
        </el-table-column>
        <el-table-column
            prop="notWorkDate"
            label="离职日期"
            width="90">
        </el-table-column>
        <el-table-column
            prop="workState"
            label="在职状态"
            width="70">
        </el-table-column>
        <el-table-column
            prop="contractTerm"
            label="合同期限"
            width="100">
          <template slot-scope="scope">
            <el-tag size="small" v-text="scope.row.contractTerm"></el-tag>&nbsp;&nbsp;年
          </template>
        </el-table-column>
        <el-table-column
            prop="beginContract"
            label="合同起始日期"
            width="100">
        </el-table-column>
        <el-table-column
            prop="endContract"
            label="合同终止日期"
            width="100">
        </el-table-column>
        <el-table-column
            prop="address"
            label="联系地址"
            width="320">
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="编辑员工资料" placement="bottom">
              <el-button size="mini" class="empOperateButton" icon="el-icon-edit">编辑</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="查看高级资料" placement="bottom">
              <el-button size="mini" class="empOperateButton" type="primary" icon="el-icon-view">查看</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除员工" placement="bottom">
              <el-button size="mini" class="empOperateButton" type="danger" icon="el-icon-delete-solid">删除</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <el-pagination
          style="display: flex; justify-content: flex-end;"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          @prev-click="handlePrevClick"
          @next-click="handleNextClick"
          :current-page="this.pageArguments.currentPage"
          :page-sizes="[15, 50, 100, 200]"
          :page-size="this.pageArguments.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="this.pageArguments.totalSize">
      </el-pagination>
    </div>

    <!--添加员工对话框-->
    <el-dialog
        title="添加员工"
        :visible.sync="addDialogVisible"
        width="80%"
        :before-close="handleCloseAddPanel">
      <div>
        <el-form ref="addEmpForm" :model="emp">
          <!--第一行：姓名、性别、出生日期、身份证号-->
          <el-row>
            <el-col :span="5">
              <el-form-item label="姓名：" prop="name" size="small">
                <el-input size="small" clearable v-model="emp.name" placeholder="请输入员工姓名..." prefix-icon="el-icon-edit" style="width: 220px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="性别：" prop="gender" size="small">
                <el-radio-group v-model="emp.gender" size="small">
                  <el-radio-button label="男"></el-radio-button>
                  <el-radio-button label="女"></el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="出生日期：" prop="birthday" size="small">
                <el-date-picker
                    v-model="emp.birthday"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="出生日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="9">
              <el-form-item label="身份证号：" prop="idCard" size="small">
                <el-input size="small" clearable v-model="emp.idCard" placeholder="请输入身份证号..." style="width: 240px;"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!--第二行：婚姻状况、政治面貌-->
          <el-row>
            <el-col :span="5">
              <el-form-item label="婚姻状况：" prop="wedlock" size="small">
                <el-radio-group v-model="emp.wedlock" size="small">
                  <el-radio-button label="已婚"></el-radio-button>
                  <el-radio-button label="未婚"></el-radio-button>
                  <el-radio-button label="离异"></el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>

            <el-col :span="5">
              <el-form-item label="政治面貌：" prop="politicId" size="small">
                <el-select clearable v-model="emp.politicId" filterable placeholder="请选择政治面貌..." @visible-change="showSelectPoliticId" size="small" style="width: 160px;">
                  <el-option
                      v-for="(politic, index) in this.politicIds"
                      :key="index"
                      :label="politic.name"
                      :value="politic.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="addDialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="addEmp">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "EmpBasic",
  data(){
    return {
      empName: '',
      empList: [],
      loading: true,
      pageArguments: {
        totalSize: 0,
        size: 15,
        currentPage: 1
      },
      addDialogVisible: false,
      // 添加员工基本信息
      emp: {
        name: '',
        address: '',
        beginContract: '',
        beginDate: '',
        birthday: '',
        contractTerm: null,
        conversionTime: '',
        createDate: '',
        departmentId: null,
        email: '',
        endContract: '',
        engageForm: '',
        gender: '',
        idCard: '',
        jobLevelId: null,
        nationId: null,
        nativePlace: '',
        notWorkDate: '',
        phone: '',
        politicId: null,
        posId: null,
        salaryId: null,
        school: '',
        specialty: '',
        tiptopDegree: '',
        wedlock: '',
        workAge: null,
        workId: '',
        workState: ''
      },
      politicIds: []
    }
  },

  methods: {
    searchEmp(){
      if(this.empName){
        this.initEmpList()
      } else {
        this.$message.warning("请输入员工名进行搜索！");
      }
    },
    refreshEmpList(){
      this.empName = ''
      this.pageArguments.currentPage = 1
      this.initEmpList()
    },
    initEmpList(){
      this.loading = true
      let pageParam = 'currentPage=' + this.pageArguments.currentPage + '&size=' + this.pageArguments.size;
      // 员工名搜索
      let empParam = '';
      if(this.empName){
        empParam += '&name=' + this.empName
        pageParam += empParam
      }
      this.getRequest('/employee/basic/?' + pageParam).then(resp => {
        if(resp.code === 200){
          this.empList = resp.data
          this.loading = false
          this.pageArguments.totalSize = resp.total
        }
      })
    },
    // 表格内容居中
    cellStyle(){
      return 'text-align: center;'
    },
    // 表格头部居中
    cellHeaderStyle(){
      return 'text-align: center;'
    },
    handleSizeChange(pageSize){
      this.pageArguments.size = pageSize
      this.initEmpList()
    },
    handleCurrentChange(currentPage){
      this.pageArguments.currentPage = currentPage
      this.initEmpList()
    },
    handlePrevClick(currentPage){
    },
    handleNextClick(currentPage){
    },
    handleCloseAddPanel(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      this.clearAddDialog();
    },
    // 打开添加面板
    showAddDialog(){
      this.addDialogVisible = true
    },
    showSelectPoliticId(isShow){
      // 显示政治面貌的下拉选择框
      if(isShow && this.politicIds.length === 0){
        this.getRequest('/employee/basic/politicsStatus').then(resp => {
          if(resp.code === 200){
            this.politicIds = resp.object
          }
        })
      }
    },
    // 清空添加对话框的选择、填写的数据
    clearAddDialog(){
      this.politicIds = []
    },
    // 添加员工
    addEmp(){
      this.addDialogVisible = false;
      this.clearAddDialog();
    }
  },
  mounted() {
    this.initEmpList()
  }
}
</script>

<style scoped>
.functional-area {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}
.emp-search-input {
  width: 400px;
  margin-right: 10px;
}
.emp-search {
  display: flex;
  width: 485px;
  justify-content: space-between;
}
.empOperateButton {
  padding: 2px;
}
.emp-table-container {
  height: 700px;
}
</style>