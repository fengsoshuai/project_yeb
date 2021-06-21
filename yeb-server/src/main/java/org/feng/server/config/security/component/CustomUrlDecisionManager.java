package org.feng.server.config.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 验证URL是否有权限
 * @author FengJinSong
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            // 当前url所需角色
            String needRole = configAttribute.getAttribute();
            // 判断角色是否登陆即可访问的角色，在CustomFilter中设置
            if("ROLE_LOGIN".equals(needRole)){
                // 判断是否登陆
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请登陆");
                }
                return;
            }

            // 判断用户角色是否为url所需角色
            boolean hasAuth = authentication.getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.getAuthority().equals(needRole));
            if(hasAuth){
                return;
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
