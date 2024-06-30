package com.zhaolq.mars.service.admin.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Arche是希腊文，英文是principle，字面意义是开始、太初、起源。
 * 当一事物（存有物、或事件、或认识）以某些方式从“另一事物“出发，后者即称为开始或起源，在中国被翻译为始元。
 *
 * @Author zhaolq
 * @Date 2023/4/25 23:35
 */
@Slf4j
@Lazy(false)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Configuration
@MapperScan(basePackages = {"com.zhaolq.**.dao.arche"}, sqlSessionTemplateRef = "archeSqlSessionTemplate")
public class ArcheDSConfig {
    private String mapperLocation = "classpath*:**/mappers/arche/**/*.xml";
    private String typeAliasesPackage = "com.zhaolq.*.entity";

    @Value("${mars.datasource.archedb.driver-class-name}")
    private String driver;

    @Value("${mars.datasource.archedb.url}")
    private String url;

    @Value("${mars.datasource.archedb.username}")
    private String username;

    @Value("${mars.datasource.archedb.password}")
    private String password;

    @Bean(name = "archeDataSourceProperties")
    @ConfigurationProperties(prefix = "mars.datasource.archedb")
    public DataSourceProperties setDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "archeDataSource")
    @ConfigurationProperties(prefix = "mars.datasource.archedb.hikari")
    public HikariDataSource setDataSource(@Qualifier("archeDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "archeSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("archeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            // 使用mybatis-plus时不能使用自带的 SqlSessionFactoryBean，要使用 MybatisSqlSessionFactoryBean
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(dataSource);
            // factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml")); // 设置MyBatis配置文件路径
            factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
            factoryBean.setTypeAliasesPackage(typeAliasesPackage);

            Properties archeProperties = new Properties();
            archeProperties.setProperty("helperDialect", "mysql");
            archeProperties.setProperty("offsetAsPageNum", "true");
            archeProperties.setProperty("rowBoundsWithCount", "true");
            archeProperties.setProperty("reasonable", "true");
            archeProperties.setProperty("params", "pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero");
            archeProperties.setProperty("supportMethodsArguments", "true");
            // 分页插件
            Interceptor interceptor = new PageInterceptor();
            interceptor.setProperties(archeProperties);
            factoryBean.setPlugins(new Interceptor[]{interceptor});
            // 支持驼峰
            factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

            sqlSessionFactory = factoryBean.getObject();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return sqlSessionFactory;
    }

    @Bean(name = "archeTransactionManager")
    public PlatformTransactionManager setTransactionManager(@Qualifier("archeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "archeSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("archeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "archeJdbcTemplate")
    public JdbcTemplate setJdbcTemplate2(@Qualifier("archeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource, false);
    }
}
