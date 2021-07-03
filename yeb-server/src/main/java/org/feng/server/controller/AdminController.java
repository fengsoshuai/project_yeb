package org.feng.server.controller;


import io.swagger.annotations.ApiModelProperty;
import org.feng.server.config.pattern.TemplateMethodController;
import org.feng.server.entity.Admin;
import org.feng.server.entity.ResponseBean;
import org.feng.server.service.IAdminService;
import org.feng.server.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  管理员控制器
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController extends TemplateMethodController<Admin> {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;

    @GetMapping("/")
    @ApiModelProperty("获取所有的管理员")
    public List<Admin> getAllAdmins(String keywords){
        return adminService.getAllAdmins(keywords);
    }


    @ApiModelProperty("修改管理员")
    @PutMapping("/")
    public ResponseBean updateAdmin(@RequestBody Admin admin){
        return super.update(admin);
    }

    @ApiModelProperty("删除管理员")
    @DeleteMapping("/{id}")
    public ResponseBean deleteAdmin(@PathVariable Integer id){
        return super.delete(id);
    }

    @ApiModelProperty("获取所有角色")
    @GetMapping("/roles")
    public ResponseBean getAllRoles(){
        return ResponseBean.ok(roleService.list());
    }

    @ApiModelProperty("更新管理员角色")
    @PutMapping("/role")
    public ResponseBean updateAdminRole(Integer adminId, Integer[] rids){
        return adminService.updateAdminRole(adminId, rids);
    }

    @Override
    protected void initService() {
        if(super.getService() == null){
            super.setService(adminService);
        }
    }
}
