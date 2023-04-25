package com.zhaolq.mars.api.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * 图片、文件、音乐等媒体文件
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_SYS_MEDIA_FILE")
@Schema(description = "图片、文件、音乐等媒体文件")
public class MediaFileEntity extends Model<MediaFileEntity> {

    @Schema(description = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "资源类型(表名)：user-用户；")
    @TableField("RESOURCE_TYPE")
    private String resourceType;

    @Schema(description = "资源编号")
    @TableField("RESOURCE_ID")
    private String resourceId;

    @Schema(description = "文件类型(字段)：avatar-头像；")
    @TableField("FILE_TYPE")
    private String fileType;

    @Schema(description = "文件内容")
    @TableField("FILE_CONTENT")
    private byte[] fileContent;

    @Schema(description = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @Schema(description = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Schema(description = "更新时间")
    @TableField("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;

    @Schema(description = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Integer status;

    @Schema(description = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Integer delFlag;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
