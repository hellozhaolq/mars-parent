package com.zhaolq.service.sys.entity;

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

/**
 * <p>
 * 国家 ISO 3166-1
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_STD_COUNTRY")
@ApiModel(value="CountryEntity对象", description="国家 ISO 3166-1")
public class CountryEntity extends Model<CountryEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代码")
    @TableId(value = "CODE", type = IdType.ASSIGN_ID)
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "英文名称")
    @TableField("NAME_EN")
    private String nameEn;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
