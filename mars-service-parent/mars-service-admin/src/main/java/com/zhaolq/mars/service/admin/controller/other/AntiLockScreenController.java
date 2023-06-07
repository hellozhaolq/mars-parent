package com.zhaolq.mars.service.admin.controller.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaolq.mars.service.admin.task.ScheduledMouseMove;
import com.zhaolq.mars.common.core.result.R;
import com.zhaolq.mars.common.core.result.ResultCode;

import lombok.extern.slf4j.Slf4j;

/**
 * 防锁屏
 *
 * @author zhaolq
 * @date 2023/5/17 10:49:07
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(path = "/antiLockScreen", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class AntiLockScreenController {
    @Autowired
    private ScheduledMouseMove scheduledMouseMove;

    @RequestMapping(value = "/turnOn/{antiLockScreenMinutes}")
    public R<Object> turnOnAntiLockScreen(@PathVariable long antiLockScreenMinutes) throws Exception {
        if (!(antiLockScreenMinutes > 0)) {
            return R.failure(ResultCode.FAILURE);
        }
        return R.success(scheduledMouseMove.turnOnAntiLockScreen(antiLockScreenMinutes));
    }

    @RequestMapping(value = "/turnOff")
    public R<Object> turnOffAntiLockScreen() {
        return R.success(scheduledMouseMove.turnOffAntiLockScreen());
    }
}
