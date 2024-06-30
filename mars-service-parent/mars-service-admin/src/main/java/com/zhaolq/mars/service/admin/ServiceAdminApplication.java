package com.zhaolq.mars.service.admin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zhaolq.mars.common.spring.application.MyApplication;

import io.mybatis.activerecord.spring.boot.autoconfigure.MapperProviderAutoConfiguration;
import lombok.extern.slf4j.Slf4j;

/**
 * 启动类
 *
 * @Author zhaolq
 * @Date 2024/6/30 21:26
 */
@Slf4j
@ComponentScan({"com.zhaolq.*"})
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
        MapperProviderAutoConfiguration.class})
public class ServiceAdminApplication {
    public static void main(String[] args) {
        MyApplication.run("mars", ServiceAdminApplication.class, args);
        // SpringApplication.run(ServiceAdminApplication.class, args);
    }
}
