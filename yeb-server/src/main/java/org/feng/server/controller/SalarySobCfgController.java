package org.feng.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.ApiOperation;
import org.feng.consts.Consts;
import org.feng.server.entity.Employee;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.ResponsePageBean;
import org.feng.server.service.IEmployeeService;
import org.feng.server.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 员工账套
 *
 * @author FengJinSong
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation("获取所有的工资账套")
    @GetMapping("/salaries")
    public ResponseBean getAllSalaries(){
        return ResponseBean.ok(salaryService.list());
    }

    @ApiOperation("获取所有员工的账套")
    @GetMapping("/")
    public ResponsePageBean getEmpWithSalary(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "15") Integer size){
        return employeeService.getEmpWithSalary(currentPage, size);
    }

    @ApiOperation("更新员工账套")
    @PutMapping("/")
    public ResponseBean updateEmpWithSalary(Integer eid, Integer sid){
        if(employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid))){
            return ResponseBean.success(Consts.UPDATE_SUCCESS);
        }
        return ResponseBean.error(Consts.UPDATE_FAILED);
    }
}
