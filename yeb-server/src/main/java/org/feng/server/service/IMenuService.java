package org.feng.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.feng.server.entity.Menu;

import java.util.List;

/**
 * <p>
 *  菜单业务类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 通过登陆用户id查询菜单列表
     * @return 菜单列表
     */
    List<Menu> getMenusByAdminId();

    /**
     * 通过用户角色查询菜单列表
     * @return 菜单列表
     */
    List<Menu> getMenusWithRole();

    /**
     * 获取所有菜单
     * @return 菜单列表
     */
    List<Menu> getAllMenus();
}
