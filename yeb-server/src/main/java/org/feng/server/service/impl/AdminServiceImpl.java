package org.feng.server.service.impl;

import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feng.consts.Consts;
import org.feng.server.config.security.component.JwtTokenUtil;
import org.feng.server.entity.Admin;
import org.feng.server.entity.AdminRole;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.Role;
import org.feng.server.mapper.AdminMapper;
import org.feng.server.mapper.AdminRoleMapper;
import org.feng.server.mapper.RoleMapper;
import org.feng.server.service.IAdminService;
import org.feng.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ResponseBean login(String username, String password, String code, HttpServletRequest request) {
        if(StringUtil.isEmpty(code)){
            return ResponseBean.error("验证码不能为空");
        }
        // 校验验证码
        // Object captcha = request.getSession().getAttribute("captcha");
        // 验证码从redis获取
        Object captcha = redisTemplate.opsForValue().get(String.valueOf(code.hashCode()));
        if(Objects.isNull(captcha)){
            return ResponseBean.error("验证码错误");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 用户名或密码校验不通过
        if(userDetails == null || passwordEncoder.matches(password, userDetails.getPassword())){
            return ResponseBean.error("用户名或密码不正确");
        }
        // 账号被禁用
        if(!userDetails.isEnabled()){
            return ResponseBean.error("账号被禁用，请联系管理员");
        }

        // 更新 security 登陆用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 登陆成功
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>(16);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ResponseBean.response(200, "登陆成功", tokenMap);
    }

    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
    }

    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    @Override
    public List<Admin> getAllAdmins(String keywords) {
        return adminMapper.getAllAdmins(IAdminService.getCurrentAdmin().getId(), keywords);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseBean updateAdminRole(Integer adminId, Integer[] rids) {
        // 删除所有权限
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        // 去掉所有权限时
        if(ArrayUtil.isEmpty(rids)){
            return ResponseBean.success(Consts.UPDATE_SUCCESS);
        }

        Integer rows = adminRoleMapper.addAdminRole(adminId, rids);
        if(rows == rids.length){
            return ResponseBean.success(Consts.UPDATE_SUCCESS);
        }
        return ResponseBean.error(Consts.UPDATE_FAILED);
    }
}
