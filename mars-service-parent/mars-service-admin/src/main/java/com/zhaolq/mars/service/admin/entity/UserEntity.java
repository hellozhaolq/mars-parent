package com.zhaolq.mars.service.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaolq.mars.common.mybatis.config.IdGenerate;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.common.valid.group.Remove;
import io.mybatis.provider.Entity;
import io.mybatis.provider.keysql.GenId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "用户管理")
@Entity.Table("T_BASE_USER")
public class UserEntity {
    @JsonProperty("id")
    @NotNull(groups = {Edit.class, Remove.class}, message = "id缺失")
    @Schema(description = "编号")
    @Entity.Column(id = true, genId = IdGenerate.class)
    private String id;

    @NotNull(groups = {Add.class}, message = "账号缺失")
    @Schema(description = "账号")
    @Entity.Column("ACCOUNT")
    private String account;

    @NotNull(groups = {Add.class}, message = "密码缺失")
    @Schema(description = "密码")
    @Entity.Column("PASSWORD")
    private String password;

    @NotNull(groups = {Add.class}, message = "用户名缺失")
    @Schema(description = "用户名")
    @Entity.Column("NAME")
    private String name;

    @Schema(description = "昵称")
    @Entity.Column("NICK_NAME")
    private String nickName;

    @Schema(description = "加密盐")
    @Entity.Column("SALT")
    private String salt;

    @NotNull(groups = {Add.class}, message = "性别缺失")
    @Schema(description = "性别  1：男   2：女")
    @Entity.Column("SEX")
    private Byte sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 序列化时转换成指定的格式
    @Schema(description = "出生日期")
    @Entity.Column("BIRTHDAY")
    private Date birthday;

    @Schema(description = "年龄")
    @Entity.Column("AGE")
    private Integer age;

    @Schema(description = "身份证号")
    @Entity.Column("ID_NUMBER")
    private String idNumber;

    @Schema(description = "地址")
    @Entity.Column("ADDRESS")
    private String address;

    @NotNull(groups = {Add.class}, message = "邮箱缺失")
    @Email(message = "邮件格式错误")
    @Schema(description = "邮箱")
    @Entity.Column("EMAIL")
    private String email;

    @NotNull(groups = {Add.class}, message = "手机号缺失")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @Schema(description = "手机号")
    @Entity.Column("MOBILE")
    private String mobile;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "入职时间")
    @Entity.Column("ENTRY_TIME")
    private Date entryTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "离职时间")
    @Entity.Column("DEPARTURE_TIME")
    private Date departureTime;

    @NotNull(groups = {Add.class}, message = "国家代码缺失")
    @Schema(description = "国家代码")
    @Entity.Column("COUNTRY_CODE")
    private String countryCode;

    @NotNull(groups = {Add.class}, message = "民族代码缺失")
    @Schema(description = "民族代码")
    @Entity.Column("NATION_CODE")
    private String nationCode;

    @Schema(description = "政治面貌代码")
    @Entity.Column("POLITICAL_STATUS_CODE")
    private String politicalStatusCode;

    @Schema(description = "用户类型")
    @Entity.Column("USER_TYPE")
    private String userType;

    @Schema(description = "身份代码")
    @Entity.Column("IDENTITY_CODE")
    private String identityCode;

    @NotNull(groups = {Add.class}, message = "机构ID缺失")
    @Schema(description = "机构ID")
    @Entity.Column("DEPT_ID")
    private String deptId;

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

    @Schema(description = "【0】是否:1是0否")
    @Entity.Column("FLAG")
    private String flag;

    /************* 以上对应数据库字段 *************/

    /**
     * 一对一
     */
    @EqualsAndHashCode.Exclude
    @Entity.Transient
    private RoleEntity role;

    /**
     * 一对多
     */
    @EqualsAndHashCode.Exclude
    @Entity.Transient
    private List<RoleEntity> roleList;

    /*********** 使用mb的通用curd会在实体类属性和表字段间自动做映射，不参与映射的三种解决方案(排除非表字段) ***********/
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
     * 3、推荐方法：使用注解标识 @Entity.Transient
     */
    @EqualsAndHashCode.Exclude
    @Entity.Transient
    private String remarkTest3;

    public static String getRemarkTest2() {
        return UserEntity.remarkTest2;
    }

    public static void setRemarkTest2(String remarkTest2) {
        UserEntity.remarkTest2 = remarkTest2;
    }
}
