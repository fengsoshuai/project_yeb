package org.feng.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feng.consts.Consts;
import org.feng.server.entity.Department;
import org.feng.server.entity.ResponseBean;
import org.feng.server.mapper.DepartmentMapper;
import org.feng.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        if(1 == department.getResult()){
            return ResponseBean.response(200, Consts.ADD_SUCCESS, department);
        }
        return ResponseBean.error(Consts.ADD_FAILED);
    }

    @Override
    public ResponseBean deleteDepartment(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        return parseDeleteResult().getOrDefault(department.getResult(), ResponseBean.error(Consts.DELETE_FAILED));
    }

    /**
     * 列举删除结果状态
     * @return 删除响应集合
     */
    private Map<Integer, ResponseBean> parseDeleteResult(){
        Map<Integer, ResponseBean> result = new HashMap<>(16);
        result.put(-2, ResponseBean.error("该部门下有子部门，删除失败！"));
        result.put(-1, ResponseBean.error("该部门下有员工，删除失败！"));
        result.put(1, ResponseBean.success(Consts.DELETE_SUCCESS));
        return result;
    }
}
