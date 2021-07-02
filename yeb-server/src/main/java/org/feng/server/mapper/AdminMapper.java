package org.feng.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.feng.server.entity.Admin;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface AdminMapper extends BaseMapper<Admin> {


    /**
     * 获取所有管理员
     * @param keywords 搜索词
     * @param id 当前登陆用户id
     * @return 管理员列表
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);
}
