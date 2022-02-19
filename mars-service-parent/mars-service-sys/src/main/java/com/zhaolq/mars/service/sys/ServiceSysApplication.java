package com.zhaolq.mars.service.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.zhaolq.mars.common.spring.application.MarsApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@MapperScan({"com.zhaolq.**.mapper"})
@ComponentScan({"com.zhaolq.*"})
@Slf4j
public class ServiceSysApplication {

    public static void main(String[] args) {
        log.info(">>>>>>>> SpringApplication.run()开始，系统启动中，请耐心等待...");
        // SpringApplication.run(ServiceSysApplication.class, args);
        MarsApplication.run(ServiceSysApplication.class, args);
        log.info(">>>>>>>> SpringApplication.run()结束，系统启动成功！");

    }

}
