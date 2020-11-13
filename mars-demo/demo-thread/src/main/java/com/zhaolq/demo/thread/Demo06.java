package com.zhaolq.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 带返回值的线程实现方式
 *
 *      我们发现之前提到的不管是继承Thread类还是实现Runnable接口，发现有两个问题，第一个是无法抛出更多的异常，第二个是线程执行完毕之的后并无法获得线程返回值。
 * 那么下面的这种实现方式就可以完成我们的需求。这种方式的实现就是我们后面要详细介绍的Future模式，只是在jdk5的时候，官方给我们提供了可用的API，我们可以直接使用。
 * 但是使用这种方式创建线程比上面两种方式要复杂一些，步骤如下：
 *      1)、创建一个类实现Callable接口，实现call方法。这个接口类似于Runnable接口，但比Runnable接口更加强大，增加了异常和返回值。
 *      2)、创建一个FutureTask，指定Callable对象，做为线程任务。
 *      3)、创建线程，指定线程任务。
 *      4)、启动线程
 *
 * @author zhaolq
 * @date 2020/11/13 17:42
 */
public class Demo06 {
    public static void main(String[] args) throws Exception {

        // 创建线程任务
        Callable<Integer> call = () -> {
            System.out.println("线程任务开始执行了....");
            Thread.sleep(2000);
            return 1;
        };

        // 将任务封装为FutureTask
        FutureTask<Integer> task = new FutureTask<>(call);

        // 开启线程，执行线程任务
        new Thread(task).start();

        // ====================
        // 这里是在线程启动之后，线程结果返回之前
        System.out.println("这里可以为所欲为....");
        // ====================

        // 为所欲为完毕之后，拿到线程的执行结果
        Integer result = task.get();
        System.out.println("主线程中拿到异步任务执行的结果为：" + result);
    }
}