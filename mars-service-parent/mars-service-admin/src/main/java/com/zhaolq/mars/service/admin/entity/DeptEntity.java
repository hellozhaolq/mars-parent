package com.zhaolq.mars.service.admin.entity;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 机构管理
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity.Table("T_BASE_DEPT")
@Schema(description = "机构管理")
public class DeptEntity {
    @Schema(description = "编号")
    @Entity.Column(id = true)
    private String id;

    @Schema(description = "机构名称")
    @Entity.Column("NAME")
    private String name;

    @Schema(description = "机构代码")
    @Entity.Column("CODE")
    private String code;

    @Schema(description = "备注")
    @Entity.Column("REMARK")
    private String remark;

    @Schema(description = "类型【1：集团；2：学校 】")
    @Entity.Column("TYPE")
    private Integer type;

    @Schema(description = "子类型")
    @Entity.Column("SUBTYPE")
    private String subtype;

    @Schema(description = "上级机构ID，一级机构为0")
    @Entity.Column("PARENT_ID")
    private String parentId;

    @Schema(description = "排序")
    @Entity.Column("ORDER_NUM")
    private Long orderNum;

    @Schema(description = "创建人")
    @Entity.Column("CREATE_BY")
    private String createBy;

    @Schema(description = "创建时间")
    @Entity.Column("CREATE_TIME")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @Entity.Column("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Schema(description = "更新时间")
    @Entity.Column("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;

    @Schema(description = "状态  0：禁用   1：正常")
    @Entity.Column("STATUS")
    private Integer status;

    @Schema(description = "是否删除  -1：已删除  0：正常")
    @Entity.Column("DEL_FLAG")
    private Integer delFlag;
}
