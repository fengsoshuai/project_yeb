package org.feng.server.entity.vo;

import lombok.Data;

/**
 * 视图传输类：用于修改密码数据传输
 */
@Data
public class AdminPwdVO {
    // 管理员id
    private Integer adminId;
    // 旧密码
    private String oldPass;
    // 新密码
    private String pass;
}
