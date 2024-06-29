package com.zhaolq.mars.service.admin.config;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义任务调度线程池
 * TaskScheduler配置（开启@EnableScheduling注解）TaskSchedulingProperties
 *
 * @Author zhaolq
 * @Date 2023/5/26 17:29:00
 */
@Slf4j
@EnableScheduling
@Configuration
public class TaskSchedulerConfig {

    @Value("${spring.task.scheduling.thread-name-prefix:marsScheduler-}")
    private String threadNamePrefix;

    @Value("${spring.task.scheduling.pool.size:10}")
    private int size;

    @Value("${spring.task.scheduling.shutdown.await-termination:true}")
    private boolean awaitTermination;

    @Value("${spring.task.scheduling.shutdown.await-termination-period:600s}")
    private Duration awaitTerminationPeriod;

    @Bean
    @Primary
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize(); // 此方法必须调用，否则会抛出ThreadPoolTaskScheduler未初始化异常
        scheduler.setThreadNamePrefix(threadNamePrefix);
        scheduler.setPoolSize(size);
        scheduler.setWaitForTasksToCompleteOnShutdown(awaitTermination);
        scheduler.setAwaitTerminationSeconds((int) awaitTerminationPeriod.toSeconds());
        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return scheduler;
    }
}
