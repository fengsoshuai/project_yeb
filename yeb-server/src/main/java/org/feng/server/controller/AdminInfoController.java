package org.feng.server.controller;

import io.swagger.annotations.ApiOperation;
import org.feng.consts.Consts;
import org.feng.server.entity.Admin;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.vo.AdminPwdVO;
import org.feng.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class AdminInfoController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation("更新用户个人信息")
    @PutMapping("/admin/info")
    public ResponseBean updateAdminInfo(@RequestBody Admin admin, Authentication authentication){
        if(adminService.updateById(admin)){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));

            return ResponseBean.success(Consts.UPDATE_SUCCESS);
        }
        return ResponseBean.error(Consts.UPDATE_FAILED);
    }

    @ApiOperation("更新用户密码")
    @PutMapping("/admin/pass")
    public ResponseBean updateAdminPassword(@RequestBody AdminPwdVO adminPwdVO){
        return adminService.updateAdminPassword(adminPwdVO);
    }


}
