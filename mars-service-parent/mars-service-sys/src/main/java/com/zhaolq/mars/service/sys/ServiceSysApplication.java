package com.zhaolq.mars.service.sys;

import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.service.IUserService;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

import javax.annotation.Resource;
import java.util.Iterator;

@SpringBootApplication
@MapperScan({"com.zhaolq.**.mapper"})
@ComponentScan({"com.zhaolq.*"})
@Slf4j
public class ServiceSysApplication {

    public static void main(String[] args) {
        log.info(">>>>>>>> SpringApplication.run()开始，系统启动中，请耐心等待...");
        SpringApplication.run(ServiceSysApplication.class, args);
        log.info(">>>>>>>> SpringApplication.run()结束，系统启动成功！");
    }

}
