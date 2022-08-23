package com.zhaolq.mars.service.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.zhaolq.mars.common.spring.application.MyApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@MapperScan({"com.zhaolq.**.mapper"})
@ComponentScan({"com.zhaolq.*"})
@Slf4j
public class ServiceSysApplication {
    public static void main(String[] args) {
        MyApplication.run("mars", ServiceSysApplication.class, args);
    }
}