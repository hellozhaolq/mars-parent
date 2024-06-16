package com.zhaolq.mars.service.admin.entity;

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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity.Table("T_BASE_MENU")
@Schema(description = "菜单管理")
public class MenuEntity {

    @JsonProperty("id")
    @NotNull(groups = {Edit.class, Remove.class}, message = "id缺失")
    @Schema(description = "编号")
    @Entity.Column(id = true)
    private String id;

    @NotNull(groups = {Add.class}, message = "菜单名称缺失")
    @Schema(description = "菜单名称")
    @Entity.Column("NAME")
    private String name;

    @NotNull(groups = {Add.class}, message = "菜单代码缺失")
    @Schema(description = "菜单代码")
    @Entity.Column("CODE")
    private String code;

    @Schema(description = "备注")
    @Entity.Column("REMARK")
    private String remark;

    @Schema(description = "授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)")
    @Entity.Column("PERMS")
    private String perms;

    @NotNull(groups = {Add.class}, message = "类型缺失")
    @Schema(description = "类型   0：目录   1：菜单   2：按钮")
    @Entity.Column("TYPE")
    private Integer type;

    @Schema(description = "url类型：1.普通页面 2.嵌套服务器页面 3.嵌套完整外部页面")
    @Entity.Column("URL_TYPE")
    private Integer urlType;

    @Schema(description = "菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login" +
            ".html, iframe:前缀会替换成服务器地址)")
    @Entity.Column("URL")
    private String url;

    @Schema(description = "路径前缀")
    @Entity.Column("SCHEME")
    private String scheme;

    @Schema(description = "请求路径")
    @Entity.Column("PATH")
    private String path;

    @Schema(description = "打开方式:_self窗口内,_blank新窗口")
    @Entity.Column("TARGET")
    private String target;

    @NotNull(groups = {Add.class}, message = "父菜单ID缺失")
    @Schema(description = "父菜单ID，一级菜单为0")
    @Entity.Column("PARENT_ID")
    private String parentId;

    @NotNull(groups = {Add.class}, message = "排序缺失")
    @Schema(description = "排序")
    @Entity.Column("ORDER_NUM")
    private Integer orderNum;

    @Schema(description = "菜单图标")
    @Entity.Column("ICON")
    private String icon;

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
    private Integer status;

    @NotNull(groups = {Add.class}, message = "是否删除缺失")
    @Schema(description = "是否删除  -1：已删除  0：正常")
    @Entity.Column("DEL_FLAG")
    private Integer delFlag;

    /************* 以上对应数据库字段 *************/

    @EqualsAndHashCode.Exclude
    private List<MenuEntity> children = new ArrayList<MenuEntity>();
}
