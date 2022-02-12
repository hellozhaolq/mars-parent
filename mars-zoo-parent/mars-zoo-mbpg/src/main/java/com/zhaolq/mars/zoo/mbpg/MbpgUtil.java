package com.zhaolq.mars.zoo.mbpg;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.OracleQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.*;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/9 9:53
 */
public class MbpgUtil {

    /**
     * 表名（区分大小写，英文逗号分割）
     */
    private static String tableNames;

    private static String projectPath = ClassLoader.getSystemResource("").getPath().replace("/target/classes/", "/");
    private static Properties properties;

    private static DbType dbType;
    private static String dbDriverClassName;
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    private static String author;
    private static String parent;
    private static String moduleName;

    private static String deleteTablePrefix;
    private static String deleteFieldPrefix;

    static {
        properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("db.properties"));

            tableNames = properties.getProperty("tableNames");

            dbType = DbType.getDbType(properties.getProperty("db.type"));
            dbDriverClassName = properties.getProperty("db.driverClassName");
            dbUrl = properties.getProperty("db.url");
            dbUsername = properties.getProperty("db.username");
            dbPassword = properties.getProperty("db.password");

            author = properties.getProperty("author");
            parent = properties.getProperty("parent");
            moduleName = (moduleName = properties.getProperty("moduleName")) == null ? "" : moduleName;

            deleteTablePrefix = (deleteTablePrefix = properties.getProperty("deleteTablePrefix")) == null ? "" : deleteTablePrefix;
            deleteFieldPrefix = (deleteFieldPrefix = properties.getProperty("deleteFieldPrefix")) == null ? "" : deleteFieldPrefix;

        } catch (Exception e) {
            System.out.println("未发现数据库配置文件db.properties");
            System.exit(0);
        }
    }

    /**
     * 全局配置
     *
     * @param
     * @return com.baomidou.mybatisplus.generator.config.GlobalConfig
     * @throws
     */
    public static GlobalConfig getGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(MbpgUtil.projectPath + "/src/main/java");
        globalConfig.setFileOverride(false);
        globalConfig.setOpen(false);
        // 是否在xml中添加二级缓存配置
        globalConfig.setEnableCache(true);
        globalConfig.setAuthor(author);
        globalConfig.setKotlin(false);
        globalConfig.setSwagger2(true);
        // 开启ActiveRecord模式：https://zh.wikipedia.org/wiki/Active_Record
        globalConfig.setActiveRecord(true);
        globalConfig.setBaseResultMap(true);
        // 时间类型对应策略
        globalConfig.setDateType(DateType.TIME_PACK);
        globalConfig.setBaseColumnList(true);

        // 各层文件名称方式
        globalConfig.setEntityName("%sEntity");
        globalConfig.setControllerName("%sController");
        globalConfig.setServiceName("I%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");

        // 指定生成的主键的ID类型
        globalConfig.setIdType(IdType.ASSIGN_ID);

        return globalConfig;

    }

    /**
     * 数据源配置
     *
     * @param
     * @return com.baomidou.mybatisplus.generator.config.DataSourceConfig
     * @throws
     */
    public static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbQuery(new OracleQuery());
        dataSourceConfig.setTypeConvert(new OracleTypeConvert());
        dataSourceConfig.setKeyWordsHandler(null);
        if (dbType == DbType.ORACLE || dbType == DbType.ORACLE_12C) {
            dataSourceConfig.setDbType(dbType);
            dataSourceConfig.setSchemaName(dbUsername.toUpperCase());
        }
        dataSourceConfig.setUrl(dbUrl);
        dataSourceConfig.setDriverName(dbDriverClassName);
        dataSourceConfig.setUsername(dbUsername);
        dataSourceConfig.setPassword(dbPassword);
        return dataSourceConfig;
    }

    /**
     * 包配置
     *
     * @param
     * @return com.baomidou.mybatisplus.generator.config.PackageConfig
     * @throws
     */
    public static PackageConfig getPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(moduleName);
        packageConfig.setParent(parent);
        packageConfig.setEntity("entity");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("mapper");
        packageConfig.setXml("mapper.xml");
        return packageConfig;
    }

    /**
     * 自定义配置
     *
     * @param
     * @return com.baomidou.mybatisplus.generator.InjectionConfig
     * @throws
     */
    public static InjectionConfig getInjectionConfig() {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 文件输出配置。自定义配置会被优先输出
        List<FileOutConfig> focList = new ArrayList<>();

        /************************* 按照如下示例，设置各层文件模板和输出位置，并添加到focList *************************/
        // velocity模板引擎后缀'.vm'; freemarker模板引擎后缀'.ftl';
        String templatePath = "/templates/mapper.xml.vm";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return MbpgUtil.projectPath + "/src/main/resources/mapper/" + moduleName
                        + "/" + stringConverter(tableInfo.getName()) + "Mapper" + StringPool.DOT_XML;
            }

            public String stringConverter(String str) {
                // 下划线转驼峰命
                str = NamingStrategy.underlineToCamel(str);
                // 首字母转大写
                char[] ch = str.toCharArray();
                if (ch[0] >= 'a' && ch[0] <= 'z') {
                    ch[0] = (char) (ch[0] - 32);
                }
                return new String(ch);
            }
        });
        /*****************************************************************************************************/

        injectionConfig.setFileOutConfigList(focList);
        injectionConfig.setFileCreate(new IFileCreate() {
            /**
             * 自定义判断是否创建文件
             */
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir(filePath);

                // 若各层文件已存在，不想覆盖创建返回false
                switch (fileType) {
                    case ENTITY:
                    case CONTROLLER:
                    case SERVICE:
                    case SERVICE_IMPL:
                    case MAPPER:
                    case XML:
                        return !new File(filePath).exists();
                    case OTHER:
                    default:
                        // 允许创建文件(覆盖)
                        return true;
                }

            }
        });
        return injectionConfig;
    }

    /**
     * 模板配置
     *
     * @param
     * @return com.baomidou.mybatisplus.generator.config.TemplateConfig
     * @throws
     */
    public static TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        // 指定自定义模板路径, 位置：/resources/templates/entity.java.ftl(或者是.vm)。注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        templateConfig.setEntity(ConstVal.TEMPLATE_ENTITY_JAVA);
        templateConfig.setEntityKt(ConstVal.TEMPLATE_ENTITY_KT);
        templateConfig.setController(ConstVal.TEMPLATE_CONTROLLER);
        templateConfig.setService(ConstVal.TEMPLATE_SERVICE);
        templateConfig.setServiceImpl(ConstVal.TEMPLATE_SERVICE_IMPL);
        templateConfig.setMapper(ConstVal.TEMPLATE_MAPPER);
        templateConfig.setXml(ConstVal.TEMPLATE_XML);
        return templateConfig;
    }

    /**
     * 策略配置
     *
     * @param
     * @return com.baomidou.mybatisplus.generator.config.StrategyConfig
     * @throws
     */
    public static StrategyConfig getStrategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(false);
        strategyConfig.setSkipView(false);
        strategyConfig.setNameConvert(null);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 删除表前缀
        strategyConfig.setTablePrefix(deleteTablePrefix.split(","));
        // 删除字段前缀
        strategyConfig.setFieldPrefix(deleteFieldPrefix.split(","));

        // strategyConfig.setSuperEntityClass()
        // 写于父类中的公共字段，该字段不再出现在entity和MapperXml的BaseResultMap中
        // strategyConfig.setSuperEntityColumns("flag");
        // strategyConfig.setSuperControllerClass()
        strategyConfig.setSuperServiceClass(ConstVal.SUPER_SERVICE_CLASS);
        strategyConfig.setSuperServiceImplClass(ConstVal.SUPER_SERVICE_IMPL_CLASS);
        strategyConfig.setSuperMapperClass(ConstVal.SUPER_MAPPER_CLASS);

        strategyConfig.setInclude(tableNames.split(","));
        strategyConfig.setEntitySerialVersionUID(true);
        strategyConfig.setEntityColumnConstant(false);
        strategyConfig.setChainModel(true);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setEntityTableFieldAnnotationEnable(true);

        return strategyConfig;
    }

}
