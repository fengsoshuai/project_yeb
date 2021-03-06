package org.feng.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.feng.server.entity.Admin;
import org.feng.server.entity.Menu;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.Role;
import org.feng.server.entity.vo.AdminPwdVO;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  管理员业务类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 获取当前登陆的管理员
     * @return Admin
     */
    static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

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

    /**
     * 获取所有管理员
     * @param keywords 搜索词
     * @return 管理员列表
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * 更新管理员角色，先删除所有对应的角色，再插入对应的角色数据
     * @param adminId 管理员id
     * @param rids 角色id数组
     * @return 响应结果
     */
    ResponseBean updateAdminRole(Integer adminId, Integer[] rids);

    /**
     * 修改管理员密码
     * @param adminPwdVO 管理员密码实体vo
     * @return 响应结果
     */
    ResponseBean updateAdminPassword(AdminPwdVO adminPwdVO);
}
