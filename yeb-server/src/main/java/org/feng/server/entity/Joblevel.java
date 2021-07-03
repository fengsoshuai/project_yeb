package org.feng.server.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 *  职称实体
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@Data
@BeanHasCreateDate
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_joblevel")
@ApiModel(value="Joblevel对象")
public class Joblevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "职称名称")
    @ApiModelProperty(value = "职称名称")
    private String name;

    @ApiModelProperty(value = "职称等级")
    private String titleLevel;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;


}
