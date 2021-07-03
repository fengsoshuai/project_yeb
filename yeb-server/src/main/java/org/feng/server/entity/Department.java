package org.feng.server.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  部门实体：重写hashcode和equals，name唯一并不能为空
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "name")
@Accessors(chain = true)
@TableName("t_department")
@ApiModel(value="Department对象", description="部门实体对象，包含子级部门")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NonNull
    @Excel(name = "部门名称")
    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "路径")
    private String depPath;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "是否上级")
    private Boolean isParent;

    @ApiModelProperty(value = "子部门列表")
    @TableField(exist = false)
    private List<Department> children;

    @ApiModelProperty(value = "添加返回结果，存储过程使用")
    @TableField(exist = false)
    private Integer result;
}
