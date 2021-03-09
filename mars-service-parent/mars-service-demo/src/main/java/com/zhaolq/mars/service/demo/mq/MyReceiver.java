package com.zhaolq.mars.service.demo.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/9 17:11
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MyReceiver {
    @RabbitHandler
    public void process(List<MyModel> list) {
        System.out.println(new RabbitConfig().getHost());

        System.out.println("接收处理队列A当中的消息： ");
        for (MyModel model : list) {
            System.out.println(model.getId() + "" + model.getInfo());
        }
    }
}
