package com.zhaolq.mars.service.ioc;

import com.zhaolq.mars.service.mq.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/12 15:46
 */
@Service
public class OrderService {

    private String name;
    private OrderEntity order;

    @Autowired
    public OrderService(OrderEntity order) {
        this.order = order;
    }

    @Autowired
    @Resource(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

}
