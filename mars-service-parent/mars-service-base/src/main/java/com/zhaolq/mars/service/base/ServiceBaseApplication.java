package com.zhaolq.mars.service.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.zhaolq.mars.common.spring.application.MyApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@MapperScan({"com.zhaolq.**.mapper"})
@ComponentScan({"com.zhaolq.*"})
@Slf4j
public class ServiceBaseApplication {
    public static void main(String[] args) {
        MyApplication.run("mars", ServiceBaseApplication.class, args);
    }
}
