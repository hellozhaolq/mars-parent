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
 * 民族 GB 3304-91
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_STD_NATION")
@Schema(description = "民族 GB 3304-91")
public class NationEntity extends Model<NationEntity> {

    @Schema(description = "代码")
    @TableId(value = "CODE", type = IdType.ASSIGN_ID)
    private String code;

    @Schema(description = "名称")
    @TableField("NAME")
    private String name;


    @Override
    public Serializable pkVal() {
        return this.code;
    }

}
