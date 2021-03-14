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
        order.setId("order");
        order.setMessageId("order");
        order.setName("测试order");
        return order;
    }

    @Bean
    public OrderEntity order2() {
        OrderEntity order = new OrderEntity();
        order.setId("order2");
        order.setMessageId("order2");
        order.setName("测试order2");
        return new OrderEntity();
    }

    @Bean
    public String name() {
        return "方法名称name";
    }

    @Bean
    public String name2() {
        return "方法名称name2";
    }

}
