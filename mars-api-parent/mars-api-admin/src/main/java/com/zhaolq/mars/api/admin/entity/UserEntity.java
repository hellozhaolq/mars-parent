package com.zhaolq.mars.api.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.zhaolq.mars.common.mybatis.annotation.SqlConditionOracle;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.common.valid.group.Remove;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_BASE_USER")
@Schema(description = "用户管理")
public class UserEntity extends Model<UserEntity> {

    @JsonProperty("id")
    @NotNull(groups = {Edit.class, Remove.class}, message = "id缺失")
    @Schema(description = "编号")
    @TableId(value = "ID", type = IdType.ASSIGN_ID) // 局部主键策略
    private String id;

    @NotNull(groups = {Add.class}, message = "账号缺失")
    @Schema(description = "账号")
    @TableField("ACCOUNT")
    private String account;

    @NotNull(groups = {Add.class}, message = "密码缺失")
    @Schema(description = "密码")
    @TableField("PASSWORD")
    private String password;

    @NotNull(groups = {Add.class}, message = "用户名缺失")
    @Schema(description = "用户名")
    @TableField(value = "NAME", condition = SqlConditionOracle.LIKE)
    private String name;

    @Schema(description = "昵称")
    @TableField("NICK_NAME")
    private String nickName;

    @Schema(description = "加密盐")
    @TableField("SALT")
    private String salt;

    @NotNull(groups = {Add.class}, message = "性别缺失")
    @Schema(description = "性别  1：男   2：女")
    @TableField("SEX")
    private Byte sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 序列化时转换成指定的格式
    @Schema(description = "出生日期")
    @TableField("BIRTHDAY")
    private Date birthday;

    @Schema(description = "年龄")
    @TableField("AGE")
    private Integer age;

    @Schema(description = "身份证号")
    @TableField("ID_NUMBER")
    private String idNumber;

    @Schema(description = "地址")
    @TableField("ADDRESS")
    private String address;

    @NotNull(groups = {Add.class}, message = "邮箱缺失")
    @Email(message = "邮件格式错误")
    @Schema(description = "邮箱")
    @TableField("EMAIL")
    private String email;

    @NotNull(groups = {Add.class}, message = "手机号缺失")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @Schema(description = "手机号")
    @TableField("MOBILE")
    private String mobile;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "入职时间")
    @TableField("ENTRY_TIME")
    private Date entryTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "离职时间")
    @TableField("DEPARTURE_TIME")
    private Date departureTime;

    @NotNull(groups = {Add.class}, message = "国家代码缺失")
    @Schema(description = "国家代码")
    @TableField("COUNTRY_CODE")
    private String countryCode;

    @NotNull(groups = {Add.class}, message = "民族代码缺失")
    @Schema(description = "民族代码")
    @TableField("NATION_CODE")
    private String nationCode;

    @Schema(description = "政治面貌代码")
    @TableField("POLITICAL_STATUS_CODE")
    private String politicalStatusCode;

    @Schema(description = "用户类型")
    @TableField("USER_TYPE")
    private String userType;

    @Schema(description = "身份代码")
    @TableField("IDENTITY_CODE")
    private String identityCode;

    @NotNull(groups = {Add.class}, message = "机构ID缺失")
    @Schema(description = "机构ID")
    @TableField("DEPT_ID")
    private String deptId;

    @Schema(description = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @TableField("LAST_UPDATE_BY")
    private String lastUpdateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    @TableField(value = "LAST_UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdateTime;

    @NotNull(groups = {Add.class}, message = "状态缺失")
    @Schema(description = "状态  0：禁用   1：正常")
    @TableField("STATUS")
    private Byte status;

    @NotNull(groups = {Add.class}, message = "是否删除缺失")
    @Schema(description = "是否删除  -1：已删除  0：正常")
    @TableField("DEL_FLAG")
    private Byte delFlag;

    @Schema(description = "【0】是否:1是0否")
    @TableField("FLAG")
    private String flag;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

    /************* 以上对应数据库字段 *************/

    /**
     * 一对一
     */
    @TableField(exist = false)
    @EqualsAndHashCode.Exclude
    private RoleEntity role;

    /**
     * 一对多
     */
    @TableField(exist = false)
    @EqualsAndHashCode.Exclude
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
     * 3、推荐的方法：@TableField(exist = false)
     */
    @TableField(exist = false)
    @EqualsAndHashCode.Exclude
    private String remarkTest3;

    public static String getRemarkTest2() {
        return remarkTest2;
    }

    public static void setRemarkTest2(String remarkTest2) {
        UserEntity.remarkTest2 = remarkTest2;
    }

}
