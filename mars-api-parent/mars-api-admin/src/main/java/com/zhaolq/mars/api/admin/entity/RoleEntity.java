package com.zhaolq.mars.api.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.common.valid.group.Remove;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_BASE_ROLE")
@Schema(description = "角色管理")
public class RoleEntity extends Model<RoleEntity> {

    @JsonProperty("id")
    @NotNull(groups = {Edit.class, Remove.class}, message = "id缺失")
    @Schema(description = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @NotNull(groups = {Add.class}, message = "角色名称缺失")
    @Schema(description = "角色名称")
    @TableField("NAME")
    private String name;

    @NotNull(groups = {Add.class}, message = "角色代码缺失")
    @Schema(description = "角色代码")
    @TableField("CODE")
    private String code;

    @Schema(description = "备注")
    @TableField("REMARK")
    private String remark;

    @Schema(description = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    @TableField(value = "LAST_UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdateTime;

    @NotNull(groups = {Add.class}, message = "状态缺失")
    @Schema(description = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Byte status;

    @NotNull(groups = {Add.class}, message = "是否删除缺失")
    @Schema(description = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Byte delFlag;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

    /************* 以上对应数据库字段 *************/

    /**
     * 一对多
     */
    @TableField(exist = false)
    @EqualsAndHashCode.Exclude
    private List<MenuEntity> menuList;

    /**
     * 一对多
     */
    @TableField(exist = false)
    @EqualsAndHashCode.Exclude
    private List<DeptEntity> deptList;

}
