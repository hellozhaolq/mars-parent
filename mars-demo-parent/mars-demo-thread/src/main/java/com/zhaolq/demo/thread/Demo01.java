package com.zhaolq.demo.thread;

import java.util.Date;

/**
 * 继承Thread类
 *
 * @author zhaolq
 * @date 2020/11/13 16:35
 */
public class Demo01 {
    public static void main(String[] args) {
        /********************************************************************************/

        // 创建线程对象，无参构造+setXxx()
//        MyThread my1 = new MyThread();
//        MyThread my2 = new MyThread();
//        my1.setName("周润发"); // 设置线程名称
//        my2.setName("刘德华");
//        my1.start();
//        my2.start();
//        my1.start(); // IllegalThreadStateException:非法的线程状态异常。// 为什么呢?因为这个相当于是my1线程被调用了两次。而不是启动两个线程。

        /********************************************************************************/

        // 带参构造方法给线程起名字
        MyThread my1 = new MyThread("周润发AAA");
        MyThread my2 = new MyThread("刘德华BBB");
        MyThread my3 = new MyThread("梁朝伟CCC");

        // 更改线程优先级，默认是5
//        my1.setPriority(10); // 最大值，线程优先级高仅仅表示线程获取的 CPU时间片的几率高，但是要在次数比较多，或者多次运行的时候才能看到比较好的效果。
//        my2.setPriority(1); // 最小值
        // 获取默认优先级
        System.out.println(my1.getPriority());
        System.out.println(my2.getPriority());
        System.out.println(my3.getPriority());
        // 获取main方法所在的线程对象的名称
        System.out.println(Thread.currentThread().getName());

        my1.start();
        try {
            my1.join(); // 等待该线程终止，然后继续执行后面的代码
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        my2.start();
        my3.start();

        /********************************************************************************/
    }
}

class MyThread extends Thread {
    public MyThread() {
    }

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int x = 0; x < 100; x++) {
            System.out.println(getName() + ":" + x + ",日期：" + new Date());

//            try {
//                Thread.sleep(1000);// 线程休眠
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

//            Thread.yield();// 暂停当前正在执行的线程对象，并执行其他线程。
        }
    }
}
