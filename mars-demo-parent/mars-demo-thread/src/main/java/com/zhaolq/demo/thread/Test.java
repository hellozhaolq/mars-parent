package com.zhaolq.demo.thread;

import com.zhaolq.mars.tool.core.constant.StringPool;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * @author zhaolq
 * @date 2021/7/9 16:41
 */
public class Test {

    public static void main(String[] args) {
        Thread.currentThread().setName("main");

        CustomRunnable runnable = new CustomRunnable();

        Thread threadA = new Thread(runnable, "线程A");
        Thread threadB = new Thread(runnable, "线程B");

        threadA.start();
        threadB.start();

        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println(threadA.getName() + " - " + threadA.getState());
                System.out.println(threadB.getName() + " - " + threadB.getState());
                if(Constant.lock.tryLock()){
                    System.out.println(Thread.currentThread().getName() + " - " + "已获取锁");
                    System.out.println(Thread.currentThread().getName() + " - " + "已释放锁");
                    Constant.lock.unlock();
                } else {
                    System.out.println(Thread.currentThread().getName() + " - " + "锁不可用");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CustomRunnable implements Runnable {
    @Override
    public void run() {
        while (Constant.count.get() < 1000) {
            Constant.count.addAndGet(1);
            if(Constant.threadName.equals(Thread.currentThread().getName())){
                // 为了保证不交替获取锁
                continue;
            }
            try {
                if (Constant.lock.tryLock()) {
                    Constant.threadName = Thread.currentThread().getName();
                    Constant.lock.lock();
                    System.out.println(Thread.currentThread().getName() + " - " + "已获取锁");
                } else {
                    System.out.println(Thread.currentThread().getName() + " - " + "锁不可用");
                }
            } finally {
                if (Constant.lock.tryLock()) {
                    System.out.println(Thread.currentThread().getName() + " - " + "已释放锁");
                    Constant.lock.unlock();
                } else {
                    System.out.println(Thread.currentThread().getName() + " - " + "锁不可用");
                }
            }
        }
    }
}

class Constant {
    public static Lock lock = new ReentrantLock();
    public static String threadName = "";
    public static AtomicInteger count = new AtomicInteger(0);
}

