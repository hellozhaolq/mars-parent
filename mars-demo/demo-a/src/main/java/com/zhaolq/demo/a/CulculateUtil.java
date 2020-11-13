package com.zhaolq.demo.a;

/**
 *
 *
 * @author zhaolq
 * @date 2020/11/13 14:23
 */
public class CulculateUtil {
    /**
     * @Fields sum : Description
     */
    private static long sum = 0;
    /**
     * @Fields max : Description
     */
    private static long max = 0;
    /**
     * @Fields min : Description
     */
    private static long min = 999999999;
    /**
     * @Fields times : 调用次数
     */
    private static int times = 0;

    /**
     * 某个线程结束时需要回掉的方法.
     * @param dif 花费时间
     * @throws
     */
    public static void finish(long dif) {
        times++;
        if (dif < min) {
            min = dif;
        }
        if (dif > max) {
            max = dif;
        }
        sum += dif;
    }

    /**
     * @return max
     */
    public static long getMax() {
        return max;
    }

    /**
     * @param max 要设置的 max
     */
    public static void setMax(long max) {
        CulculateUtil.max = max;
    }

    /**
     * @return min
     */
    public static long getMin() {
        return min;
    }

    /**
     * @param min 要设置的 min
     */
    public static void setMin(long min) {
        CulculateUtil.min = min;
    }

    /**
     * @return times
     */
    public static int getTimes() {
        return times;
    }

    /**
     * @param times 要设置的 times
     */
    public static void setTimes(int times) {
        CulculateUtil.times = times;
    }

    /**
     * @return sum
     */
    public static long getSum() {
        return sum;
    }

    /**
     * @param sum 要设置的 sum
     */
    public static void setSum(long sum) {
        CulculateUtil.sum = sum;
    }

}
