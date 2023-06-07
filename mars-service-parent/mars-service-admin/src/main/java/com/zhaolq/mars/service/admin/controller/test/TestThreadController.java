package com.zhaolq.mars.service.admin.controller.test;

import java.util.concurrent.TimeUnit;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaolq.mars.common.core.result.R;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试线程
 *
 * @author zhaolq
 * @date 2023/5/29 11:31:05
 * @since 1.0.0
 */
@Slf4j
@RestController
@Tag(name = "测试模块", description = "测试模块")
@RequestMapping(path = "/testThread", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class TestThreadController {

    @GetMapping("/sleep/{sleepMilliSeconds}")
    @Operation(summary = "并发测试", description = "并发测试")
    public R<Object> sleep(@PathVariable long sleepMilliSeconds) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(sleepMilliSeconds);
        log.debug(String.valueOf(sleepMilliSeconds));
        return R.success();
    }

}
