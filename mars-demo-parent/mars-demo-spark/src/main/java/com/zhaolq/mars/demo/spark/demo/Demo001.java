package com.zhaolq.mars.demo.spark.demo;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

import com.zhaolq.mars.demo.spark.common.BaseSpark;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaolq
 * @date 2023/6/14 17:53:35
 */
@Slf4j
public class Demo001 extends BaseSpark {

    public static void main(String[] args) throws Exception {
        new Demo001().execute(args);
    }

    @Override
    public void before() throws Exception {
    }

    @Override
    public void process() throws Exception {
        Dataset<Row> userDataset = sparkSession.read().jdbc(properties.getProperty("url"), userTable, properties);
        Dataset<Row> countryDataset = sparkSession.read().format("jdbc").options(propertiesMap).option("dbtable", countryTable).load();

        // 将两张表注册为临时表，进行关联查询
        userDataset.createOrReplaceTempView("v_base_user");
        countryDataset.createOrReplaceTempView("v_base_country");
        Dataset<Row> result = sparkSession.sql("select u.name as user_name, c.name as country_name from v_base_user u, v_base_country c where u.country_code = c.code");

        // 将查询出的结果保存到mysql表之中
        result.write().mode(SaveMode.Overwrite).jdbc(properties.getProperty("url"), outputTable, properties);
        result.write().mode(SaveMode.Overwrite)
                .option("header", "true") // 具体有哪些option，请查看SparkSQL指南
                .option("encoding", "gbk")
                .csv(outputPath + System.currentTimeMillis());
        result.write().mode(SaveMode.Overwrite)
                .option("encoding", "utf-8")
                .json(outputPath + System.currentTimeMillis());
    }

    @Override
    public void after() throws Exception {
    }
}
