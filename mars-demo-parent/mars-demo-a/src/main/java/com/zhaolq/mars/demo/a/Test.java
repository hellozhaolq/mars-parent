package com.zhaolq.mars.demo.a;

import java.util.Set;

import com.zhaolq.mars.common.core.lang.ConsoleTable;

import cn.hutool.core.lang.ClassScanner;

/**
 * @author zhaolq
 * @date 2021/6/24 14:39
 */
public class Test {
    public static void main(String[] args) {
        Set<Class<?>> allUtils = ClassScanner.scanAllPackageByAnnotation("com.zhaolq", FunctionalInterface.class);
        ConsoleTable consoleTable = ConsoleTable.create().setDBCMode(false).addHeader("className", "packageName");
        for (Class<?> clazz : allUtils) {
            consoleTable.addBody(clazz.getSimpleName(), clazz.getPackage().getName());
        }
        consoleTable.print();
    }
}
