package org.feng.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.feng.server.entity.Department;
import org.feng.server.entity.ResponseBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return Departments
     */
    List<Department> getAllDepartments();

    /**
     * 添加部门
     * @param department 数据
     * @return 响应结果
     */
    ResponseBean addDepartment(Department department);

    /**
     * 删除部门
     * @param id 部门id
     * @return 响应结果
     */
    ResponseBean deleteDepartment(Integer id);
}
