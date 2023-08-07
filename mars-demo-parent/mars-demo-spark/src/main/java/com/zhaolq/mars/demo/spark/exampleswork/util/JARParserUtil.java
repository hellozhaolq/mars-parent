package com.zhaolq.mars.demo.spark.exampleswork.util;

/**
 * @author zhaolq
 * @date 2023/8/7 14:37:56
 */
public class JARParserUtil {
    public JARParserUtil() {
    }

    public static String getKeyValue(String[] args, String key) {
        int argNum = args.length;

        for (int i = 1; i < argNum; ++i) {
            String[] fields = args[i].split("=");
            String paraKey = fields[0];
            String paraValue = fields[1];
            if (paraKey.equals(key)) {
                return paraValue;
            }
        }

        return "";
    }
}
