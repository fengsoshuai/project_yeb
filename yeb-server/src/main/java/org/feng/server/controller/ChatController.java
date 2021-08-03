package org.feng.server.controller;

import io.swagger.annotations.ApiOperation;
import org.feng.server.entity.ResponseBean;
import org.feng.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线聊天
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation("获取所有操作员")
    @GetMapping("/admin")
    public ResponseBean getAllAdmins(String keywords){
        return ResponseBean.ok(adminService.getAllAdmins(keywords));
    }
}
