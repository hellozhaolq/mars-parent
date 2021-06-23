package com.zhaolq.mars.common.springboot;

import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 *
 * @author zhaolq
 * @date 2021/6/23 17:48
 */
public class MarsApplication {

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class<?>[] { primarySource }, args);
    }


}
