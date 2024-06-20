package com.zhaolq.mars.service.admin.entity;

import java.time.LocalDateTime;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色机构
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity.Table("T_BASE_ROLE_DEPT")
@Schema(description = "角色机构")
public class RoleDeptEntity {
    @Schema(description = "编号")
    @Entity.Column(id = true)
    private String id;

    @Schema(description = "角色ID")
    @Entity.Column("ROLE_ID")
    private String roleId;

    @Schema(description = "机构ID")
    @Entity.Column("DEPT_ID")
    private String deptId;

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
}
