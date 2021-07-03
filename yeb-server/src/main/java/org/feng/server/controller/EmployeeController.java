package org.feng.server.controller;


import io.swagger.annotations.ApiOperation;
import org.feng.server.entity.Employee;
import org.feng.server.entity.PoliticsStatus;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.ResponsePageBean;
import org.feng.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 *  员工管理控制器
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/employee/basic/")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation("获取所有员工（分页展示）")
    @GetMapping("/")
    public ResponsePageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        Employee employee,
                                        LocalDate[] beginDateScope
                                    ){
        return employeeService.getEmployeeByPage(currentPage, size, employee, beginDateScope);
    }

    @ApiOperation("获取所有政治面貌")
    @GetMapping("/politicsStatus")
    public ResponseBean getAllPoliticsStatus(){
        return ResponseBean.ok(politicsStatusService.list());
    }

    @ApiOperation("获取所有职称")
    @GetMapping("/joblevels")
    public ResponseBean getAllJoblevels(){
        return ResponseBean.ok(joblevelService.list());
    }

    @ApiOperation("获取所有民族")
    @GetMapping("/nations")
    public ResponseBean getAllNations(){
        return ResponseBean.ok(nationService.list());
    }

    @ApiOperation("获取所有职位")
    @GetMapping("/positions")
    public ResponseBean getAllPositions(){
        return ResponseBean.ok(positionService.list());
    }

    @ApiOperation("获取所有部门")
    @GetMapping("/departments")
    public ResponseBean getAllDepartments(){
        return ResponseBean.ok(departmentService.getAllDepartments());
    }

    @ApiOperation("获取工号")
    @GetMapping("/maxWorkId")
    public ResponseBean getMaxWorkId(){
        return employeeService.getMaxWorkId();
    }

    @PutMapping("/")
    @ApiOperation("添加员工")
    public ResponseBean addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }
}
