package com.zhaolq.mars.service.admin.task;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * cpu计算计划
 *
 * @Author zhaolq
 * @Date 2023/5/17 10:21:10
 * @Since 1.0.0
 */
@Component
@Slf4j
public class ScheduledCpuCalc {
    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void cpuCalc() {

        if (NetUtil.ping("127.0.0.1")) {
            return;
        }
        Callable<Long> call = () -> {
            long i = 0;
            while (i < 30000000000L) {
                i++;
            }
            return i;
        };
        for (int i = 1; i <= 5; i++) {
            new Thread(new FutureTask<>(call), "thread-" + i).start();
            FutureTask<Long> f = new FutureTask<>(call);
        }
    }
}
