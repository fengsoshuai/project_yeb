<template>
  <div>
    <!--功能区-->
    <div class="functional-area">
      <div class="emp-search">
        <el-input :disabled="showAdvancedSearchVisible" class="emp-search-input" clearable @clear="initEmpList(showAdvancedSearchVisible ? 'advancedSearch' : 'withName')" @keydown.enter.native="initEmpList(showAdvancedSearchVisible ? 'advancedSearch' : 'withName')" size="small" prefix-icon="el-icon-search" v-model="empName" placeholder="请输入员工名进行搜索..."></el-input>
        <el-button :disabled="showAdvancedSearchVisible" class="emp-search-button" size="small" type="primary" icon="el-icon-search" @click="searchEmp">搜索</el-button>
        <el-button type="primary" size="small" @click="showAdvancedSearchVisible = !showAdvancedSearchVisible">
          <i :class="showAdvancedSearchVisible ? 'fa fa-angle-double-up' : 'fa fa-angle-double-down'" aria-hidden="true"></i>
          高级搜索
        </el-button>
        <el-button size="small" type="success" icon="el-icon-refresh" @click="refreshEmpList">刷新</el-button>
      </div>

      <!--导入、导出、添加员工-->
      <div class="buttonMenus">
        <el-popover
            placement="left-end"
            title="导入员工数据"
            width="360"
            @show="clearUploadContent"
            trigger="click">
          <el-upload
              drag
              :before-upload="beforeUpload"
              :on-success="uploadSuccess"
              :on-error="uploadError"
              :show-file-list="false"
              :disabled="importDisabled"
              :headers="importHeaders"
              action="/employee/basic/import">
            <i v-bind:class="uploadClass"></i>
            <div class="el-upload__text" v-html="uploadText">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">选择：员工数据的Excel文件</div>
          </el-upload>
          <el-button :disabled="importDisabled" slot="reference" type="success" size="small"><i class="el-icon-upload2" aria-hidden="true"></i>导入</el-button>
        </el-popover>

        <el-popover
            placement="top-start"
            title="导出员工数据功能"
            width="200"
            trigger="hover"
            content="当未选择员工时导出全部数据；当选择某些员工时则导出该部分员工数据">
          <el-button slot="reference" type="success" size="small" @click="exportEmp"><i class="el-icon-download" aria-hidden="true" ></i>导出</el-button>
        </el-popover>
        <el-button type="primary" icon="el-icon-plus" size="small" @click="showAddDialog">添加员工</el-button>
      </div>
    </div>

    <!--高级搜索面板-->
    <transition name="slide-fade">
      <div v-show="showAdvancedSearchVisible" style="border: 1px solid #1989FA; font-size: 12px; border-radius: 5px;margin: 15px 0; padding: 5px; box-sizing: border-box;">
        <el-row>
          <el-col :span="4">
            政治面貌：
              <el-select clearable v-model="searchValue.politicId" filterable placeholder="请选择政治面貌..." size="mini" style="width: 150px;">
                <el-option
                    v-for="(politic, index) in this.politicIds"
                    :key="index"
                    :label="politic.name"
                    :value="politic.id">
                </el-option>
              </el-select>
          </el-col>

          <el-col :span="4">
            民族：
            <el-select clearable v-model="searchValue.nationId" filterable placeholder="请选择民族..."  size="mini" style="width: 150px;">
              <el-option
                  v-for="(nation, index) in this.nationIds"
                  :key="index"
                  :label="nation.name"
                  :value="nation.id">
              </el-option>
            </el-select>
          </el-col>

          <el-col :span="4">
            职位：
            <el-select clearable v-model="searchValue.posId" filterable placeholder="请选择职位..." size="mini" style="width: 150px;">
              <el-option
                  v-for="(position, index) in this.posIds"
                  :key="index"
                  :label="position.name"
                  :value="position.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            职称：
            <el-select clearable v-model="searchValue.jobLevelId" filterable placeholder="请选择职称..." size="mini" style="width: 150px;">
              <el-option
                  v-for="(jobLevel, index) in this.jobLevelIds"
                  :key="index"
                  :label="jobLevel.name"
                  :value="jobLevel.id">
              </el-option>
            </el-select>
          </el-col>

          <el-col :span="4">
            聘用形式：
            <el-radio-group v-model="searchValue.engageForm" size="mini">
              <el-radio-button label="劳动合同">劳动合同</el-radio-button>
              <el-radio-button label="劳务合同">劳务合同</el-radio-button>
            </el-radio-group>
          </el-col>

          <el-col :span="4" style="display: inline-flex; align-items: center;">
            所属部门：
            <el-popover
                placement="left"
                title="请选择部门"
                width="250"
                height="300"
                trigger="manual"
                content=""
                v-model="selectAdvanceDeptVisible">
              <el-tree  default-expand-all :data="deptTreeData" :props="deptTreeProps" @node-click="handleDeptNodeSearchClick" style="width: 250px;">
              </el-tree>
              <div slot="reference" style="border:1px solid #dedede; width: 150px; height: 27px; display:inline-flex; align-items: center;border-radius: 5px;cursor:pointer;padding-left:8px;font-size:12px;box-sizing: border-box;" @click="selectAdvanceDeptVisible = !selectAdvanceDeptVisible">
                <span v-text="selectedSearchDept"></span>
              </div>
            </el-popover>
          </el-col>
        </el-row>

        <el-row style="margin-top: 10px;">
          <el-col :span="10">
            入职日期：
            <el-date-picker
                v-model="searchValue.beginDateScope"
                size="mini"
                type="daterange"
                unlink-panels
                range-separator="至"
                value-format="yyyy-MM-dd"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
            </el-date-picker>
          </el-col>

          <el-col :span="4" :offset="10">
            <el-button icon="el-icon-close" type="warning" size="mini" @click="cancelAdvancedSearch">取消</el-button>
            <el-button icon="el-icon-search" type="primary" size="mini" @click="doAdvancedSearch">搜索</el-button>
          </el-col>
        </el-row>
      </div>
    </transition>
    <!--员工列表-->
    <div class="emp-table-container">
      <el-table
          :data="empList"
          border
          stripe
          size="small"
          style="width: 100%; margin-bottom: 8px;"
          hide-on-single-page
          max-height="650"
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          :header-cell-style="cellHeaderStyle"
          @selection-change="handleSelectEmps"
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
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="编辑员工资料" placement="bottom">
              <el-button size="mini" class="empOperateButton" icon="el-icon-edit" @click="showEditDialog(scope.row)">编辑</el-button>
            </el-tooltip>
            <!--<el-tooltip class="item" effect="dark" content="查看高级资料" placement="bottom">
              <el-button size="mini" class="empOperateButton" type="primary" icon="el-icon-view">查看</el-button>
            </el-tooltip>-->
            <el-tooltip class="item" effect="dark" content="删除员工" placement="bottom">
              <el-button size="mini" class="empOperateButton" type="danger" icon="el-icon-delete-solid" @click="deleteEmp(scope.row)">删除</el-button>
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
          :page-sizes="[10, 50, 100, 200]"
          :page-size="this.pageArguments.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="this.pageArguments.totalSize">
      </el-pagination>
    </div>

    <!--添加员工对话框-->
    <el-dialog
        :title="dialogTitle"
        :visible.sync="addDialogVisible"
        width="80%">
      <div>
        <el-form ref="addEmpForm" :model="emp" :rules="empRules">
          <!--第一行：姓名、性别、出生日期、身份证号-->
          <el-row>
            <el-col :span="5">
              <el-form-item label="姓名：" prop="name" size="small">
                <el-input size="small" clearable v-model="emp.name" placeholder="请输入员工姓名..." prefix-icon="el-icon-edit" style="width: 220px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="出生日期：" prop="birthday" size="small">
                <el-date-picker
                    v-model="emp.birthday"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 200px;"
                    placeholder="出生日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="性别：" prop="gender" size="small">
                <el-radio-group v-model="emp.gender" size="small">
                  <el-radio-button label="男"></el-radio-button>
                  <el-radio-button label="女"></el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="身份证号：" prop="idCard" size="small">
                <el-input prefix-icon="el-icon-postcard" size="small" clearable v-model="emp.idCard" placeholder="请输入身份证号..." style="width: 200px;"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!--第二行：婚姻状况、政治面貌、民族、籍贯-->
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
            <el-col :span="6">
              <el-form-item label="政治面貌：" prop="politicId" size="small">
                <el-select clearable v-model="emp.politicId" filterable placeholder="请选择政治面貌..." size="small" style="width: 200px;">
                  <el-option
                      v-for="(politic, index) in this.politicIds"
                      :key="index"
                      :label="politic.name"
                      :value="politic.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="民族：" prop="nationId" size="small">
                <el-select clearable v-model="emp.nationId" filterable placeholder="请选择民族..."  size="small" style="width: 180px;">
                  <el-option
                      v-for="(nation, index) in this.nationIds"
                      :key="index"
                      :label="nation.name"
                      :value="nation.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="籍贯：" prop="nativePlace" size="small">
                <el-input prefix-icon="el-icon-edit" size="small" placeholder="请输入籍贯..." clearable v-model="emp.nativePlace" style="width: 228px;"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <!--第三行：电子邮箱、联系地址、职位、职称 -->
          <el-row>
            <el-col :span="5">
              <el-form-item label="电子邮箱：" prop="email" size="small">
                <el-input prefix-icon="el-icon-message" size="small" placeholder="请输入电子邮箱..." clearable v-model="emp.email" style="width: 190px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="联系地址：" prop="address" size="small">
                <el-input prefix-icon="el-icon-location" size="small" placeholder="请输入联系地址..." clearable v-model="emp.address" style="width: 200px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="职位：" prop="posId" size="small">
                <el-select clearable v-model="emp.posId" filterable placeholder="请选择职位..." size="small" style="width: 180px;">
                  <el-option
                      v-for="(position, index) in this.posIds"
                      :key="index"
                      :label="position.name"
                      :value="position.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="职称：" prop="jobLevelId" size="small">
                <el-select clearable v-model="emp.jobLevelId" filterable placeholder="请选择职称..." size="small" style="width: 228px;">
                  <el-option
                      v-for="(jobLevel, index) in this.jobLevelIds"
                      :key="index"
                      :label="jobLevel.name"
                      :value="jobLevel.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <!--第四行：所属部门（暂时用input，应该是一个树形结构） 、电话号码、工号、学历-->
          <el-row>
            <el-col :span="5">
              <el-form-item label="所属部门：" prop="departmentId" size="small">
                <el-popover
                  placement="right"
                  title="请选择部门"
                  width="250"
                  height="300"
                  trigger="manual"
                  content=""
                  v-model="selectDeptVisible">
                  <el-tree  default-expand-all :data="deptTreeData" :props="deptTreeProps" @node-click="handleDeptNodeClick" style="width: 250px;">
                  </el-tree>
                  <div slot="reference" style="border:1px solid #dedede; width: 190px; height: 30px; display:inline-flex;border-radius: 5px;cursor:pointer;padding-left:8px;font-size:12px;box-sizing: border-box;" @click="showDeptView">
                    <span v-text="selectedDept"></span>
                  </div>
                 </el-popover>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="电话号码：" prop="phone" size="small">
                <el-input prefix-icon="el-icon-phone" size="small" placeholder="请输入电话号码..." clearable v-model="emp.phone" style="width: 200px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="工号：" prop="workId" size="small">
                <el-input disabled prefix-icon="el-icon-edit" size="small" v-model="emp.workId" style="width: 180px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="学历：" prop="tiptopDegree" size="small">
                <el-select clearable v-model="emp.tiptopDegree" filterable size="small" style="width: 228px;">
                  <el-option v-for="(tiptopDegree) in this.tiptopDegrees " :key="tiptopDegree" :label="tiptopDegree" :value="tiptopDegree"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <!--第五行：毕业院校、所学专业、入职日期、转正日期-->
          <el-row>
            <el-col :span="5">
              <el-form-item label="毕业院校：" prop="school" size="small">
                <el-input prefix-icon="el-icon-school" size="small" placeholder="请输入毕业院校..." clearable v-model="emp.school" style="width: 190px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所学专业：" prop="specialty" size="small">
                <el-input prefix-icon="el-icon-edit" size="small" placeholder="请输入所学专业..." clearable v-model="emp.specialty" style="width: 200px;"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="入职日期：" prop="beginDate" size="small">
                <el-date-picker
                    v-model="emp.beginDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 152px;"
                    placeholder="入职日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="转正日期：" prop="conversionTime" size="small">
                <el-date-picker
                    v-model="emp.conversionTime"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 200px;"
                    placeholder="转正日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>

        <!--第六行：合同起始日期、合同截止日期、聘用方式-->
          <el-row>
            <el-col :span="5">
              <el-form-item label="合同起始日期：" prop="beginContract" size="small">
                <el-date-picker
                    v-model="emp.beginContract"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 162px;"
                    placeholder="合同起始日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同截止日期：" prop="endContract" size="small">
                <el-date-picker
                    v-model="emp.endContract"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 172px;"
                    placeholder="合同截止日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="聘用方式：" prop="engageForm" size="small">
                <el-radio-group v-model="emp.engageForm" size="small">
                  <el-radio-button label="劳动合同">劳动合同</el-radio-button>
                  <el-radio-button label="劳务合同">劳务合同</el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="7"></el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="clearAddDialog">取 消</el-button>
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
        size: 10,
        currentPage: 1
      },
      dialogTitle: '添加员工',
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
        workState: '在职'
      },
      politicIds: [],
      nationIds: [],
      posIds: [],
      jobLevelIds: [],
      tiptopDegrees: ['博士', '硕士', '本科', '大专', '高中', '初中', '小学', '其他'],
      selectDeptVisible: false,
      deptTreeData: [],
      deptTreeProps: {
        children: 'children',
        label: 'name'
      },
      // 选中的部门名
      selectedDept: '请点击选择部门',
      empRules: {
        name: [
          { required: true, message: '请输入员工姓名', trigger: 'blur' },
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
        ],
        gender: [{ required: true, message: '请选择性别', trigger: 'blur' }],
        birthday: [ { required: true, message: '请选择出生日期', trigger: 'blur' }],
        idCard: [{ required: true, message: '请输入身份证号', trigger: 'blur' },
          { pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/, message: '身份证号不正确', trigger: 'blur' }
        ],
        wedlock: [{ required: true, message: '请选择婚姻状况', trigger: 'blur' }],
        nationId: [{ required: true, message: '请选择民族', trigger: 'blur' }],
        nativePlace: [{ required: true, message: '请输入籍贯', trigger: 'blur' }],
        politicId: [{ required: true, message: '请选择政治面貌', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
        ],
        phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        address: [{ required: true, message: '请输入住址', trigger: 'blur' }],
        departmentId: [{ required: true, message: '请选择部门', trigger: 'blur' }],
        jobLevelId: [{ required: true, message: '请选择职称', trigger: 'blur' }],
        posId: [{ required: true, message: '请选择职位', trigger: 'blur' }],
        engageForm: [{ required: true, message: '请选择聘用方式', trigger: 'blur' }],
        tiptopDegree: [{ required: true, message: '请选择学历', trigger: 'blur' }],
        specialty: [{ required: true, message: '请输入专业', trigger: 'blur' }],
        school: [{ required: true, message: '请输入毕业学校', trigger: 'blur' }],
        beginDate: [{ required: true, message: '请选择入职日期', trigger: 'blur' }],
        // workState: [{ required: true, message: '请选择在职状态', trigger: 'blur' }],
        workId: [{ required: true, message: '请输入工号', trigger: 'blur' }],
        // contractTerm: [{ required: true, message: '请选择合同期限', trigger: 'blur' }],
        conversionTime: [{ required: true, message: '请选择转正日期', trigger: 'blur' }],
        notWorkDate: [{ required: true, message: '请选择离职日期', trigger: 'blur' }],
        beginContract: [{ required: true, message: '请选择合同起始日期', trigger: 'blur' }],
        endContract: [{ required: true, message: '请选择合同结束日期', trigger: 'blur' }],
        workAge: [{ required: true, message: '请输入工龄', trigger: 'blur' }]
        //,idCard: [{ required: true, message: '请输入身份证号', trigger: 'blur' }]
      },
      // 多选选中的员工
      selectedEmp: [],
      uploadClass: 'el-icon-upload',
      uploadText: '将文件拖到此处，或<em>点击上传</em>',
      importDisabled: false,
      importHeaders: {
        Authorization: window.sessionStorage.getItem('tokenStr')
      },

      // 高级搜索
      showAdvancedSearchVisible: false,
      selectAdvanceDeptVisible: false,
      selectedSearchDept: '',
      searchValue: {
        politicId: null,
        nationId: null,
        posId: null,
        jobLevelId: null,
        engageForm: null,
        departmentId: null,
        beginDateScope: null
      }

    }
  },

  methods: {
    exportEmp(){
      let ids = ''
      this.selectedEmp.forEach(emp => ids += 'ids=' + emp.id + '&')
      this.downLoadRequest('/employee/basic/export?' + ids)
    },
    beforeUpload(){
      this.importDisabled = true
      this.uploadClass = 'el-icon-loading'
      this.uploadText = '正在上传...'
    },
    uploadSuccess(response, file, fileList){
      this.uploadClass = 'el-icon-success'
      this.uploadText = '上传成功！点击<em>继续上传</em>'
      this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
      this.importDisabled = false
    },
    uploadError(err, file, fileList){
      this.uploadClass = 'el-icon-error'
      this.uploadText = '上传失败！点击<em>重新上传</em>'
      this.importDisabled = false
    },
    clearUploadContent(){
      this.uploadClass= 'el-icon-upload'
      this.uploadText= '将文件拖到此处，或<em>点击上传</em>'
    },
    // 选中员工
    handleSelectEmps(selectedIndex){
      this.selectedEmp = selectedIndex
    },
    searchEmp(){
      if(this.empName){
        this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
      } else {
        this.$message.warning("请输入员工名进行搜索！");
      }
    },
    refreshEmpList(){
      this.empName = ''
      this.pageArguments.currentPage = 1
      this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
    },
    // 初始化员工数据
    initEmpList(searchType){
      this.loading = true
      let pageParam = 'currentPage=' + this.pageArguments.currentPage + '&size=' + this.pageArguments.size;
      // 高级搜索
      if(searchType && searchType === 'advancedSearch'){
        if(this.searchValue.politicId){
          pageParam += '&politicId=' + this.searchValue.politicId
        }
        if(this.searchValue.nationId){
          pageParam += '&nationId=' + this.searchValue.nationId
        }
        if(this.searchValue.posId){
          pageParam += '&posId=' + this.searchValue.posId
        }
        if(this.searchValue.jobLevelId){
          pageParam += '&jobLevelId=' + this.searchValue.jobLevelId
        }
        if(this.searchValue.departmentId){
          pageParam += '&departmentId=' + this.searchValue.departmentId
        }
        if(this.searchValue.engageForm){
          pageParam += '&engageForm=' + this.searchValue.engageForm
        }
        if(this.searchValue.beginDateScope){
          pageParam += '&beginDateScope=' + this.searchValue.beginDateScope
        }
      } else {
        // 员工名搜索
        if(this.empName){
          pageParam += '&name=' + this.empName
        }
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
      this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
    },
    handleCurrentChange(currentPage){
      this.pageArguments.currentPage = currentPage
      this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
    },
    handlePrevClick(currentPage){
    },
    handleNextClick(currentPage){
    },
    // 获取工号
    getMaxWorkId(){
      this.getRequest('/employee/basic/maxWorkId').then(resp => {
        if(resp.code === 200){
          this.emp.workId = resp.object
        }
      })
    },
    // 打开添加面板
    showAddDialog(){
      // 清除数据
      this.clearAddDialog()
      this.addDialogVisible = true
      this.dialogTitle = '添加员工'
      // 初始化数据
      this.showSelectPosId(true)
      this.getMaxWorkId();
    },

    // 政治面貌
    showSelectPoliticId(isShow){
      // 显示政治面貌的下拉选择框
      if(isShow){
        if(sessionStorage.getItem('politicIds')){
          this.politicIds = JSON.parse(sessionStorage.getItem('politicIds'))
        } else {
          this.getRequest('/employee/basic/politicsStatus').then(resp => {
            if(resp.code === 200){
              this.politicIds = resp.object
              sessionStorage.setItem('politicIds', JSON.stringify(resp.object))
            }
          })
        }
      }
    },
    // 民族
    showSelectNationId(isShow){
      if(isShow){
        if(sessionStorage.getItem('nationIds')){
          this.nationIds = JSON.parse(sessionStorage.getItem('nationIds'))
        } else {
          this.getRequest('/employee/basic/nations').then(resp => {
            if (resp.code === 200) {
              this.nationIds = resp.object
              sessionStorage.setItem('nationIds', JSON.stringify(resp.object))
            }
          })
        }
      }
    },
    // 职位：经常发生变化，不存储在sessionStorage中
    showSelectPosId(isShow){
      if(isShow){
          this.getRequest('/employee/basic/positions').then(resp => {
            if(resp.code === 200){
              this.posIds = resp.object
            }
          })
      }
    },
    // 职称
    showSelectJobLevelId(isShow){
      if(isShow){
        if(sessionStorage.getItem('jobLevelIds')){
          this.jobLevelIds = JSON.parse(sessionStorage.getItem('jobLevelIds'))
        } else {
          this.getRequest('/employee/basic/joblevels').then(resp => {
            if(resp.code === 200){
              this.jobLevelIds = resp.object
              sessionStorage.setItem('jobLevelIds', JSON.stringify(resp.object))
            }
          })
        }
      }
    },
    // 初始化树形结构部门
    initDeptTreeData(){
      if(sessionStorage.getItem('deptTreeData')){
        this.deptTreeData = JSON.parse(sessionStorage.getItem('deptTreeData'))
      } else {
        this.getRequest('/employee/basic/departments').then(resp => {
          if(resp.code === 200){
            this.deptTreeData = resp.object
            sessionStorage.setItem('deptTreeData', JSON.stringify(resp.object))
          }
        })
      }
    },

    // 清空添加对话框的选择、填写的数据
    clearAddDialog(){
      this.emp = {
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
        workState: '在职'
      }
      this.addDialogVisible = false;
      this.selectedDept = '请点击选择部门'
      this.selectDeptVisible = false;
    },
    // 添加员工和修改员工
    addEmp(){
      this.$refs['addEmpForm'].validate((valid) => {
        if(valid){
          // 存在id为修改
          if(this.emp.id){
            this.putRequest('/employee/basic/', this.emp).then(resp => {
              if(resp.code === 200){
                this.clearAddDialog()
                this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
              }
            })
          } else {
            // 添加员工
            this.postRequest('/employee/basic/', this.emp).then(resp => {
              if(resp.code === 200){
                this.clearAddDialog()
                this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
              }
            })
          }
        }
      })
    },
    // 点击选择部门，树形展示
    showDeptView(){
      this.selectDeptVisible = !this.selectDeptVisible
    },
    // 点击部门树节点，选中
    handleDeptNodeClick(data){
      this.selectedDept = data.name
      this.emp.departmentId = data.id
      this.selectDeptVisible = false
    },
    // 高级搜索中的选择部门
    handleDeptNodeSearchClick(data){
      this.searchValue.departmentId = data.id
      this.selectedSearchDept = data.name
      this.selectAdvanceDeptVisible = false
    },
    // 取消高级搜素
    cancelAdvancedSearch(){
      this.showAdvancedSearchVisible = false
      this.selectAdvanceDeptVisible = false
      this.selectedSearchDept = ''
      this.searchValue = {
        politicId: null,
        nationId: null,
        posId: null,
        jobLevelId: null,
        engageForm: null,
        departmentId: null,
        beginDateScope: null
      }
    },
    // 执行高级搜索
    doAdvancedSearch(){
      this.initEmpList('advancedSearch')
    },
    // 显示编辑框
    showEditDialog(data){
      this.dialogTitle = '编辑员工';
      this.emp = data
      this.selectedDept = data.department.name
      this.addDialogVisible = true;
    },
    // 删除员工信息
    deleteEmp(data){
      this.$confirm('此操作将永久删除员工【'+data.name+'】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/employee/basic/' + data.id).then(resp => {
          if(resp && resp.code === 200){
            this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
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
    this.initEmpList(this.showAdvancedSearchVisible ? 'advancedSearch' : 'withName')
    // 初始化添加时的数据
    this.showSelectJobLevelId(true)
    this.showSelectNationId(true)
    this.showSelectPoliticId(true)
    this.initDeptTreeData()
    // 职位
    this.showSelectPosId(true)
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
  height: 670px;
}
.buttonMenus {
  display: flex;
  justify-content: space-between;
  width: 245px;
}
.el-upload-dragger .el-icon-loading{
  font-size: 67px;
  color: royalblue;
  margin: 40px 0 16px;
  line-height: 50px;
}
.el-upload-dragger .el-icon-success{
  font-size: 67px;
  color: limegreen;
  margin: 40px 0 16px;
  line-height: 50px;
}
.el-upload-dragger .el-icon-error{
  font-size: 67px;
  color: indianred;
  margin: 40px 0 16px;
  line-height: 50px;
}

/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all .8s ease;
}
.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
  /* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateY(10px);
  opacity: 0;
}
</style>