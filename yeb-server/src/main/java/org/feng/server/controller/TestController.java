package org.feng.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * @author FengJinSong
 */
@RestController
public class TestController {

    /**
     *
     * 在t_menu中，url
     * 系统管理员ROLE_admin拥有该角色
     * @return
     */
    @GetMapping("/employee/basic/7")
    public String hello(){
        return "/employee/basic/7";
    }


    /**
     *
     * 在t_menu中，url
     * 系统管理员ROLE_admin没有该角色
     * @return
     */
    @GetMapping("/employee/advanced/8")
    public String hello2(){
        return "/employee/advanced/8";
    }



}
