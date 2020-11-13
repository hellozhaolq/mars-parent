package com.zhaolq.demo.a;

import java.util.Date;

/**
 *
 *
 * @author zhaolq
 * @date 2020/11/13 14:23
 */
public class ThreadTest implements Runnable {
    /**
     * @Fields threadName : Description
     */
    private String threadName;
    /**
     * @Fields setTime : Description
     */
    private Date setTime;

    /**
     * @Fields ic : Description
     */
    ICUtilsInstance ic;
    /**
     * @Fields type : Description
     */
    int type;

    /**
     * .
     * <p>
     * Title: run
     * </p>
     * <p>
     * Description:
     * </p>
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while (true) {
            Date d = new Date();
            if (d.after(setTime)){
                break;
            }
        }
        Date d1 = new Date();
        if (type == 1) {
            ICUtils.fixPersonIDCode("650103760113073");
            ICUtils.isIdentityId("650103760113073");
        } else if (type == 2) {
            ic = new ICUtilsInstance();
            ic.fixPersonIDCode("650103760113073");
            ic.isIdentityId("650103760113073");
        } else if (type == 3) {
            ic.fixPersonIDCode("650103760113073");
            ic.isIdentityId("650103760113073");
        }
        Date d2 = new Date();
        long dif = d2.getTime() - d1.getTime();
        System.out.println("线程名为:" + threadName + "的线程,运行时间为:" + dif + "ms.");
        CulculateUtil.finish(dif);
    }

    /**
     * 创建一个新的实例 ThreadTest.
     * @param threadName
     */
    public ThreadTest(String threadName, Date setTime, int type) {
        super();
        this.threadName = threadName;
        this.setTime = setTime;
        this.type = type;
        if (type == 3) {
            ic = new ICUtilsInstance();
        }
    }

}
