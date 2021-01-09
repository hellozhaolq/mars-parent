package com.zhaolq.mars.service.sys.entity;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_SYS_ROLE_MENU")
@ApiModel(value="RoleMenuEntity对象", description="角色菜单")
public class RoleMenuEntity extends Model<RoleMenuEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private BigDecimal id;

    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private BigDecimal roleId;

    @ApiModelProperty(value = "菜单ID")
    @TableField("MENU_ID")
    private BigDecimal menuId;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
