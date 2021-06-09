package com.zhaolq.mars.service.sys.controller;

import com.zhaolq.mars.common.mybatis.pagination.WrapperBuilder;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author zhaolq
 * @date 2021/6/9 23:49
 */
@Slf4j
@RestController
@AllArgsConstructor
public class LoginController {

    private IUserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public String login(Model model, String account, String password) {
        UserEntity userEntity = userService.getOne(WrapperBuilder.getQueryWrapper(new UserEntity().setAccount(account).setPassword(password)));
        if(userEntity == null){
            return "返回主页或提示用户名密码错误";
        }


        return "";
    }

}
