package com.zhaolq.mars.demo.spark.common;

/**
 * @author zhaolq
 * @date 2023/8/7 11:42:54
 */
public class CommonUtils {
    public static final String dropTable = "drop table if exists %s";
    public static final String createTable = "create table %s as select * from %s";

    public static String dropTableSql(String tableName) {
        return String.format(dropTable, tableName);
    }

    public static String createTableSql(String inputTableName, String outputTableName) {
        return String.format(createTable, outputTableName, inputTableName);
    }
}
