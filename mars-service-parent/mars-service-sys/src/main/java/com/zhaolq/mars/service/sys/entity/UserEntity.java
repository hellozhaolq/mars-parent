package com.zhaolq.mars.service.sys.entity;

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
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Data
@Configuration
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_SYS_USER")
// @KeySequence("SEQ_SYS_USER_ID")
@ApiModel(value = "UserEntity对象", description = "用户管理")
public class UserEntity extends Model<UserEntity> {

    @JsonProperty("id")
    @NotNull(groups = {Edit.class, Remove.class}, message = "id缺失")
    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @NotNull(groups = {Add.class}, message = "账号缺失")
    @ApiModelProperty(value = "账号")
    @TableField("ACCOUNT")
    private String account;

    @NotNull(groups = {Add.class}, message = "密码缺失")
    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @NotNull(groups = {Add.class}, message = "用户名缺失")
    @ApiModelProperty(value = "用户名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "昵称")
    @TableField("NICK_NAME")
    private String nickName;

    @ApiModelProperty(value = "加密盐")
    @TableField("SALT")
    private String salt;

    @NotNull(groups = {Add.class}, message = "性别缺失")
    @ApiModelProperty(value = "性别  1：男   2：女")
    @TableField("SEX")
    private Byte sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 序列化时转换成指定的格式
    @ApiModelProperty(value = "出生日期")
    @TableField("BIRTHDAY")
    private Date birthday;

    @ApiModelProperty(value = "年龄")
    @TableField("AGE")
    private Integer age;

    @ApiModelProperty(value = "身份证号")
    @TableField("ID_NUMBER")
    private String idNumber;

    @ApiModelProperty(value = "地址")
    @TableField("ADDRESS")
    private String address;

    @NotNull(groups = {Add.class}, message = "邮箱缺失")
    @Email(message = "邮件格式错误")
    @ApiModelProperty(value = "邮箱")
    @TableField("EMAIL")
    private String email;

    @NotNull(groups = {Add.class}, message = "手机号缺失")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @ApiModelProperty(value = "手机号")
    @TableField("MOBILE")
    private String mobile;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "入职时间")
    @TableField("ENTRY_TIME")
    private Date entryTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "离职时间")
    @TableField("DEPARTURE_TIME")
    private Date departureTime;

    @NotNull(groups = {Add.class}, message = "国家代码缺失")
    @ApiModelProperty(value = "国家代码")
    @TableField("COUNTRY_CODE")
    private String countryCode;

    @NotNull(groups = {Add.class}, message = "民族代码缺失")
    @ApiModelProperty(value = "民族代码")
    @TableField("NATION_CODE")
    private String nationCode;

    @ApiModelProperty(value = "政治面貌代码")
    @TableField("POLITICAL_STATUS_CODE")
    private String politicalStatusCode;

    @ApiModelProperty(value = "用户类型")
    @TableField("USER_TYPE")
    private String userType;

    @ApiModelProperty(value = "身份代码")
    @TableField("IDENTITY_CODE")
    private String identityCode;

    @NotNull(groups = {Add.class}, message = "机构缺失")
    @ApiModelProperty(value = "机构ID")
    @TableField("DEPT_ID")
    private String deptId;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;

    @NotNull(groups = {Add.class}, message = "状态缺失")
    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Byte status;

    @NotNull(groups = {Add.class}, message = "是否删除缺失")
    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Byte delFlag;

    @ApiModelProperty(value = "【0】是否:1是0否")
    @TableField("FLAG")
    private String flag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /************* 以上对应数据库字段 *************/

    @TableField(exist = false)
    private RoleEntity role;

    @TableField(exist = false)
    private List<RoleEntity> roleList;

    /*********** 使用mbp的通用curd会在实体类属性和表字段间自动做映射，不参与映射的三种解决方案(排除非表字段) ***********/
    /**
     * 1、不被序列化：transient
     */
    private transient String remarkTest;
    /**
     * 2、为了可以序列化，使用static，但这样的属性归类所有，不满足一个对象各一个，mybatisPlus不会认为是表字段
     * Lombok不会为static变量生成get、set方法，需手动
     */
    private static String remarkTest2;
    /**
     * 3、正确的方法：@TableField(exist = false)
     */
    @TableField(exist = false)
    private String remarkTest3;

    public static String getRemarkTest2() {
        return remarkTest2;
    }

    public static void setRemarkTest2(String remarkTest2) {
        UserEntity.remarkTest2 = remarkTest2;
    }

}
