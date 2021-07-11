package org.feng.server.controller;


import io.swagger.annotations.ApiOperation;
import org.feng.server.entity.Menu;
import org.feng.server.service.IAdminService;
import org.feng.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * 查询当前登陆用户对应的菜单列表
     * @return 菜单列表
     */
    @ApiOperation(value = "通过登陆用户的id查询菜单列表")
    @GetMapping("/menus")
    public List<Menu> getMenusByAdminId(){
        return menuService.getMenusByAdminId();
    }

    /**
     * 查询当前登陆用户角色对应的菜单列表
     * @return 菜单列表
     */
    @ApiOperation(value = "通过用户角色查询菜单列表")
    @GetMapping("/menu/role")
    public List<Menu> getMenusWithRole(){
        return menuService.getMenusWithRole();
    }
}
