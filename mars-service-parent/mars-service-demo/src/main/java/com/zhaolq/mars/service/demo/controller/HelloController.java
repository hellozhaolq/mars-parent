package com.zhaolq.mars.service.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
//@Api(tags = "Hello", description = "hello描述")
@Slf4j
public class HelloController {

    /**
     * 日志打印
     *
     * @param
     * @return java.lang.Object
     * @throws
     */
    // @ApiOperation("日志打印")
    @GetMapping("/printLog")
    public Object printLog() {
        long start = System.nanoTime();
        String msg = "打印日志";
        for (int i = 0; i < 10000; i++) {
            log.error(msg + i);
            log.warn(msg + i);
            log.info(msg + i);
            log.debug(msg + i);
            log.trace(msg + i);
        }
        long time = System.nanoTime() - start;
        log.info(String.valueOf(time));
        System.out.println(time);
        return "Test Hello!";
    }

}
