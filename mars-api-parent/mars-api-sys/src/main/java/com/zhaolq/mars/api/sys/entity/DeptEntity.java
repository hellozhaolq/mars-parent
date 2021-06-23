package com.zhaolq.mars.api.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 机构管理
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_SYS_DEPT")
@ApiModel(value="DeptEntity对象", description="机构管理")
public class DeptEntity extends Model<DeptEntity> {

    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "机构名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "机构代码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "类型【1：集团；2：学校 】")
    @TableField("TYPE")
    private Integer type;

    @ApiModelProperty(value = "子类型")
    @TableField("SUBTYPE")
    private String subtype;

    @ApiModelProperty(value = "上级机构ID，一级机构为0")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "排序")
    @TableField("ORDER_NUM")
    private Long orderNum;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;

    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
