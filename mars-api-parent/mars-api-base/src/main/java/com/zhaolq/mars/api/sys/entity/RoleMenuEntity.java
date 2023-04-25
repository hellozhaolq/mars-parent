package com.zhaolq.mars.api.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_BASE_ROLE_MENU")
@Schema(description = "角色菜单")
public class RoleMenuEntity extends Model<RoleMenuEntity> {

    @Schema(description = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "角色ID")
    @TableField("ROLE_ID")
    private String roleId;

    @Schema(description = "菜单ID")
    @TableField("MENU_ID")
    private String menuId;

    @Schema(description = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @Schema(description = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Schema(description = "更新时间")
    @TableField("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
