/**
 * Copyright ©2020 Synjones. All rights reserved.
 */
package com.synjones.app.controller;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synjones.app.vo.TestVo;
import com.synjones.core.tool.api.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 注解Demo
 * 
 * @author zhaolq
 * @date 2020年7月24日 上午11:34:24
 */
@RestController
@RequestMapping("/test")
@Api(value = "注解Demo", tags = "注解Demo")
public class TestController {

	@GetMapping("/getTest")
	@ApiOperation(value = "注解Demo", notes = "注解Demo", httpMethod = "GET")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "account", value = "账号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query") })
	public R<List<TestVo>> getTest(@Valid TestVo testVo, BindingResult bindingResult,
			@RequestParam(required = true) @NotNull(message = "系统类型不能为空") String systemType) {
		/**
		 * @Valid 注解可以实现数据的验证，你可以定义实体，在实体的属性上添加校验规则，而在API接收数据时添加@valid关键字，这时你的实体将会开启一个校验的功能。
		 * 
		 * @Valid 和 BindingResult 是一一对应的，如果有多个@Valid，那么每个@Valid后面跟着的BindingResult就是这个@Valid的验证结果，顺序不能乱
		 * 
		 * 这里的systemType校验的异常需要spring apo进行统一异常捕获处理
		 */
		
		
		if (bindingResult.hasErrors()) {
			String errors = bindingResult.getAllErrors().toString();
			return R.success(errors);
		}

		System.out.println(testVo);

		List<TestVo> list = new LinkedList<TestVo>();
		TestVo vo = new TestVo();
		for (int i = 0; i < 3; i++) {
			list.add(vo);
		}
		return R.data(list);
	}

	@GetMapping("/getTest2")
	public R<String> getTest2(@Valid @NotNull(message = "系统类型不能为空") String systemType) {
		System.out.println(systemType);
		return R.data(systemType);
	}
	
	@GetMapping("/getTest3")
	public R<String> getTest3(@RequestParam(required = true) @NotNull(message = "系统类型不能为空") String systemType) {
		System.out.println(systemType);
		return R.data(systemType);
	}

}
