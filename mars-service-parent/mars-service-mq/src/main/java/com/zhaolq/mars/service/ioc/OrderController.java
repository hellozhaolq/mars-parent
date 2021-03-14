package com.zhaolq.mars.service.ioc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/12 15:48
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order")
    public String order() {
        System.out.println(orderService.getOrder().toString());
        System.out.println(orderService.getName());
        return orderService.getOrder().toString();
    }
}
