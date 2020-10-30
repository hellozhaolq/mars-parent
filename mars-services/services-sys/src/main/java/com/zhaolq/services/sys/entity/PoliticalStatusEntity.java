package com.zhaolq.services.sys.entity;

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
 * 政治面貌
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_STD_POLITICAL_STATUS")
@ApiModel(value="PoliticalStatusEntity对象", description="政治面貌")
public class PoliticalStatusEntity extends Model<PoliticalStatusEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代码")
    @TableId(value = "CODE", type = IdType.ASSIGN_ID)
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "简称")
    @TableField("NAME_SHORT")
    private String nameShort;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
