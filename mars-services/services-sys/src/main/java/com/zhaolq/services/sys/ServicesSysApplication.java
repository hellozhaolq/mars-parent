package com.zhaolq.services.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"com.zhaolq.**.mapper"})
@ComponentScan({"com.zhaolq.*"})
public class ServicesSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicesSysApplication.class, args);
    }

}
