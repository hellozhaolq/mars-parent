package com.zhaolq.mars.api.admin.entity;

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
 * 政治面貌
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_BASE_POLITICAL_STATUS")
@Schema(description = "政治面貌")
public class PoliticalStatusEntity extends Model<PoliticalStatusEntity> {

    @Schema(description = "代码")
    @TableId(value = "CODE", type = IdType.ASSIGN_ID)
    private String code;

    @Schema(description = "名称")
    @TableField("NAME")
    private String name;

    @Schema(description = "简称")
    @TableField("NAME_SHORT")
    private String nameShort;


    @Override
    public Serializable pkVal() {
        return this.code;
    }

}
