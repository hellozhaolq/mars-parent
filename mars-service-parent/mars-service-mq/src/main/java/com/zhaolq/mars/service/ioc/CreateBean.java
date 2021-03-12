package com.zhaolq.mars.service.ioc;

import com.zhaolq.mars.service.mq.entity.OrderEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/12 15:43
 */
@Configuration
public class CreateBean {

    @Bean
    public OrderEntity order() {
        OrderEntity order = new OrderEntity();
        order.setId("100");
        order.setMessageId("100");
        order.setName("测试");
        return new OrderEntity();
    }

    @Bean
    public String str() {
        return "字符串bean";
    }

}
