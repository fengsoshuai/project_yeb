package org.feng.server.exception;

import org.feng.server.entity.ResponseBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常捕捉处理
 * @author FengJinSong
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * 捕捉并处理 SQLException 及其子异常
     * @param exception SQLException实例
     * @return ResponseBean 友好的提示
     */
    @ExceptionHandler(SQLException.class)
    public ResponseBean mySqlException(SQLException exception){
        if(exception instanceof SQLIntegrityConstraintViolationException){
            return ResponseBean.error("该数据有关联数据，操作失败");
        }

        return ResponseBean.error("数据库异常，操作失败");
    }

    /**
     * 用户登陆失败：
     * <ul>
     *     <li>用户名或密码不正确</li>
     * </ul>
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseBean authentication(AuthenticationException exception){
        if(exception instanceof UsernameNotFoundException){
            return ResponseBean.error(exception.getMessage());
        }
        return ResponseBean.error("用户登陆失败");
    }

}
