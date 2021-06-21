package org.feng.server.config.security.component;

import org.feng.server.entity.Menu;
import org.feng.server.entity.Role;
import org.feng.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 权限控制
 * 根据请求的url分析请求所需的角色
 * @author FengJinSong
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.getMenusWithRole();

        Optional<String[]> result = Optional.of(
                menus.stream().filter(menu -> antPathMatcher.match(menu.getUrl(), requestUrl))
                    .map(Menu::getRoles)
                        .flatMap(Collection::stream)
                        .map(Role::getName)
                        .toArray(String[]::new)
        );
        return SecurityConfig.createList(result.orElse(new String[]{"ROLE_LOGIN"}));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
