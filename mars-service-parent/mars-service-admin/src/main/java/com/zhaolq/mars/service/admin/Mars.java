package com.zhaolq.mars.service.admin;

import java.util.Set;

import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.lang.ConsoleTable;

/**
 * @author zhaolq
 * @date 2023/5/24 10:31:03
 * @since 1.0.0
 */
public class Mars {
    public static void main(String[] args) {
        Set<Class<?>> allUtils = ClassScanner.scanAllPackageByAnnotation("com.zhaolq", FunctionalInterface.class);
        ConsoleTable consoleTable = ConsoleTable.create().setSBCMode(true).addHeader("工具类名", "所在包");
        for (Class<?> clazz : allUtils) {
            consoleTable.addBody(clazz.getSimpleName(), clazz.getPackage().getName());
        }
        consoleTable.print();
    }
}
