package com.zhaolq.mars.demo.spark.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * CommonUtils
 *
 * @author zWX1085453
 * @since 2022/5/17 17:05
 */
@Slf4j
public class SparkUtils {
    /**
     * 废除（缓存的）元数据shell方式
     *
     * @param outputTable outputTable
     * @param impalaURL impalaURL
     * @return void
     * @throws Exception
     */
    public static void invalidateMetadataWithProcess(String outputTable, String impalaURL) throws Exception {
        String[] validTableStr = new String[]{
                "impala-shell",
                "-i",
                impalaURL,
                "-q",
                String.format(Locale.ROOT, "invalidate metadata %s", outputTable)};
        Process process = Runtime.getRuntime().exec(validTableStr);
        process.waitFor();
        try (
                InputStream in = process.getInputStream();
                BufferedReader read = new BufferedReader(new InputStreamReader(in));
                InputStream in2 = process.getErrorStream();
                BufferedReader read2 = new BufferedReader(new InputStreamReader(in2))) {

            StringBuilder text = new StringBuilder();
            String data;
            while ((data = read.readLine()) != null) {
                text.append(data).append(" ");
            }
            log.info("text: " + text.toString());

            StringBuilder text2 = new StringBuilder();
            String data2;
            while ((data2 = read2.readLine()) != null) {
                text2.append(data2).append(" ");
            }
            log.info("text2: " + text2.toString());

            String result = read.readLine();
            log.info("INFO: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 废除（缓存的）元数据sql方式
     *
     * @param outputTable outputTable
     * @param impalaURL impalaURL
     * @return void
     */
    public static void invalidateMetadataWithSql(String outputTable, String impalaURL) {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            log.error("Not found org.apache.hive.jdbc.HiveDriver");
            throw new IllegalArgumentException(e);
        }

        try (Connection connection = DriverManager.getConnection(impalaURL);
             Statement statement = connection.createStatement()) {
            statement.execute(String.format("invalidate metadata %s", outputTable));
        } catch (SQLException e) {
            log.error("Get Connection Error");
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Failed to save table", e);
        }
    }

    /**
     * 数组转map
     *
     * @param args args
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    public static Map<String, String> array2Map(String[] args) {
        Map<String, String> argsMap = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String[] fields = args[i].split("=", 2);
            argsMap.put(fields[0], fields[1]);
        }
        return argsMap;
    }
}
