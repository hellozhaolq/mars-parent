package com.zhaolq.mars.demo.spark.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import lombok.extern.slf4j.Slf4j;

/**
 * Spark应用基类
 *
 * @author zhaolq
 * @date 2023/6/15 16:25:32
 */
@Slf4j
public abstract class BaseSpark {
    public String userTable;
    public String countryTable;
    public String outputTable;
    public String outputPath;
    public Properties properties;
    public Map<String, String> propertiesMap;

    public String master;
    public SparkSession sparkSession;
    public SparkContext sparkContext;
    public JavaSparkContext javaSparkContext;

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
        userTable = "t_base_user";
        countryTable = "t_base_country";
        outputTable = "t_result";
        outputPath = "D:\\temp\\";

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/basedb?useUnicode=true&characterEncoding=utf8&useSSL=true" +
                     "&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true" +
                     "&noAccessToProcedureBodies=true";
        String user = "base";
        String password = "123456789";

        properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("driver", driver);
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        propertiesMap = new HashMap<>();
        propertiesMap.put("url", url);
        propertiesMap.put("driver", driver);
        propertiesMap.put("user", user);
        propertiesMap.put("password", password);

        master = "local[*]";
    }

    private void create() {
        sparkSession = SparkSession
                .builder()
                .master("local[*]") // 按照Cpu最多Cores来设置线程数
                .appName(new Object() {}.getClass().getEnclosingClass().getSimpleName()) // 设置application名字
                /*
                    Spark属性主要分为两种：
                        一种是与部署相关，比如“spark.driver.memory”，“spark.executor.instances”，这种属性在运行时通过编程方式设置可能无效，SparkConf或者行为取决于您选择的
                    集群管理器和部署模式，建议通过配置文件(conf/spark-defaults.conf)或spark-submit命令行选项进行设置。
                        一种是与Spark运行时控制相关，比如“spark.task.maxFailures”。
                    查看Spark属性: http://localhost:4040/environment/，只有通过spark-defaults.conf、SparkConf或命令行明确指定的值才会出现，对于所有其他配置属性，您可以假定使用默认值。
                    更多Spark配置：https://spark.apache.org/docs/latest/configuration.html
                */
                .config("spark.kryoserializer.buffer", "50m") // Kryo序列化缓冲区的初始大小
                .config("spark.kryoserializer.buffer.max", "100m") // Kryo序列化缓冲区的最大大小，必须小于2048MiB
                .config("spark.debug.maxToStringFields", "1000") // spark2.4.0有效
                .config("spark.sql.debug.maxToStringFields", "1000")
                .config("spark.default.parallelism", "200")
                .config("spark.sql.shuffle.partitions", "200")
                .config("spark.memory.fraction", "1024m")
                .config("spark.memory.storageFraction", "1024m")
                .enableHiveSupport() // 增加支持 hive Support
                .getOrCreate(); // 获取或者新建一个 sparkSession

        sparkContext = sparkSession.sparkContext();
        javaSparkContext= JavaSparkContext.fromSparkContext(sparkContext);
    }

    public abstract void before() throws Exception;

    public abstract void process() throws Exception;

    public abstract void after() throws Exception;

    private void destroy() {
        // 停止SparkContext，与stop()类似。如果在close之后只需退出Spark应用程序，就不必考虑执行close
        sparkSession.close();
    }

}
