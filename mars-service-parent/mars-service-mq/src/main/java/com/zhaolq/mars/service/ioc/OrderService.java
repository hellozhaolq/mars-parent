package com.zhaolq.mars.service.ioc;

import com.zhaolq.mars.service.mq.entity.OrderEntity;
import org.springframework.stereotype.Service;

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


    public OrderService(OrderEntity order) {
        this.order = order;
    }

    public void setName(String name) {

    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

}
