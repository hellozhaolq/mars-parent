package com.zhaolq.demo.thread;

import java.util.Date;

/**
 * 线程的停止与中断
 *
 * public final void stop():让线程停止，过时了，但是还可以使用。
 * public void interrupt():中断线程。把线程的状态终止，并抛出一个InterruptedException。
 *
 * @author zhaolq
 * @date 2020/11/13 16:58
 */
public class Demo03 {
    public static void main(String[] args) {
        ThreadStop ts = new ThreadStop();
        ts.start();

        // 你超过三秒不醒过来，我就干死你
        try {
            Thread.sleep(3000);
            // ts.stop();
            ts.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadStop extends Thread {
    @Override
    public void run() {
        System.out.println("开始执行：" + new Date());

        // 我要休息10秒钟，亲，不要打扰我哦
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // e.printStackTrace();
            System.out.println("线程被终止了");
        }

        System.out.println("结束执行：" + new Date());
    }
}
