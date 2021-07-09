package com.zhaolq.mars.api.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.common.valid.group.Remove;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_SYS_MENU")
@ApiModel(value = "MenuEntity对象", description = "菜单管理")
public class MenuEntity extends Model<MenuEntity> {

    @JsonProperty("id")
    @NotNull(groups = {Edit.class, Remove.class}, message = "id缺失")
    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @NotNull(groups = {Add.class}, message = "菜单名称缺失")
    @ApiModelProperty(value = "菜单名称")
    @TableField("NAME")
    private String name;

    @NotNull(groups = {Add.class}, message = "菜单代码缺失")
    @ApiModelProperty(value = "菜单代码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)")
    @TableField("PERMS")
    private String perms;

    @NotNull(groups = {Add.class}, message = "类型缺失")
    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
    @TableField("TYPE")
    private Integer type;

    @ApiModelProperty(value = "url类型：1.普通页面 2.嵌套服务器页面 3.嵌套完整外部页面")
    @TableField("URL_TYPE")
    private Integer urlType;

    @ApiModelProperty(value = "菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login" +
            ".html, iframe:前缀会替换成服务器地址)")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "路径前缀")
    @TableField("SCHEME")
    private String scheme;

    @ApiModelProperty(value = "请求路径")
    @TableField("PATH")
    private String path;

    @ApiModelProperty(value = "打开方式:_self窗口内,_blank新窗口")
    @TableField("TARGET")
    private String target;

    @NotNull(groups = {Add.class}, message = "父菜单ID缺失")
    @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    @TableField("PARENT_ID")
    private String parentId;

    @NotNull(groups = {Add.class}, message = "排序缺失")
    @ApiModelProperty(value = "排序")
    @TableField("ORDER_NUM")
    private Integer orderNum;

    @ApiModelProperty(value = "菜单图标")
    @TableField("ICON")
    private String icon;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "LAST_UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdateTime;

    @NotNull(groups = {Add.class}, message = "状态缺失")
    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Integer status;

    @NotNull(groups = {Add.class}, message = "是否删除缺失")
    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Integer delFlag;

    @TableField(exist = false)
    @EqualsAndHashCode.Exclude
    private List<MenuEntity> children = new ArrayList<MenuEntity>();

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /************* 以上对应数据库字段 *************/

}