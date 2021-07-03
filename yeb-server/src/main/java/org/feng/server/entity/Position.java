package org.feng.server.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.feng.server.config.annotation.BeanHasCreateDate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 职位：重写hashcode和equals，name唯一并不能为空
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@BeanHasCreateDate
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "name")
@Accessors(chain = true)
@TableName("t_position")
@ApiModel(value="Position对象")
public class Position implements Serializable {

    private static final long serialVersionUID = 8351287084052024193L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NonNull
    @Excel(name = "职位")
    @ApiModelProperty(value = "职位")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;


}
