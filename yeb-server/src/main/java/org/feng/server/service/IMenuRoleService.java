package org.feng.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.feng.server.entity.MenuRole;
import org.feng.server.entity.ResponseBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface IMenuRoleService extends IService<MenuRole> {


    /**
     * 更新角色菜单
     * @param rid 角色id
     * @param mids 菜单id
     * @return 更新结果
     */
    ResponseBean updateMenuRole(Integer rid, Integer[] mids);
}
