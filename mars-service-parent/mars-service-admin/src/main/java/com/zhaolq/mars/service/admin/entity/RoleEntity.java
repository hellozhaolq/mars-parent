package com.zhaolq.mars.service.admin.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.common.valid.group.Remove;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "角色管理")
@Entity.Table("T_BASE_ROLE")
public class RoleEntity {
    @JsonProperty("id")
    @NotNull(groups = {Edit.class, Remove.class}, message = "id缺失")
    @Schema(description = "编号")
    @Entity.Column(id = true)
    private String id;

    @NotNull(groups = {Add.class}, message = "角色名称缺失")
    @Schema(description = "角色名称")
    @Entity.Column("NAME")
    private String name;

    @NotNull(groups = {Add.class}, message = "角色代码缺失")
    @Schema(description = "角色代码")
    @Entity.Column("CODE")
    private String code;

    @Schema(description = "备注")
    @Entity.Column("REMARK")
    private String remark;

    @Schema(description = "创建人")
    @Entity.Column("CREATE_BY")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    @Entity.Column("CREATE_TIME")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @Entity.Column("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    @Entity.Column("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;

    @NotNull(groups = {Add.class}, message = "状态缺失")
    @Schema(description = "状态  0：禁用   1：正常")
    @Entity.Column("STATUS")
    private Byte status;

    @NotNull(groups = {Add.class}, message = "是否删除缺失")
    @Schema(description = "是否删除  -1：已删除  0：正常")
    @Entity.Column("DEL_FLAG")
    private Byte delFlag;

    /************* 以上对应数据库字段 *************/

    /**
     * 一对多
     */
    @EqualsAndHashCode.Exclude
    private List<MenuEntity> menuList;

    /**
     * 一对多
     */
    @EqualsAndHashCode.Exclude
    private List<DeptEntity> deptList;
}
