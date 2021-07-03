package org.feng.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.feng.server.entity.Employee;

import java.time.LocalDate;

/**
 * <p>
 *  员工的Mapper 接口
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工（分页展示）
     * @param employeePage 分页数据
     * @param employee 员工
     * @param beginDateScope 入职时间区域
     * @return 分页响应对象
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> employeePage, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);




}
