package org.feng.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.feng.server.entity.AdminRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 更新管理员角色
     * @param adminId 管理员id
     * @param rids 角色id数组
     * @return 响应结果
     */
    Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
