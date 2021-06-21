package org.feng.server.service.impl;

import cn.hutool.captcha.generator.MathGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feng.server.config.security.component.JwtTokenUtil;
import org.feng.server.entity.Admin;
import org.feng.server.entity.ResponseBean;
import org.feng.server.entity.Role;
import org.feng.server.mapper.AdminMapper;
import org.feng.server.mapper.RoleMapper;
import org.feng.server.service.IAdminService;
import org.feng.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public ResponseBean login(String username, String password, String code, HttpServletRequest request) {
        // 校验验证码
        Object captcha = request.getSession().getAttribute("captcha");
        if(Objects.nonNull(captcha)){
            if(StringUtil.isEmpty(code)){
                return ResponseBean.error("验证码不能为空");
            }
            boolean verifyCode = new MathGenerator().verify(captcha.toString(), code);
            if(!verifyCode){
                return ResponseBean.error("验证码错误");
            }
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
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
    }

    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }
}