package com.zhaolq.mars.service.admin.controller;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaolq.mars.api.admin.entity.UserEntity;
import com.zhaolq.mars.common.mybatis.pagination.WrapperBuilder;
import com.zhaolq.mars.service.admin.service.IUserService;
import com.zhaolq.mars.common.core.result.R;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录控制器
 *
 * @author zhaolq
 * @date 2021/6/9 23:49
 */
@Slf4j
@RestController
@Tag(name = "登录模块", description = "登录模块")
@AllArgsConstructor
@RequestMapping(path = "/", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoginController {

    private ServerProperties serverProperties;

    private DataSourceProperties dataSourceProperties;

    private IUserService userService;

    @RequestMapping("/login")
    @Parameters({
            @Parameter(name = "account", description = "账号", style = ParameterStyle.FORM, required = true),
            @Parameter(name = "password", description = "密码", style = ParameterStyle.FORM, required = true)
    })
    @Operation(summary = "登录", description = "登录")
    public R<String> login(
            Model model,
            @Parameter(name = "account", description = "账号", style = ParameterStyle.FORM, required = true) String account,
            @Parameter(name = "password", description = "密码", style = ParameterStyle.FORM, required = true) String password) {
        UserEntity userEntity = userService.getOne(WrapperBuilder.getQueryWrapper(new UserEntity().setAccount(account).setPassword(password)));
        if (userEntity == null) {
            return R.failureCh("返回主页或提示用户名密码错误");
        }

        return R.success("登录成功");
    }

}
