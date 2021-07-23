package com.zhaolq.mars.demo.a;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * @author zhaolq
 * @date 2021/6/24 14:39
 */
public class Test {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("新建线程组1");

        MyThread myThread = new MyThread();
        Thread thread1 = new Thread(group, myThread, "thread1");
        Thread thread2 = new Thread(group, myThread, "thread2");

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("此线程组的名称：" + group.getName());
        System.out.println("此线程组及其子组中活动线程数的估计值：" + group.activeCount());

        System.out.println("将此线程组及其子组中的每个活动线程复制到指定的数组中：");
        Thread[] arrary = new Thread[2];
        int num = group.enumerate(arrary);
        System.out.println("放入数组的线程数：" + num);

        System.out.println("设置组的最大优先级,线程组中已经具有更高优先级的线程不受影响：");
        group.setMaxPriority(5);

        System.out.println("返回此线程组的父级：" + group.getParent());

        System.out.println("中断此线程组中的所有线程：");
        group.interrupt();

        System.out.println("测试此线程组是否已被销毁：" + group.isDestroyed());
        System.out.println("销毁此线程组及其所有子组。此线程组必须为空，表示此线程组中的所有线程都已停止：");
        group.destroy();
        System.out.println("测试此线程组是否已被销毁：" + group.isDestroyed());

    }
}

class MyThread implements Runnable {
    @Override
    public void run() {
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getThreadGroup().getName() + " " + Thread.currentThread().getName());
        }
        System.out.println(Thread.currentThread().getName() + " 线程已中断 ");
    }
}