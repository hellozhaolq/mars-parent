package com.zhaolq.mars.service.admin.schedule;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.hutool.core.date.DatePattern;
import lombok.extern.slf4j.Slf4j;

/**
 * 光标移动计划
 *
 * @author zhaolq
 * @date 2023/5/17 10:18:09
 * @since 1.0.0
 */
@Component
@Slf4j
public class MouseMoveSched {
    public LocalDateTime mouseMoveEndTime = null;

    /**
     * 鼠标移动定时任务
     *
     * @return void
     */
    @Scheduled(cron = "0 * * * * ?")
    public void mouseMoveSchedule() {
        if (mouseMoveEndTime != null && mouseMoveEndTime.compareTo(LocalDateTime.now()) >= 0) {
            log.debug("Sport " + LocalDateTime.now().format(DatePattern.NORM_DATETIME_FORMATTER));
            mouseMove(-1, -1);
            mouseMove(1, 1);
        }
    }

    /**
     * 开启防锁屏
     *
     * @return void
     */
    public synchronized String turnOnAntiLockScreen(long antiLockScreenMinutes) {
        if (antiLockScreenMinutes <= 0)
            return null;
        mouseMoveEndTime = LocalDateTime.now().plusMinutes(antiLockScreenMinutes).withSecond(0).withNano(0);
        String data = "Turn on Anti-Lock Screen, end time " + mouseMoveEndTime.format(DatePattern.NORM_DATETIME_FORMATTER);
        log.info(data);
        return data;
    }

    /**
     * 关闭防锁屏
     *
     * @return void
     */
    public synchronized Boolean turnOffAntiLockScreen() {
        mouseMoveEndTime = null;
        log.info("Turn off Anti-Lock Screen");
        return Boolean.TRUE;
    }

    /**
     * 鼠标指针移动
     *
     * @param xOffset 横向偏移量
     * @param yOffset 纵向偏移量
     */
    public void mouseMove(int xOffset, int yOffset) {
        Point point = MouseInfo.getPointerInfo().getLocation();
        int xAxis = (int) point.getX() + xOffset;
        int yAxis = (int) point.getY() + yOffset;
        Robot robot;
        try {
            robot = new Robot();
            robot.mouseMove(xAxis, yAxis);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        log.trace(xAxis + "---" + yAxis);
    }

    /**
     * 鼠标点击
     *
     * @param leftOrRight leftOrRight
     */
    public void mousePress(String leftOrRight) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        if ("right".equalsIgnoreCase(leftOrRight)) {
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }
        if ("left".equalsIgnoreCase(leftOrRight)) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }
}
