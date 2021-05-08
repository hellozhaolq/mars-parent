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

/**
 * <p>
 * 民族 GB 3304-91
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_STD_NATION")
@ApiModel(value="NationEntity对象", description="民族 GB 3304-91")
public class NationEntity extends Model<NationEntity> {

    @ApiModelProperty(value = "代码")
    @TableId(value = "CODE", type = IdType.ASSIGN_ID)
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField("NAME")
    private String name;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
