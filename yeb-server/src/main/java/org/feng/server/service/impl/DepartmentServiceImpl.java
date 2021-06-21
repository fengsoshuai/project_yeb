package org.feng.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feng.server.entity.Department;
import org.feng.server.mapper.DepartmentMapper;
import org.feng.server.service.IDepartmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
