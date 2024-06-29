package com.zhaolq.mars.service.admin.task;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 异步任务
 * <p>
 * 使用@Async注解的异步方法的返回值只能为void或者Future。
 * 使用经过spring容器管理的bean(代理对象)调用@Async方法才会触发异步执行，
 *
 * @Author zhaolq
 * @Date 2023/5/27 9:07:08
 */
@Component
@Slf4j
public class AsyncTask {
    @Async
    public void dealNoReturnTask() {
        log.debug("Async invoke return void start, ThreadName: " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("Async invoke return void end, ThreadName: " + Thread.currentThread().getName());
    }

    @Async
    public Future<String> dealHaveReturnTask() {
        log.debug("Async Invoke Return Future, ThreadName: " + Thread.currentThread().getName());
        Future<String> future;
        try {
            Thread.sleep(3000);
            future = new AsyncResult<String>("success");
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error");
        }
        return future;
    }
}
