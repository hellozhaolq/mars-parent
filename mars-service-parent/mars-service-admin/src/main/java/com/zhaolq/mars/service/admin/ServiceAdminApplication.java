package com.zhaolq.mars.service.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan({"com.zhaolq.*"})
@Slf4j
public class ServiceAdminApplication {
    public static void main(String[] args) {
        MyApplication.run("mars", ServiceAdminApplication.class, args);
        // SpringApplication.run(ServiceAdminApplication.class, args);
    }
}
