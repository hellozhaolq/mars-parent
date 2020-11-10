package com.zhaolq.demo.a;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author zhaolq
 * @date 2020/8/25 10:14
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        // 跑满cpu
        while (true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                }
            }).start();
        }
    }

}