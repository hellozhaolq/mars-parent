package com.zhaolq.mars.common.spring.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 获取bean
 *
 * @Author zhaolq
 * @Date 2024/6/29 21:31
 */
@Slf4j
@Data
@Component
public class SpringContext implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public <T> T getBean(String beanName) throws BeansException {
        return (T) applicationContext.getBean(beanName);
    }

    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return (T) applicationContext.getBean(beanName, requiredType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
