package com.zhaolq.mars.service.admin.entity;

import java.time.LocalDateTime;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图片、文件、音乐等媒体文件
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity.Table("T_BASE_MEDIA_FILE")
@Schema(description = "图片、文件、音乐等媒体文件")
public class MediaFileEntity {
    @Schema(description = "编号")
    @Entity.Column(id = true)
    private String id;

    @Schema(description = "资源类型(表名)：user-用户；")
    @Entity.Column("RESOURCE_TYPE")
    private String resourceType;

    @Schema(description = "资源编号")
    @Entity.Column("RESOURCE_ID")
    private String resourceId;

    @Schema(description = "文件类型(字段)：avatar-头像；")
    @Entity.Column("FILE_TYPE")
    private String fileType;

    @Schema(description = "文件内容")
    @Entity.Column("FILE_CONTENT")
    private byte[] fileContent;

    @Schema(description = "创建人")
    @Entity.Column("CREATE_BY")
    private String createBy;

    @Schema(description = "创建时间")
    @Entity.Column("CREATE_TIME")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @Entity.Column("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Schema(description = "更新时间")
    @Entity.Column("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;

    @Schema(description = "状态  0：禁用   1：正常")
    @Entity.Column("STATUS")
    private Integer status;

    @Schema(description = "是否删除  -1：已删除  0：正常")
    @Entity.Column("DEL_FLAG")
    private Integer delFlag;
}
