package com.zhaolq.mars.service.sys.controller;

import com.zhaolq.mars.common.spring.utils.SpringContextUtils;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.utils.ObjectUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 测试
 *
 * @author zhaolq
 * @date 2021/6/17 17:47
 */
@Slf4j
@RestController
@RequestMapping("/appTest")
public class AppTestController {

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private LoggingSystem loggingSystem;

    @GetMapping("/getDataSourceProperties")
    public R<Object> getDataSourceProperties() {
        Map<String, String> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 可使treeMap保存元素加入的顺序
                return 1;
            }
        });
        map.put("DriverClassName", dataSourceProperties.getDriverClassName());
        map.put("Url", dataSourceProperties.getUrl());
        map.put("Username", dataSourceProperties.getUsername());
        map.put("Password", dataSourceProperties.getPassword());
        return R.success(map);
    }

    @GetMapping("/getBean")
    public R<Object> getBean(String beanName) {
        if (StringUtils.isEmpty(beanName)) {
            beanName = "userServiceImpl";
            /*
            IUserService bean = SpringContextUtils.getInstance().getBean(beanName);
            UserEntity user = bean.getById(1);
            return R.success(user);
            */
        }
        Object bean = SpringContextUtils.getInstance().getBean(beanName);
        if (ObjectUtils.isEmpty(bean)) {
            return R.success("No bean named '" + beanName + "' available ");
        }
        return R.success("bean的简单名称: " + bean.getClass().getSimpleName());
    }

}
