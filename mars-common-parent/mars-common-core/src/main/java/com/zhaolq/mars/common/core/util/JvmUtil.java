package com.zhaolq.mars.common.core.util;

import java.lang.management.ManagementFactory;

/**
 * JVM工具
 *
 * @Author zhaolq
 * @Date 2023/5/11 16:14:31
 * @Since 1.0.0
 */
public class JvmUtil {
    /**
     * 获取JVM内存信息，包括 总内存、剩余内存、最大内存、最大可用内存
     *
     * @return java.lang.String
     */
    public static String getJvmMemoryInfo() {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        String jvmTotalMemory = String.join(":", "JVM总内存", String.valueOf(Runtime.getRuntime().totalMemory())); // 默认物理内存的1/64
        String jvmFreeMemory = String.join(":", "JVM剩余内存", String.valueOf(Runtime.getRuntime().freeMemory())); //
        String jvmMaxMemory = String.join(":", "JVM最大内存", String.valueOf(Runtime.getRuntime().maxMemory())); // 默认是物理内存的1/4
        // 最大内存-已用内存=最大内存-(总内存-剩余内存)
        String jvmUsableMemory = String.join(":", "JVM最大可用内存", String.valueOf(maxMemory - totalMemory + freeMemory));

        String LoadedClassCount = String.join(":", "当前加载到 Java 虚拟机中的类数",
                String.valueOf(ManagementFactory.getClassLoadingMXBean().getLoadedClassCount()));

        return String.join("\n", jvmTotalMemory, jvmFreeMemory, jvmMaxMemory, jvmUsableMemory, LoadedClassCount);
    }
}
