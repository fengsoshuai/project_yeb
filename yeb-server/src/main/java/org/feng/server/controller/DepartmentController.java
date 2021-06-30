package org.feng.server.controller;


import io.swagger.annotations.ApiOperation;
import org.feng.server.entity.Department;
import org.feng.server.entity.ResponseBean;
import org.feng.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  部门管理
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping("/")
    @ApiOperation("获取所有部门")
    public ResponseBean getAllDepartments(){
        return ResponseBean.ok(departmentService.getAllDepartments());
    }

    @ApiOperation("添加部门")
    @PutMapping("/")
    public ResponseBean addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/{id}")
    public ResponseBean deleteDepartment(@PathVariable Integer id){
        return departmentService.deleteDepartment(id);
    }
}
