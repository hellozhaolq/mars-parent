package com.zhaolq.mars.service.admin.controller.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaolq.mars.common.core.result.R;
import com.zhaolq.mars.service.admin.task.AsyncTask;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试控制器
 *
 * @Author zhaolq
 * @Date 2023/5/27 9:14:18
 * @Since 1.0.0
 */
@Slf4j
@RestController
@Tag(name = "测试模块", description = "测试模块")
@RequestMapping(path = "/testTask", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class TestTaskController {

    @Resource
    private AsyncTask asyncTask;

    @GetMapping("/testAsyncTask")
    @Operation(summary = "测试异步任务", description = "测试异步任务")
    public R<Object> testAsyncTask() throws ExecutionException, InterruptedException {
        asyncTask.dealNoReturnTask();
        Future<String> f = asyncTask.dealHaveReturnTask();
        log.debug("main线程执行完毕");
        log.debug(f.get());
        return R.success(f.get());
    }
}
