package com.zhaolq.mars.service.admin.entity;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 政治面貌
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity.Table("T_BASE_POLITICAL_STATUS")
@Schema(description = "政治面貌")
public class PoliticalStatusEntity {
    @Schema(description = "代码")
    @Entity.Column(id = true)
    private String code;

    @Schema(description = "名称")
    @Entity.Column("NAME")
    private String name;

    @Schema(description = "简称")
    @Entity.Column("NAME_SHORT")
    private String nameShort;
}
