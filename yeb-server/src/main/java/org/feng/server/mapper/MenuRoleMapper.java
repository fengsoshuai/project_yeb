package org.feng.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.feng.server.entity.MenuRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid 角色id
     * @param mids 菜单id列表
     * @return 操作结果
     */
    Integer insertRecord(Integer rid, Integer[] mids);
}
