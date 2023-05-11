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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.pagehelper.PageInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * Arche是希腊文，英文是principle，字面意义是开始、太初、起源。
 * 当一事物（存有物、或事件、或认识）以某些方式从“另一事物“出发，后者即称为开始或起源，在中国被翻译为始元。
 *
 * @author zhaolq
 * @date 2023/4/25 23:35
 * @since 1.0.0
 */
@Configuration
@MapperScan(basePackages = {"com.zhaolq.**.dao.arche", ""}, sqlSessionTemplateRef = "archeSqlSessionTemplate")
@Slf4j
public class ArcheDSConfig {
    private String mapperLocation = "classpath*:**/mappers/arche/**/*.xml";
    private String typeAliasesPackage = "com.zhaolq.*.entity";

    @Value("${jdbc.archedb.driver}")
    private String driver;

    @Value("${jdbc.archedb.url}")
    private String url;

    @Value("${jdbc.archedb.username}")
    private String username;

    @Value("${jdbc.archedb.password}")
    private String password;

    @Bean(name = "archeDataSource")
    @ConfigurationProperties(prefix = "jdbc.archedb")
    public DataSource setDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "archeSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("archeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(dataSource);
            factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
            factoryBean.setTypeAliasesPackage(typeAliasesPackage);

            Properties properties = new Properties();
            properties.setProperty("helperDialect", "mysql");
            properties.setProperty("offsetAsPageNum", "true");
            properties.setProperty("rowBoundsWithCount", "true");
            properties.setProperty("reasonable", "true");
            properties.setProperty("supportMethodsArguments", "true");
            properties.setProperty("params", "pageNum=pageNumKey;pageSize=pageSizeKey;");
            // 分页插件
            Interceptor interceptor = new PageInterceptor();
            interceptor.setProperties(properties);
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
    public DataSourceTransactionManager setTransactionManager(@Qualifier("archeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "archeSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("archeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
