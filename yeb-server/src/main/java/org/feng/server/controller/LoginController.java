package org.feng.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.feng.server.entity.Admin;
import org.feng.server.entity.AdminLoginParam;
import org.feng.server.entity.ResponseBean;
import org.feng.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登陆控制器
 *
 * @author FengJinSong
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    /**
     * 登陆并返回结果
     * @param adminLoginParam 登陆参数，用户名、密码、验证码
     * @param request request对象
     * @return 登陆成功或失败的结果
     */
    @ApiOperation(value = "登陆并返回结果")
    @PostMapping("/login")
    public ResponseBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), adminLoginParam.getCode(), request);
    }

    @ApiOperation(value = "退出登陆")
    @PostMapping("/logout")
    public ResponseBean logout(){
        return ResponseBean.success("注销成功");
    }

    @ApiOperation(value = "获取当前登陆用户信息")
    @GetMapping("/admin/info")
    public ResponseBean getAdminInfo(Principal principal){
        if(principal == null){
            return null;
        }

        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return ResponseBean.response(200, "用户信息获取成功", admin);
    }
}
