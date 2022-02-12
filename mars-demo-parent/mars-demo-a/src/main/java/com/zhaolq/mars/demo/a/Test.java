package com.zhaolq.mars.demo.a;

import com.zhaolq.mars.tool.core.utils.RandomUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 *
 * @author zhaolq
 * @date 2021/6/24 14:39
 */
public class Test {
    public static void main(String[] args) {
        // 步骤一：创建 Callable 接口的实现类，并实现 call() 方法。创建 Callable 实现类的实例
        Callable<Integer> call = () -> {
            System.out.println("线程任务开始，call()方法执行....");

            int a = RandomUtils.randomInt(1, 3);
            int b = RandomUtils.randomInt(1, 3);
            if (a != b) {
                System.out.println("两个随机数不相等");
                throw new Exception("两个随机数不相等");
            } else {
                System.out.println("两个随机数相等");
            }

            return a;
        };
        // 步骤二：使用 FutureTask 类来包装 Callable 对象
        FutureTask<Integer> task = new FutureTask<>(call); // 提供了检查任务执行情况和检索结果的方法，具体查看Future接口。
        System.out.println("task.isDone(): " + task.isDone());
        // 步骤三：使用 FutureTask 对象作为 Thread 对象的 target 创建并启动新线程
        new Thread(task).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task.isDone(): " + task.isDone());

        // --------------- 这里是在线程启动之后，线程结果返回之前 ---------------
        System.out.println("-------------- 这里可以为所欲为 --------------");
        // --------------- 这里是在线程启动之后，线程结果返回之前 ---------------

        // 步骤四：为所欲为完毕后，调用 FutureTask 对象的 get() 方法来获得子线程执行结束后的返回值
        // Integer result = task.get(1, TimeUnit.SECONDS);
        Integer result = null;
        while (result == null || !task.isDone()) {
            try {
                // 可以多次调用
                result = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                result = 0;
            } catch (ExecutionException e) {
                e.printStackTrace();
                result = 0;
            }
        }
        System.out.println("task.isDone(): " + task.isDone());
        System.out.println("主线程中拿到异步任务执行的结果为：" + result);
    }
}
