package com.zhaolq.mars.service.admin.config;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异步任务线程池
 * TaskExecutor配置（开启@EnableAsync注解）TaskExecutionProperties
 *
 * @Author zhaolq
 * @Date 2023/5/26 14:26:27
 */
@Slf4j
@EnableAsync
@Configuration
public class TaskExecutorConfig {

    /**
     * 线程池中TaskExecutor线程名的前缀，可以方便我们定位处理任务所在的线程池
     */
    @Value("${spring.task.execution.thread-name-prefix:marsExecutor-}")
    private String threadNamePrefix;

    /**
     * 核心线程数量，线程池创建时候初始化的线程数
     */
    @Value("${spring.task.execution.pool.core-size:10}")
    private int coreSize;

    /**
     * 最大线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
     */
    @Value("${spring.task.execution.pool.max-size:10}")
    private int maxSize;

    /**
     * 队列容量。用来缓冲执行任务的队列大小。无限容量不会增加池，因此会忽略"max-size"属性。
     */
    @Value("${spring.task.execution.pool.queue-capacity:1000}")
    private int queueCapacity;

    /**
     * 允许线程的空闲时间，超过核心线程数之外的线程在空闲时间到达后会被销毁
     */
    @Value("${spring.task.execution.pool.keep-alive:60s}")
    private Duration keepalive;

    /**
     * 是否允许核心线程超时。这样可以动态增长和缩小池。
     */
    @Value("${spring.task.execution.pool.allow-core-thread-timeout:true}")
    private boolean allowCoreThreadTimeout;

    /**
     * 执行器是否应在关闭时等待计划任务完成。
     */
    @Value("${spring.task.execution.shutdown.await-termination:true}")
    private boolean awaitTermination;

    /**
     * 执行器等待剩余任务完成的最长时间。
     */
    @Value("${spring.task.execution.shutdown.await-termination-period:600s}")
    private Duration awaitTerminationPeriod;

    /**
     * 当容器中有多个 TaskExecutor 实例时，@Async 注解默认使用名称为 taskExecutor 的那个bean，可以指定bean名称
     *
     * @return org.springframework.core.task.TaskExecutor
     */
    @Bean
    @Primary
    public TaskExecutor taskExecutor() {
        // 默认是ThreadPoolTaskExecutor，bean名称为applicationTaskExecutor
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize(); // 此方法必须调用，否则会抛出ThreadPoolTaskExecutor未初始化异常
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setCorePoolSize(coreSize); // 可以在运行时修改此设置，例如通过 JMX。
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds((int) keepalive.toSeconds());
        executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeout);
        executor.setWaitForTasksToCompleteOnShutdown(awaitTermination);
        executor.setAwaitTerminationSeconds((int) awaitTerminationPeriod.toSeconds());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
