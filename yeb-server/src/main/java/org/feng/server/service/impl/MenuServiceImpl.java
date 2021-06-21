package org.feng.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feng.server.entity.Admin;
import org.feng.server.entity.Menu;
import org.feng.server.mapper.MenuMapper;
import org.feng.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Menu> getMenusByAdminId() {
        Integer id = ((Admin) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId();
        ValueOperations<String, List<Menu>> valueOperations = redisTemplate.opsForValue();
        // 查询缓存
        List<Menu> menus = valueOperations.get("menu_" + id);
        if(CollUtil.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(id);
            valueOperations.set("menu_" + id, menus);
        }

        return menus;
    }


    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
}
