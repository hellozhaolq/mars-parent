package com.zhaolq.mars.service.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_SYS_ROLE")
@ApiModel(value="RoleEntity对象", description="角色管理")
public class RoleEntity extends Model<RoleEntity> {

    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "角色名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "角色代码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "LAST_UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdateTime;

    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Byte status;

    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Byte delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
