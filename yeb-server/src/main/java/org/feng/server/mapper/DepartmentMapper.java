package org.feng.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.feng.server.entity.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取全部部门
     * @param parentId 父级id
     * @return 部门列表
     */
    List<Department> getAllDepartmentsByParentId(Integer parentId);

    /**
     * 添加部门
     * @param department 部门
     */
    void addDepartment(Department department);


    /**
     * 删除部门
     * @param department 部门
     */
    void deleteDepartment(Department department);
}
