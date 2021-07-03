package org.feng.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.feng.server.entity.Employee;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.ResponsePageBean;

import java.time.LocalDate;

/**
 * <p>
 *  员工业务类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取所有员工（分页展示）
     * @param currentPage 当前页
     * @param size 每页大小
     * @param employee 员工
     * @param beginDateScope 入职时间区域
     * @return 分页响应对象
     */
    ResponsePageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取工号
     * @return 当前最大工号加1
     */
    ResponseBean getMaxWorkId();

    /**
     * 添加员工
     * @param employee 员工实体
     * @return 响应结果
     */
    ResponseBean addEmployee(Employee employee);
}
