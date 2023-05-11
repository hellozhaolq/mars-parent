package com.zhaolq.mars.tool.core.utils;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.SystemUtil;

/**
 * JVM工具
 *
 * @author zhaolq
 * @date 2023/5/11 16:14:31
 * @since 1.0.0
 */
public class JvmUtils {
    /**
     * 获取JVM内存信息，包括 总内存、剩余内存、最大内存、最大可用内存
     *
     * @return java.lang.String
     */
    public static String getJvmMemoryInfo() {
        String jvmTotalMemory = String.join(":", "JVM总内存", String.valueOf(RuntimeUtil.getTotalMemory())); // 默认物理内存的1/64
        String jvmFreeMemory = String.join(":", "JVM剩余内存", String.valueOf(RuntimeUtil.getFreeMemory())); //
        String jvmMaxMemory = String.join(":", "JVM最大内存", String.valueOf(RuntimeUtil.getMaxMemory())); // 默认是物理内存的1/4
        String jvmUsableMemory = String.join(":", "JVM最大可用内存", String.valueOf(RuntimeUtil.getUsableMemory())); // 最大内存-已用内存=最大内存-(总内存-剩余内存)

        String LoadedClassCount = String.join(":", "当前加载到 Java 虚拟机中的类数", String.valueOf(SystemUtil.getClassLoadingMXBean().getLoadedClassCount()));

        return String.join("\n",
                jvmTotalMemory,
                jvmFreeMemory,
                jvmMaxMemory,
                jvmUsableMemory,
                LoadedClassCount);
    }
}
