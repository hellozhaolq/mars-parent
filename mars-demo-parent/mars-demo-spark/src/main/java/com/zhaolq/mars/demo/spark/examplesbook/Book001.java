package com.zhaolq.mars.demo.spark.examplesbook;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;

import com.zhaolq.mars.common.core.util.ClassLoaderUtil;
import com.zhaolq.mars.demo.spark.common.BaseSpark;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaolq
 * @date 2023/8/7 11:05:49
 */
@Slf4j
public class Book001 extends BaseSpark {
    public String userTable;
    public String countryTable;
    public String outputTable;
    public String outputPath;

    public static void main(String[] args) throws Exception {
        new Book001().execute(args);
    }

    @Override
    public void before() throws Exception {
        System.getProperties().load(ClassLoaderUtil.getContextClassLoader().getResourceAsStream("application.properties"));

        userTable = "t_base_user";
        countryTable = "t_base_country";
        outputTable = "t_result";
        outputPath = "D:\\temp\\";

        sparkSession.sql("drop table if exists " + outputTable);
    }

    @Override
    public void process() throws Exception {
        // 创建一列包含1000行，值为0~999，列名为number的数据集
        Dataset<Row> dataset1 = sparkSession.range(1000).toDF("number");

        // DataFrame的读取：CSV半结构化数据格式
        Dataset<Row> flightDataset = sparkSession.read()
                .option("inferSchema", "true")
                .option("header", "true")
                .csv(ClassLoaderUtil.getContextClassLoader().getResource("").getPath() + "data/flight-data/csv/2015-summary.csv");
        // DataFrame的排序：sort操作不会修改DataFrame，因为它是一个转换操作。可以通过调用explain函数观察Spark正在创建一个执行计划
        Dataset<Row> dataset = flightDataset.sort(functions.col("count").desc());
        // DataFrame的收集：返回数据集的前n行
        dataset.take(3);
        // 打印执行计划。不要过分担心如何理解关于执行计划的所有内容，在使用Spark时，执行计划可以作为调试和帮助理解的有效工具。
        dataset.sort("count").explain();

        // 设置给定的Spark运行时配置属性。设置shuffle分区为5
        sparkSession.conf().set("spark.sql.shuffle.partitions", "5");
        dataset.sort("count").take(2);
        // 可以通过配置参数指定物理执行的特性，你会看到不同设置下的时间不同，可以通过SparkUI查看作业的物理和逻辑执行情况。


    }

    @Override
    public void after() throws Exception {

    }
}
