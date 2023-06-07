package com.zhaolq.mars.demo.spark.common;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.spark.sql.SparkSession;

import com.alibaba.fastjson2.JSONObject;

import lombok.extern.slf4j.Slf4j;


/**
 * IBEX Java 组件开发 Spark 应用基类
 *
 * @author zhaolq
 * @date 2022/12/9 17:01
 * @since 1.0.0
 */
@Slf4j
public abstract class BaseSparkApplication {
    public Map<String, String> argsMap;
    public String hdfsPath;
    public String serverIP;
    public String impalaURL;
    public Properties properties;
    public String isLocal;
    public SparkSession sparkSession;

    public final void execute(String[] args) throws Exception {
        // 1.Parameter initialization
        init(args);
        // 2.Establish connection
        create();
        // 3.Pre-operation
        before();
        // 4.Data processing, open interface to user
        process();
        // 5.Post-operation
        after();
        // 6.Resource destroy
        destroy();
    }

    private void init(String[] args) {
        log.info("args param: " + Arrays.toString(args));

        JSONObject json = JSONObject.parseObject(args[0]);
        hdfsPath = json.getString("hdfs");
        serverIP = json.getString("cscloud-position-server");
        impalaURL = json.getString("impalaURL");

        argsMap = SparkUtils.array2Map(args);

        isLocal = argsMap.get("local") != null && "local".equalsIgnoreCase(argsMap.get("local")) ? "local[2]" : "yarn";

        properties = new Properties();
        properties.setProperty("driver", "impala.jdbc.driverName"); // 这个驱动找不到，不清楚是哪个依赖
    }

    private void create() {
        sparkSession = SparkSession
                .builder()
                .master(isLocal)
                .appName(new Object() {}.getClass().getEnclosingClass().getSimpleName()) // 设置application名字
                // 设置给定的Spark运行时配置属性。
                .config("spark.shuffle.consolidateFiles", true)
                .config("spark.hive.mapred.supports.subdirectories", true)
                .config("spark.hadoop.mapreduce.input.fileinputformat.input.dir.recursive", true)
                .config("spark.kryoserializer.buffer", "2000mb")
                .config("spark.debug.maxToStringFields", "1000")
                .enableHiveSupport() // 增加支持 hive Support
                .getOrCreate(); // 获取或者新建一个 sparkSession
    }

    public abstract void before() throws Exception;

    public abstract void process() throws Exception;

    public abstract void after() throws Exception;

    private void destroy() {
        // 停止SparkContext，与stop()类似。如果在close之后只需退出Spark应用程序，就不必考虑执行close
        sparkSession.close();
    }
}
