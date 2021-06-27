package org.feng.server.controller;

import io.swagger.annotations.ApiOperation;
import org.feng.consts.Consts;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.Role;
import org.feng.server.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation("添加角色")
    @PostMapping("/")
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
}
