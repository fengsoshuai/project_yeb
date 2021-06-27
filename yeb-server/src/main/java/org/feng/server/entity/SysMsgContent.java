package org.feng.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_sys_msg_content")
@ApiModel(value="SysMsgContent对象")
public class SysMsgContent implements Serializable {

    private static final long serialVersionUID = -3042208789085119050L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;
}
