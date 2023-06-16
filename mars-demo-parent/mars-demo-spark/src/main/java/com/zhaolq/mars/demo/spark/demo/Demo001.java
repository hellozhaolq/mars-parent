package com.zhaolq.mars.demo.spark.demo;

import org.apache.spark.broadcast.Broadcast;
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
        Dataset<Row> userDataset = sparkSession.read().jdbc(properties.getProperty("url"), userTable, properties).cache();
        Dataset<Row> countryDataset = sparkSession.read().format("jdbc").options(propertiesMap).option("dbtable", countryTable).load().cache();

        // 将两张表注册为临时表，进行关联查询
        userDataset.createOrReplaceTempView("v_base_user");
        countryDataset.createOrReplaceTempView("v_base_country");
        Dataset<Row> result = sparkSession.sql("select u.name as user_name, c.name as country_name from v_base_user u, v_base_country c where u.country_code = c.code");
        // 传统的join操作会导致shuffle操作。因为两个RDD中，相同的key都需要通过网络拉取到一个节点上，由一个task进行join操作。
        Dataset<Row> dataset = userDataset.join(countryDataset);
        // Broadcast+map不会导致洗牌操作。先广播出去，每个Executor的内存中，都会驻留一份广播的全量数据。建议数据量比较少（几百M、或一两G）的情况下使用
        Broadcast<?> broadcastDataset = javaSparkContext.broadcast(countryDataset);
        // 将userDataset和广播的broadcastDataset进行手动关联


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
