package com.zhaolq.mars.demo.a;

import com.zhaolq.mars.tool.core.date.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 *
 * @author zhaolq
 * @date 2021/6/24 14:39
 */
public class Test {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) throws Exception {

        System.out.println(Date.from(LocalDateTime.now().plusSeconds(10).toInstant(ZoneOffset.of("+8"))));

        // 创建定时器
        Timer timer = new Timer();
        // 提交计划任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时任务执行了...");
                while (true) {
                    int sum = 0;
                    for (int i = 1; i <= 10000; i++) {
                        sum += i;
                    }
                    System.out.println(sum);
                }
            }
        }, Date.from(LocalDateTime.now().plusSeconds(3).toInstant(ZoneOffset.of("+8"))));
    }
}
