package org.feng.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.feng.consts.Consts;

import java.util.List;

/**
 * 分页响应对象
 * @author FengJinSong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePageBean {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 数据列表
     */
    private List<?> data;

    /**
     * 提示词
     */
    private String message;
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 成功响应
     * @param total 总条数
     * @param data 数据
     * @return 响应结果
     */
    public static ResponsePageBean ok(Long total, List<?> data){
        return new ResponsePageBean(total, data, Consts.SUCCESS, 200);
    }


    /**
     * 失败响应
     * @return 响应结果
     */
    public static ResponsePageBean error(){
        return new ResponsePageBean(0L, null, Consts.ERROR, 500);
    }
}
