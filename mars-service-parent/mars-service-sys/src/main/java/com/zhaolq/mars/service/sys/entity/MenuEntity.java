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
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@ApiModel(value="MenuEntity对象", description="菜单管理")
public class MenuEntity extends Model<MenuEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private BigDecimal id;

    @ApiModelProperty(value = "菜单名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "菜单代码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)")
    @TableField("PERMS")
    private String perms;

    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
    @TableField("TYPE")
    private Integer type;

    @ApiModelProperty(value = "url类型：1.普通页面 2.嵌套服务器页面 3.嵌套完整外部页面")
    @TableField("URL_TYPE")
    private Integer urlType;

    @ApiModelProperty(value = "菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)")
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

    @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    @TableField("PARENT_ID")
    private BigDecimal parentId;

    @ApiModelProperty(value = "排序")
    @TableField("ORDER_NUM")
    private Integer orderNum;

    @ApiModelProperty(value = "菜单图标")
    @TableField("ICON")
    private String icon;

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
    protected Serializable pkVal() {
        return this.id;
    }

}
