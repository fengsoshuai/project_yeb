package org.feng.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.feng.consts.Consts;

/**
 * 响应对象
 * @author FengJinSong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {

    /**响应码*/
    private long code;
    /**响应消息*/
    private String message;
    /**响应对象*/
    private Object object;

    /**
     * 操作成功 code=200
     * @param message 提示词，响应消息
     * @return 响应对象
     */
    public static ResponseBean success(String message){
        return response(200, message, null);
    }

    /**
     * 操作失败 code=500
     * @param message 提示词，响应消息
     * @return 响应对象
     */
    public static ResponseBean error(String message){
        return response(500, message, null);
    }

    /***
     * 响应操作
     * @param code 响应码
     * @param message 提示信息
     * @param object 响应的数据对象
     * @return 响应对象
     */
    public static ResponseBean response(long code, String message, Object object){
        return new ResponseBean(code, message, object);
    }

    /**
     * 操作成功，并返回数据
     * @param data 数据
     * @return 响应对象
     */
    public static ResponseBean ok(Object data){
        return new ResponseBean(200, Consts.SUCCESS, data);
    }

    /**
     * 操作失败，并返回数据
     * @param data 数据
     * @return 响应对象
     */
    public static ResponseBean fail(Object data){
        return new ResponseBean(500, Consts.ERROR, data);
    }
}
