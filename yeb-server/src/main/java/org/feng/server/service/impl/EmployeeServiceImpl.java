package org.feng.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feng.consts.Consts;
import org.feng.server.entity.Employee;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.ResponsePageBean;
import org.feng.server.mapper.EmployeeMapper;
import org.feng.server.service.IEmployeeService;
import org.feng.util.DecimalFormatUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * <p>
 *  员工业务实现类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ResponsePageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> employeePage = new Page<>(currentPage, size);
        IPage<Employee> page = employeeMapper.getEmployeeByPage(employeePage, employee, beginDateScope);
        return ResponsePageBean.ok(page.getTotal(), page.getRecords());
    }

    @Override
    public ResponseBean getMaxWorkId() {
        // 查询当前最大工号
        List<Object> objects = employeeMapper.selectObjs(new QueryWrapper<Employee>().select("max(workId)"));
        // 最大工号+1，格式化
        return ResponseBean.ok(String.format("%08d", Integer.parseInt(objects.get(0).toString()) + 1));

    }

    @Override
    public ResponseBean addEmployee(Employee employee) {
        // 处理合同期限，保留2位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        // 计算合同开始到合同结束的天数
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        employee.setContractTerm(Double.valueOf(DecimalFormatUtil.format(days / 365.00)));

        if(1 == employeeMapper.insert(employee)){
            Employee emp = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("name", employee.getName()).orderByDesc("createDate"));
            rabbitTemplate.convertAndSend("mail.welcome", emp);
            return ResponseBean.success(Consts.ADD_SUCCESS);
        }
        return ResponseBean.error(Consts.ADD_FAILED);
    }

    @Override
    public List<Employee> getEmployeeForExcel(Integer[] ids) {
        return employeeMapper.getEmployeeForExcel(ids);
    }

    @Override
    public ResponsePageBean getEmpWithSalary(Integer currentPage, Integer size) {
        Page<Employee> employeePage = new Page<>(currentPage, size);
        IPage<Employee> page = employeeMapper.getEmpWithSalary(employeePage);
        return ResponsePageBean.ok(page.getTotal(), page.getRecords());
    }
}
