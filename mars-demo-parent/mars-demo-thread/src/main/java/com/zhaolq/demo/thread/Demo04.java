package com.zhaolq.demo.thread;

/**
 * 实现Runnable接口
 *
 * 步骤：
 * ​ 1). 自定义类MyRunnable实现Runnable接口
 * ​ 2). 重写run()方法
 * ​ 3). 创建MyRunnable类的对象
 * ​ 4). 创建Thread类的对象，并把3)步骤的对象作为构造参数传递
 *
 * @author zhaolq
 * @date 2020/11/13 17:01
 */
public class Demo04 {
    public static void main(String[] args) {
        // 创建MyRunnable类的对象
        MyRunnable my = new MyRunnable();

        // 创建Thread类的对象，并把3)步骤的对象作为构造参数传递
        // Thread(Runnable target)
        // Thread t1 = new Thread(my);
        // Thread t2 = new Thread(my);
        // t1.setName("周润发AAA");
        // t2.setName("刘德华BBB");

        // Thread(Runnable target, String name)
        Thread t1 = new Thread(my, "周润发AAA");
        Thread t2 = new Thread(my, "刘德华BBB");

        t1.start();
        t2.start();
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int x = 0; x < 100; x++) {
            try {
                // 由于实现接口的方式就不能直接使用Thread类的getName()方法了,但是可以间接的使用
                System.out.println(Thread.currentThread().getName() + ":" + x);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
