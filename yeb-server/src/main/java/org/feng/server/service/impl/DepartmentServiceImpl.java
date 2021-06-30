package org.feng.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feng.server.entity.Department;
import org.feng.server.entity.ResponseBean;
import org.feng.server.mapper.DepartmentMapper;
import org.feng.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  部门业务实现
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @Override
    public ResponseBean addDepartment(Department department) {
        return null;
    }

    @Override
    public ResponseBean deleteDepartment(Integer id) {
        return null;
    }
}
