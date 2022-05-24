/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.demo.reptile;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 爬虫: 通过selenium操作浏览器打开百度进行搜索
 *
 * @author zhaolq
 * @date 2022/5/24 18:49
 * @since 1.0.0
 */
public class Reptile {
    public static void main(String[] args) {
        // System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();



        // 1.创建webdriver驱动
        WebDriver driver = new ChromeDriver();
        Cookie cookie = new Cookie("Cookie", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJjdXN0SWQiOiJlNU8vaEpyc2FJelJCZTRJbVpPa2VvYkgveWJUREh5Q290cE9Pa3dGU1VJWW8rRWhyQXpja0Y5Z1N4b1JqMWNnTUZDQTBuQjgrK1dyTkEvRGREMHl4QVFDSUFWOVM1OWFkUXBjdGVwYmRMQ0RmL2JjYldzZkMzY0VwRlE1M0NDdDQ1UDA3dUtnNkF4LytsdXd4KzZHZHB0bW9KOVAyczkyMkJFbGlyOEVjQTg9IiwidXNlcklkIjoiTFhRSXprU1RkWHlzOUM4WDNlcE9RMms3cUc5VnV1ZUpDTGlqalo4elFGRG94enB0RlZlSVN2N2s2a2phL3JjTStXVHk1Uy8valNLN3ptTFlXeThITFFIN1VvNWs2cmtWUEFiekRuYk15WjN3QmsrU09UNm42N2trOVE5L2dvK3gzWDRyWExDRmZtZjF4TElQR2h6MTRuYmdsVjgrc3c0ZUcwUE1TeDJGVHBNPSIsImVtcElkIjoiRHFqdmx4TzVXdXFabURSeFplQjN2YWJXYVJoYUtqVEdKd2JScWZKWVdLeFdtYnVRQ0VhYUpHbHRTdjY5K0FtVHFJNjNMQTA5UXVkekFMWjEva3RKaDYwVUV1SG5mUVk4aDhESldRalpXL2NVUTZPSWJQRDhrZit6Uy9XMzJwN0s0d1R4aWZiVnBwcmZmZnE1NFhaQzNNQmtTU0dGL3BPeHN5a000UzNnWkM4PSIsInRva2VuVHlwZSI6ImVtcFdlYiIsInRpbWVzdGFtcCI6MTY1MzM5NjYxODgzNH0.4d6G6o4o_JjReeovEXbX8bnxtavQbz3NVcg1H80maYA");

        driver.manage().addCookie(cookie);
        driver.get("https://yihr.chinasoftinc.com:18010/#/personal/seedakadata?top=2&left=106&yearM=2022-05");
        // 3.获取输入框，输入selenium
        // driver.findElement(By.id("kw")).sendKeys("selenium");
        // 4.获取“百度一下”按钮，进行搜索
        // driver.findElement(By.id("su")).click();
        // 5.退出浏览器
        // driver.quit();
    }
}
