package org.feng.server.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  员工实体：日期格式化，支持easy-poi的导出
 * </p>
 *
 * @author FengJinSong
 * @since 2021-06-16
 */
@BeanHasCreateDate
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee")
@ApiModel(value="Employee对象")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "员工姓名")
    @ApiModelProperty(value = "员工姓名")
    private String name;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String gender;

    @Excel(name = "出生日期", width = 20, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @Excel(name = "身份证号", width = 18)
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @Excel(name = "婚姻状况")
    @ApiModelProperty(value = "婚姻状况")
    private String wedlock;

    @ApiModelProperty(value = "民族")
    private Integer nationId;

    @Excel(name = "籍贯")
    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "政治面貌")
    private Integer politicId;

    @Excel(name = "邮箱", width = 30)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Excel(name = "电话号码", width = 15)
    @ApiModelProperty(value = "电话号码")
    private String phone;

    @Excel(name = "联系地址", width = 40)
    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "所属部门")
    private Integer departmentId;

    @ApiModelProperty(value = "职称ID")
    private Integer jobLevelId;

    @ApiModelProperty(value = "职位ID")
    private Integer posId;

    @Excel(name = "聘用形式")
    @ApiModelProperty(value = "聘用形式")
    private String engageForm;

    @Excel(name = "最高学历")
    @ApiModelProperty(value = "最高学历")
    private String tiptopDegree;

    @Excel(name = "所属专业")
    @ApiModelProperty(value = "所属专业")
    private String specialty;

    @Excel(name = "毕业院校")
    @ApiModelProperty(value = "毕业院校")
    private String school;

    @Excel(name = "入职日期", width = 20, format = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职日期")
    private LocalDate beginDate;

    @Excel(name = "在职状态")
    @ApiModelProperty(value = "在职状态")
    private String workState;

    @Excel(name = "工号")
    @ApiModelProperty(value = "工号")
    @TableField(value = "workID")
    private String workId;

    @Excel(name = "合同期限", suffix = "年")
    @ApiModelProperty(value = "合同期限")
    private Double contractTerm;

    @Excel(name = "转正日期", width = 20, format = "yyyy-MM-dd")
    @ApiModelProperty(value = "转正日期")
    private LocalDate conversionTime;

    @Excel(name = "离职日期", width = 20, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "离职日期")
    private LocalDate notWorkDate;

    @Excel(name = "合同起始日期", width = 20, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "合同起始日期")
    private LocalDate beginContract;

    @Excel(name = "合同终止日期", width = 20, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "合同终止日期")
    private LocalDate endContract;

    @Excel(name = "工龄")
    @ApiModelProperty(value = "工龄")
    private Integer workAge;

    @ApiModelProperty(value = "工资账套ID")
    private Integer salaryId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ExcelEntity(name = "民族")
    @ApiModelProperty(value = "民族")
    @TableField(exist = false)
    private Nation nation;

    @ExcelEntity(name = "政治面貌")
    @ApiModelProperty(value = "政治面貌")
    @TableField(exist = false)
    private PoliticsStatus politicsStatus;

    @ExcelEntity(name = "部门")
    @ApiModelProperty(value = "部门")
    @TableField(exist = false)
    private Department department;

    @ExcelEntity(name = "职称")
    @ApiModelProperty(value = "职称")
    @TableField(exist = false)
    private Joblevel joblevel;

    @ExcelEntity(name = "职位")
    @ApiModelProperty(value = "职位")
    @TableField(exist = false)
    private Position position;
}
