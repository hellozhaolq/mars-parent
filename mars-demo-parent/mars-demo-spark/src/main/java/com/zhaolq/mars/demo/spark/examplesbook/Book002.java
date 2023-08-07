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
public class Book002 extends BaseSpark {
    public String userTable;
    public String countryTable;
    public String outputTable;
    public String outputPath;

    public static void main(String[] args) throws Exception {
        new Book002().execute(args);
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

        // 将DataFrame注册为数据表或视图，后面可以用纯SQL对它进行查询。编写SQL查询DataFrame代码都会被“编译”成相同的底层执行计划，无性能差异。
        flightDataset.createOrReplaceTempView("flight_data_20220830_summary");
        // spark使我们的SparkSession变量，它可方便地返回新的DataFrame。
        // SQL语法
        Dataset<Row> sqlWay = sparkSession.sql("select dest_country_name, count(1) from flight_data_20220830_summary" +
                                        "group by dest_country_name");
        // DataFrame语法
        Dataset<Row> dataFrameWay = flightDataset.groupBy("dest_country_name").count();
        // 观察输出的执行计划是完全相同的
        sqlWay.explain();
        dataFrameWay.explain();


        // 下面是更复杂的操作，在数据中找到前五个目标国家
        // SQL语法
        Dataset<Row> maxSql = sparkSession.sql("select dest_country_name, sum(quantity) as destination_total" +
                                        "from flight_data_20220830_summary" +
                                        "group by dest_country_name" +
                                        "order by sum(quantity) desc" +
                                        "limit 5");
        // 以表格形式显示(打印)数据集的前20行。超过20个字符的字符串将被截断，所有单元格将向右对齐。
        maxSql.show();
        // DataFrame语法。调用groupBy返回一个RelationalGroupedDataset对象，它是一个DataFrame对象，具有指定的分组，但需要用户指定聚合操作，然后草能进一步查询、
        flightDataset.groupBy("dest_country_name")
                .sum("quantity")
                .withColumnRenamed("sum(quantity)", "destination_total")
                .sort(functions.desc("destination_total"))
                .limit(5)
                .show();


    }

    @Override
    public void after() throws Exception {

    }
}
