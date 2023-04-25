package com.zhaolq.mars.api.sys.entity;

import java.io.Serializable;

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
 * 国家 ISO 3166-1
 * https://zh.wikipedia.org/wiki/ISO_3166-1
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_BASE_COUNTRY")
@Schema(description = "国家 ISO 3166-1")
public class CountryEntity extends Model<CountryEntity> {

    @Schema(description = "代码")
    @TableId(value = "CODE", type = IdType.ASSIGN_ID)
    private String code;

    @Schema(description = "名称")
    @TableField("NAME")
    private String name;

    @Schema(description = "英文名称")
    @TableField("NAME_EN")
    private String nameEn;


    @Override
    public Serializable pkVal() {
        return this.code;
    }

}
