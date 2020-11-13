package com.zhaolq.demo.a;

import java.util.Date;

/**
 *
 *
 * @author zhaolq
 * @date 2020/11/13 14:16
 */
public class COMUT {
    public COMUT() {

    }

    public static void main(String[] args) {
        int times = 5000;
        int type = 3;
        try {
            Date setTime = new Date();
            // 设定为启动程序后的2s执行
            setTime.setTime(setTime.getTime() + 5000);
            // 测试多线程情况下的性能问题
            Thread[] thread = new Thread[times];
            for (int i = 0; i < times; i++) {
                thread[i] = new Thread(new ThreadTest("thread" + i, setTime, type));
            }
            for (int i = 0; i < times; i++) {
                thread[i].start();
            }

            for (int i = 0; i < times; i++) {
                thread[i].join();
            }
            // 等待所有子线程执行完毕后，输出下面的内容
            System.out.println("本次测试方案:" + type + "。最快:" + CulculateUtil.getMin() + "ms。最慢:" + CulculateUtil.getMax()
                    + "ms。平均:" + CulculateUtil.getSum() / CulculateUtil.getTimes() + "ms.");
        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
