package com.zhaolq.mars.service.demo.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/9 17:12
 */
@RestController
@Slf4j
public class MyMQController {
    @Autowired
    MyProducer myProducers;

    @GetMapping("/mq/producer")
    public String myProducer() {
        List<MyModel> list = new ArrayList<>();

        MyModel model = new MyModel();
        model.setId(UUID.randomUUID());
        model.setInfo("content-aaa");
        list.add(model);

        model = new MyModel();
        model.setId(UUID.randomUUID());
        model.setInfo("content-bbb");
        list.add(model);

        model = new MyModel();
        model.setId(UUID.randomUUID());
        model.setInfo("content-ccc");
        list.add(model);


        myProducers.sendMsg(list);
        return "已发送：";
    }
}
