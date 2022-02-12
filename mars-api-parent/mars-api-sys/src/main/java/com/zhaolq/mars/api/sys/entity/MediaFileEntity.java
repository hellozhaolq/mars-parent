package com.zhaolq.mars.api.sys.entity;

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
import java.time.LocalDateTime;

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
@ApiModel(value="MediaFileEntity对象", description="图片、文件、音乐等媒体文件")
public class MediaFileEntity extends Model<MediaFileEntity> {

    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "资源类型(表名)：user-用户；")
    @TableField("RESOURCE_TYPE")
    private String resourceType;

    @ApiModelProperty(value = "资源编号")
    @TableField("RESOURCE_ID")
    private String resourceId;

    @ApiModelProperty(value = "文件类型(字段)：avatar-头像；")
    @TableField("FILE_TYPE")
    private String fileType;

    @ApiModelProperty(value = "文件内容")
    @TableField("FILE_CONTENT")
    private byte[] fileContent;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;

    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Integer delFlag;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
