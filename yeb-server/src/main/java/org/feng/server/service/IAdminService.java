package org.feng.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.feng.server.entity.Admin;
import org.feng.server.entity.Menu;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface IAdminService extends IService<Admin> {


    /**
     * 登陆操作，返回token等信息
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param request 请求对象
     * @return 响应ResponseBean
     */
    ResponseBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return admin对象
     */
    Admin getAdminByUserName(String username);

    /**
     * 根据用户id查询角色
     * @param adminId 用户id
     * @return 角色列表
     */
    List<Role> getRoles(Integer adminId);
}
