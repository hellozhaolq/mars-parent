# 简介

为了在有限的资源上学习大数据处理与分析技术，借鉴Linux以及部分网上的教程，在Windows10平台搭建Spark环境。本文将简单记录搭建流程以及其中遇到的坑。

# 官网

https://spark.apache.org/

https://spark.apache.org/docs/latest/index.html

# Spark概述

Apache Spark 是一个用于大规模数据处理的统一分析引擎。它提供了 Java、Scala、Python 和 R 中的高级 API，以及支持通用执行图的优化引擎。它还支持一组丰富的<font color="red">**更高级别的工具**</font>，包括用于 SQL 和结构化数据处理的 [Spark SQL](https://spark.apache.org/docs/latest/sql-programming-guide.html)、用于 pandas 工作负载的 Spark 上的 [Pandas API](https://spark.apache.org/docs/latest/api/python/getting_started/quickstart_ps.html)（建议直接使用 [PySpark](https://spark.apache.org/docs/latest/api/python/index.html#index-page-spark-sql-and-dataframes) ）、用于机器学习的 [MLlib](https://spark.apache.org/docs/latest/ml-guide.html)、用于图形处理的 [GraphX](https://spark.apache.org/docs/latest/graphx-programming-guide.html)、用于增量计算和流处理的 [Structured Streaming](https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html)。

高级工具：

https://spark.apache.org/sql/

https://spark.apache.org/streaming/

https://spark.apache.org/mllib/

https://spark.apache.org/graphx/

# 部署

## 集群模式概述

https://spark.apache.org/docs/latest/cluster-overview.html

Spark目前支持三种集群管理器：

- Standalone Mode（单机模式，使用Spark自带的简单集群管理器） https://spark.apache.org/docs/latest/spark-standalone.html
- Running Spark on YARN（使用YARN作为集群管理器） https://spark.apache.org/docs/latest/running-on-yarn.html
- Running Spark on Kubernetes（使用Kubernetes作为集群管理器） https://spark.apache.org/docs/latest/running-on-kubernetes.html

# 更多

## Spark配置

https://spark.apache.org/docs/latest/configuration.html

## 监控

https://spark.apache.org/docs/latest/monitoring.html

# 常用JavaAPI

https://spark.apache.org/docs/latest/sql-programming-guide.html    <font color="red">**SparkSQL指南**</font>

https://spark.apache.org/docs/latest/api/java/index.html

[SparkSession](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/SparkSession.html)

[SparkSession.Builder](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/SparkSession.Builder.html)

[RuntimeConfig](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/RuntimeConfig.html)

[Dataset](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Dataset.html)

[DataFrameWriter](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/DataFrameWriter.html)

[DataFrameReader](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/DataFrameReader.html)

[functions](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/functions.html)

[Column](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Column.html)

# RDD、Dataframe、Dataset区别

# 安装Java

安装路径不要有空格

# 安装Hadoop

下载： https://hadoop.apache.org/releases.html

解压到某个不含空格的路径下，如 `D:\Program\hadoop`

添加环境变量 `HADOOP_HOME`，值为安装路径 `D:\Program\hadoop`

添加到 `Path` 路径：`%HADOOP_HOME%\bin;%HADOOP_HOME%\sbin`

进入Hadoop的配置目录 `etc\hadoop`，打开文件 `hadoop-env.cmd`，修改Java安装路径 `set JAVA_HOME=D:\Program\Java\64\jdk1.8.0_191`

下载对应版本的 [winutils](https://github.com/steveloughran/winutils)，把下载到的 `bin` 文件夹覆盖到Hadoop安装目录的`bin`文件夹，确保其中含有 `winutils.exe` 文件

​			Github：https://github.com/cdarlint/winutils

**略 --** 新建 `D:\Program\hive` 文件夹，命令行导航到 Hadoop 的 `bin` 目录，执行授权操作 `winutils.exe chmod -R 777 D:\Program\hive`

最后在命令行输入 `hadoop version` 测试是否安装成功

```cmd
C:\Users>hadoop version
Hadoop 3.3.5
Source code repository https://github.com/apache/hadoop.git -r 706d88266abcee09ed78fbaa0ad5f74d818ab0e9
Compiled by stevel on 2023-03-15T15:56Z
Compiled with protoc 3.7.1
From source with checksum 6bbd9afcf4838a0eb12a5f189e9bd7
This command was run using /D:/Program/hadoop/share/hadoop/common/hadoop-common-3.3.5.jar
```

# 安装Spark

<font color="red">**Java项目：“local[*]”本地运行时，只需安装Hadoop；要在Spark独立群集“Spark://master:7077”上运行时，需要搭建Spark单机模式或集群模式并运行。**</font>

下载： https://spark.apache.org/downloads.html

解压到某个不含空格的路径下，如 `D:\Program\spark`

添加环境变量 `SPARK_HOME`，值为安装路径 `D:\Program\spark`

添加到 `Path` 路径：`%SPARK_HOME%\bin;%SPARK_HOME%\sbin`

进入Spark的配置目录 `conf`，复制一个 `log4j.properties.template` 文件并命名为`log4j.properties`

同样在Spark的配置目录 `conf`，复制一个 `spark-env.sh.template` 文件并命名为 `spark-env.sh`，打开并增加一行代码 `SPARK_LOCAL_IP = 127.0.0.1`

验证Spark安装成功：

打开命令行，运行 `spark-shell`

此时进入 [http://localhost:4040/](http://localhost:4040/) 可以看到Spark的Web界面



