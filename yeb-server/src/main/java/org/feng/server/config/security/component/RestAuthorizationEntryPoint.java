package org.feng.server.config.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.feng.server.entity.ResponseBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登陆或者token失效时访问接口，自定义的返回结果
 * @author FengJinSong
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        responseBean(response, 401, "尚未登陆，请登录");
    }

    /**
     * 响应数据
     * @param response http的response对象
     * @param code 信息码，比如 401,500
     * @param message 提示信息
     */
    static void responseBean(HttpServletResponse response, int code, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        ResponseBean bean = ResponseBean.response(code, message, null);
        writer.write(new ObjectMapper().writeValueAsString(bean));
        writer.flush();
        writer.close();
    }
}
