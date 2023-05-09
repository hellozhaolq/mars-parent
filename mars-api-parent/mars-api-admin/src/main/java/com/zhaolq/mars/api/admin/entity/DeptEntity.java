package com.zhaolq.mars.api.admin.entity;

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
 * 机构管理
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_BASE_DEPT")
@Schema(description = "机构管理")
public class DeptEntity extends Model<DeptEntity> {

    @Schema(description = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "机构名称")
    @TableField("NAME")
    private String name;

    @Schema(description = "机构代码")
    @TableField("CODE")
    private String code;

    @Schema(description = "备注")
    @TableField("REMARK")
    private String remark;

    @Schema(description = "类型【1：集团；2：学校 】")
    @TableField("TYPE")
    private Integer type;

    @Schema(description = "子类型")
    @TableField("SUBTYPE")
    private String subtype;

    @Schema(description = "上级机构ID，一级机构为0")
    @TableField("PARENT_ID")
    private String parentId;

    @Schema(description = "排序")
    @TableField("ORDER_NUM")
    private Long orderNum;

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

    @Schema(description = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Integer status;

    @Schema(description = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Integer delFlag;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
