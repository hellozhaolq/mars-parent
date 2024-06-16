package com.zhaolq.mars.service.admin.entity;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 国家 ISO 3166-1
 * https://zh.wikipedia.org/wiki/ISO_3166-1
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity.Table("T_BASE_COUNTRY")
@Schema(description = "国家 ISO 3166-1")
public class CountryEntity {
    @Schema(description = "代码")
    @Entity.Column(id = true)
    private String code;

    @Schema(description = "名称")
    @Entity.Column("NAME")
    private String name;

    @Schema(description = "英文名称")
    @Entity.Column("NAME_EN")
    private String nameEn;
}
