package org.feng.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.feng.server.entity.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过登陆用户id查询菜单列表
     * @param id 用户id
     * @return 单列表
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 通过用户角色查询菜单列表
     * @return 菜单列表
     */
    List<Menu> getMenusWithRole();
}
