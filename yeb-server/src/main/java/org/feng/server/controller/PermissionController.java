package org.feng.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.feng.consts.Consts;
import org.feng.server.entity.Menu;
import org.feng.server.entity.MenuRole;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.Role;
import org.feng.server.service.IMenuRoleService;
import org.feng.server.service.IMenuService;
import org.feng.server.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 权限组控制器
 * <p>角色管理</p>
 * @author FengJinSong
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation("添加角色")
    @PostMapping("/role")
    public ResponseBean addRole(@RequestBody Role role){
        if(!role.getName().startsWith(Consts.PERMISSION_PREFIX)){
            role.setName(Consts.PERMISSION_PREFIX + role.getName());
        }
        if(roleService.save(role)){
            return ResponseBean.success(Consts.ADD_SUCCESS);
        }
        return ResponseBean.error(Consts.ADD_FAILED);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{rid}")
    public ResponseBean deleteRole(@PathVariable Integer rid){
        if(roleService.removeById(rid)){
            return ResponseBean.success(Consts.DELETE_SUCCESS);
        }
        return ResponseBean.error(Consts.DELETE_FAILED);
    }

    @GetMapping("/menus")
    @ApiOperation("获取菜单")
    public ResponseBean getAllMenus(){
        return ResponseBean.response(200, Consts.SUCCESS, menuService.getAllMenus());
    }


    @GetMapping("/menu/{rid}")
    @ApiOperation("根据角色id获取菜单id")
    public ResponseBean getMenusIdByRole(@PathVariable Integer rid){
        return ResponseBean.response(200, Consts.SUCCESS, menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid)).stream().map(MenuRole::getMid).collect(Collectors.toList()));
    }

    @ApiOperation("更新角色菜单")
    @PutMapping("/")
    public ResponseBean updateMenuRole(Integer rid, Integer[] mids){
        return menuRoleService.updateMenuRole(rid, mids);
    }
}
