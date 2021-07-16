package com.zhaolq.mars.demo.a;

/*
 * 分析：
 *         资源类：Student
 *         设置学生数据:SetThread(生产者)
 *         获取学生数据：GetThread(消费者)
 *         测试类:StudentDemo
 *
 * 问题1：按照思路写代码，发现数据每次都是:null---0
 * 原因：我们在每个线程中都创建了新的资源,而我们要求的是设置和获取线程的资源应该是同一个。
 * 如何实现呢?
 *         在外界把这个数据创建出来，通过构造方法传递给其他的类。
 *
 * 问题2：为了数据的效果好一些，我加入了循环和判断，给出不同的值,这个时候产生了新的问题
 *         A:同一个数据出现多次
 *         B:姓名和年龄不匹配
 * 原因：
 *         A:同一个数据出现多次
 *             CPU的一点点时间片的执行权，就足够你执行很多次。
 *         B:姓名和年龄不匹配
 *             线程运行的随机性
 * 线程安全问题：
 *         A:是否是多线程环境        是
 *         B:是否有共享数据        是
 *         C:是否有多条语句操作共享数据    是
 * 解决方案：
 *         加锁。
 *         注意：
 *             A:不同种类的线程都要加锁。
 *             B:不同种类的线程加的锁必须是同一把。
 */
public class StudentDemo {
    public static void main(String[] args) {
        Test2 t = new Test2();
        System.out.println("a: " + t.a()); // a: 失败 java.lang.IllegalMonitorStateException
        System.out.println("b: " + t.b()); // b: 成功
        System.out.println("c: " + t.c()); // c: 失败 java.lang.IllegalMonitorStateException
        System.out.println("d: " + t.d()); // d: 成功
    }
}

class Test2 {
    public String a() {
        StringBuilder result = new StringBuilder();
        try {
            Thread.currentThread().wait(1000);
            result.append("成功");
        } catch (InterruptedException e) {
            result.append("失败 ").append(e.toString());
        } catch (IllegalMonitorStateException e) {
            result.append("失败 ").append(e.toString());
        }
        return result.toString();
    }

    public String b() {
        StringBuilder result = new StringBuilder();
        synchronized (Thread.currentThread()) {
            try {
                Thread.currentThread().wait(1000);
                result.append("成功");
            } catch (InterruptedException e) {
                result.append("失败 ").append(e.toString());
            } catch (IllegalMonitorStateException e) {
                result.append("失败 ").append(e.toString());
            }
        }
        return result.toString();
    }

    public String c() {
        StringBuilder result = new StringBuilder();
        try {
            this.wait(1000);
            result.append("成功");
        } catch (InterruptedException e) {
            result.append("失败 ").append(e.toString());
        } catch (IllegalMonitorStateException e) {
            result.append("失败 ").append(e.toString());
        }
        return result.toString();
    }

    public String d() {
        StringBuilder result = new StringBuilder();
        synchronized (this) {
            try {
                this.wait(1000);
                result.append("成功");
            } catch (InterruptedException e) {
                result.append("失败 ").append(e.toString());
            } catch (IllegalMonitorStateException e) {
                result.append("失败 ").append(e.toString());
            }
        }
        return result.toString();
    }
}