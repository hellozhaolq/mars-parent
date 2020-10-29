/**
 * Copyright ©2020 Synjones. All rights reserved.
 */
package com.synjones.app.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 注解Demo
 * 
 * @author zhaolq
 * @date 2020年7月24日 上午11:35:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ApiModel(description = "注解Demo")
public class TestVo extends Model {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

	@NotNull(message = "账号不能为空")
	@ApiModelProperty(value = "账号")
	private String account;

	@NotNull(message = "用户名不能为空")
	@ApiModelProperty(value = "用户名")
	private String username;

	@JsonIgnore
	@ApiModelProperty(value = "密码", hidden = true)
	private String password;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "年龄")
	private String age;

	@ApiModelProperty(value = "性别")
	private String sex;

	@ApiModelProperty(value = "身份证")
	private String idCard;

	@ApiModelProperty(value = "机构")
	private String dept;
	
	@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "邮箱格式有误")
	@ApiModelProperty(value = "电话", required = true)
	private String telephone;

	@Email(message = "邮件格式错误")
	@ApiModelProperty(value = "邮件")
	private String email;
	
	@Valid // 嵌套验证必须用@Valid
    @Size(min = 1, message = "props至少要有一个自定义属性")
    private List<String> props;

}
