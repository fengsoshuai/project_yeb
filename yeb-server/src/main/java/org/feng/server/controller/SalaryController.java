package org.feng.server.controller;


import io.swagger.annotations.ApiOperation;
import org.feng.server.config.pattern.TemplateMethodController;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.Salary;
import org.feng.server.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  工资账套前端控制器
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController extends TemplateMethodController<Salary> {

    @Autowired
    private ISalaryService salaryService;

    @ApiOperation("获取所有的工资账套")
    @GetMapping("/")
    public ResponseBean getAllSalaries(){
        return super.getAllWithResponse();
    }

    @ApiOperation("增加工资账套")
    @PostMapping("/")
    public ResponseBean addSalary(@RequestBody Salary salary){
        return super.add(salary);
    }

    @ApiOperation("修改工资账套")
    @PutMapping("/")
    public ResponseBean updateSalary(@RequestBody Salary salary){
        return super.update(salary);
    }

    @ApiOperation("删除工资账套")
    @DeleteMapping("/{id}")
    public ResponseBean deleteSalaryById(@PathVariable Integer id){
        return super.delete(id);
    }

    @Override
    protected void initService() {
        if(super.getService() == null){
            super.setService(salaryService);
        }
    }
}
