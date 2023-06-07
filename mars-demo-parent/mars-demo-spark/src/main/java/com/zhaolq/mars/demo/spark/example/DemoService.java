package com.zhaolq.mars.demo.spark.example;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

import com.zhaolq.mars.demo.spark.common.BaseSparkApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * java组件开发模板
 *
 * @author zhaolq
 * @date 2022/12/9 17:14
 * @since 1.0.0
 */
@Slf4j
public class DemoService extends BaseSparkApplication {
    private String inputTable1;
    private String inputTable2;
    private String inputTable3;
    private String outputTable;

    public static void main(String[] args) throws Exception {
        new DemoService().execute(args);
    }

    @Override
    public void before() {
        try {
            String resourceFile = StringUtils.replace(new Object() {}.getClass().getPackage().getName(), ".", "/");
            System.getProperties().load(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceFile + "/demo.properties"));
        } catch (IOException e) {
            log.error("Properties file failed to load");
            throw new RuntimeException(e);
        }

        inputTable1 = String.join(".", argsMap.get("inputDbName1"), argsMap.get("inputTableName1"));
        inputTable2 = String.join(".", argsMap.get("inputDbName2"), argsMap.get("inputTableName2"));
        inputTable3 = String.join(".", argsMap.get("inputDbName3"), argsMap.get("inputTableName3"));
        outputTable = String.join(".", argsMap.get("outputDbName"), argsMap.get("outputTableName"));

        sparkSession.sql("drop table if exists " + outputTable);
    }

    @Override
    public void process() {
        Dataset<Row> dataset = null;
        if (isLocal.contains("local")) {
            dataset = sparkSession.read().jdbc(impalaURL, inputTable1, properties);
        } else {
            dataset = sparkSession.sql(String.format(Locale.ROOT, "SELECT * FROM %s", inputTable1));
        }

        /*************************** 示例 ***************************/
        // dataset转list
        Encoder<SpeedTestIEntity> speedTestIEntityEncoder = Encoders.bean(SpeedTestIEntity.class);
        List<SpeedTestIEntity> list = dataset
                .map((MapFunction<Row, SpeedTestIEntity>) row -> {
                    SpeedTestIEntity entity = new SpeedTestIEntity();
                    entity.setString1(row.getAs(0));
                    entity.setString2(row.getAs(2));
                    entity.setString3(row.getAs(3));
                    entity.setInteger1(row.getAs("video_start_delay"));
                    entity.setBigDecimal1(row.getAs("video_stall_ratio"));
                    return entity;
                }, speedTestIEntityEncoder).collectAsList();

        // 将list转为JavaRDD，再转为Dataset
        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());
        JavaRDD<SpeedTestIEntity> rowRDD = jsc.parallelize(list);
        Dataset<Row> outputDataset1 = sparkSession.createDataFrame(rowRDD, SpeedTestIEntity.class);

        // 将list直接转为Dataset
        Dataset<Row> outputDataset2 = sparkSession.createDataFrame(list, SpeedTestIEntity.class);
        /*************************** 示例 ***************************/

        // 将dataset用指定的模式写入库并存为表
        dataset.write().mode(SaveMode.Overwrite).saveAsTable(outputTable);
    }

    @Override
    public void after() throws Exception {
        // 两种方式都可以，可能有些报告需要用sql方式吧
        // SparkUtils.invalidateMetadataWithSql(outputTable, impalaURL);
        // SparkUtils.invalidateMetadataWithProcess(outputTable, impalaURL);
    }
}
