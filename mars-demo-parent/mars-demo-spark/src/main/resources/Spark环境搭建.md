# 官网

 https://spark.apache.org/ 

 https://spark.apache.org/docs/latest/index.html 

## 高级工具

 https://spark.apache.org/sql/ 

 https://spark.apache.org/streaming/ 

 https://spark.apache.org/mllib/ 

 https://spark.apache.org/graphx/ 

## 部署

 https://spark.apache.org/docs/latest/cluster-overview.html

 https://spark.apache.org/docs/latest/spark-standalone.html     **Spark 单机模式** 

## Spark配置

 https://spark.apache.org/docs/latest/configuration.html 

## 监控

 https://spark.apache.org/docs/latest/monitoring.html 

## Java API

 https://spark.apache.org/docs/latest/api/java/index.html  

 [SparkSession](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/SparkSession.html) 

 [SparkSession.Builder](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/SparkSession.Builder.html) 

 [RuntimeConfig](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/RuntimeConfig.html) 

 [Dataset](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Dataset.html) 

 [functions](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/functions.html) 

 [Column](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Column.html) 

# 简介

为了在有限的资源上学习大数据处理与分析技术，借鉴Linux以及部分网上的教程，在Windows10平台搭建Spark环境。本文将简单记录搭建流程以及其中遇到的坑。

Spark的部署模式：

- Standalone Mode（单机模式，使用Spark自带的简单集群管理器）
- Running Spark on YARN（使用YARN作为集群管理器）
- Running Spark on Kubernetes（使用Kubernetes作为集群管理器）

# 安装Java

安装路径不要有空格

# 安装Spark

下载： https://spark.apache.org/downloads.html 

解压到某个不含空格的路径下，如 `D:\Program\spark`

添加环境变量 `SPARK_HOME`，值为安装路径 `D:\Program\spark`

添加到 `Path` 路径：`%SPARK_HOME%\bin;%SPARK_HOME%\sbin`

略 -- 进入Spark的配置目录 `conf`，复制一个 `log4j.properties.template` 文件并命名为`log4j.properties`

略 -- 同样在Spark的配置目录 `conf`，复制一个 `spark-env.sh.template` 文件并命名为 `spark-env.sh`，打开并增加一行代码 `SPARK_LOCAL_IP = 127.0.0.1`

# 安装Hadoop

下载： https://hadoop.apache.org/releases.html 

解压到某个不含空格的路径下，如 `D:\Program\hadoop` 

添加环境变量 `HADOOP_HOME`，值为安装路径 `D:\Program\hadoop` 

添加到 `Path` 路径：`%HADOOP_HOME%\bin;%HADOOP_HOME%\sbin` 

进入Hadoop的配置目录 `etc\hadoop`，打开文件 `hadoop-env.cmd`，修改Java安装路径 `set JAVA_HOME=D:\Program\Java\64\jdk1.8.0_191` 

略 -- 下载对应版本的 [winutils](https://github.com/steveloughran/winutils)，把下载到的 `bin` 文件夹覆盖到Hadoop安装目录的`bin`文件夹，确保其中含有 `winutils.exe` 文件

​			Github：https://github.com/cdarlint/winutils

略 -- 新建 `D:\Program\hive` 文件夹，命令行导航到 Hadoop 的 `bin` 目录，执行授权操作 `winutils.exe chmod -R 777 D:\Program\hive` 

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

# 验证Spark安装成功

打开命令行，运行 `spark-shell` 

此时进入 `localhost:4040` 可以看到Spark的Web界面

























