package org.feng.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * 响应对象
 * @author FengJinSong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {

    private long code;
    private String message;
    private Object object;



    public static ResponseBean success(String message){
        return response(200, message, null);
    }


    public static ResponseBean error(String message){
        return response(500, message, null);
    }

    public static ResponseBean response(long code, String message, Object object){
        return new ResponseBean(code, message, object);
    }
}
