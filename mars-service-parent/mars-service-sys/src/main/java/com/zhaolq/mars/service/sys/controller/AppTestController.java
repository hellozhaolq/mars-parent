package com.zhaolq.mars.service.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.zhaolq.mars.common.spring.utils.SpringContextUtils;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.service.IUserService;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.utils.ObjectUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试
 *
 * @author zhaolq
 * @date 2021/6/17 17:47
 */
@Slf4j
@RestController
public class AppTestController {

    @Resource
    private DataSourceProperties dataSourceProperties;

    @GetMapping("/getProperties")
    public R<String> getProperties() {
        System.out.println(dataSourceProperties.getDriverClassName());
        System.out.println(dataSourceProperties.getUrl());
        System.out.println(dataSourceProperties.getUsername());
        System.out.println(dataSourceProperties.getPassword());
        return R.success("");
    }

    @GetMapping("/getBean")
    public R<Object> getBean(String beanName) {
        if (StringUtils.isEmpty(beanName)) {
            beanName = "userServiceImpl";
            IUserService bean = SpringContextUtils.getInstance().getBean(beanName);
            UserEntity user = bean.getById(1);
            return R.success(user);
        }
        Object bean = SpringContextUtils.getInstance().getBean(beanName);
        if (ObjectUtils.isEmpty(bean)) {
            return R.success("No bean named '" + beanName + "' available ");
        }
        return R.success("bean的简单名称: " + bean.getClass().getSimpleName());
    }
}
