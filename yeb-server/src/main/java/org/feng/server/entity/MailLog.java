package org.feng.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.feng.server.config.annotation.BeanHasCreateDate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@BeanHasCreateDate
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_mail_log")
@ApiModel(value="MailLog对象")
public class MailLog implements Serializable {

    private static final long serialVersionUID = -7304864001613921730L;

    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty(value = "接收员工id")
    private Integer eid;

    @ApiModelProperty(value = "状态（0:消息投递中 1:投递成功 2:投递失败）")
    private Integer status;

    @ApiModelProperty(value = "路由键")
    private String routeKey;

    @ApiModelProperty(value = "交换机")
    private String exchange;

    @ApiModelProperty(value = "重试次数")
    private Integer count;

    @ApiModelProperty(value = "重试时间")
    private LocalDateTime tryTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
