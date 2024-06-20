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
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.pagehelper.PageInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * MySQL DataSource Configuration
 * <pre>
 * 注解@Configuration和@Component的区别？
 * 调用@Configuration类中的@Bean注解的方法，返回的是同一个实例，此方法会被动态代理。
 * 调用@Component类中的Bean注解的方法，返回的是一个新的实例
 * </pre>
 *
 * @Author zhaolq
 * @Date 2023/4/25 14:59:20
 */
@Scope("singleton")
@Lazy(true)
@Configuration
@MapperScan(basePackages = {"com.zhaolq.**.dao.base"}, sqlSessionTemplateRef = "baseSqlSessionTemplate")
@Slf4j
public class BaseDSConfig {
    private String mapperLocation = "classpath*:**/mappers/base/**/*.xml";
    private String typeAliasesPackage = "com.zhaolq.*.entity";

    @Value("${jdbc.basedb.driver-class-name}")
    private String driver;

    @Value("${jdbc.basedb.url}")
    private String url;

    @Value("${jdbc.basedb.username}")
    private String username;

    @Value("${jdbc.basedb.password}")
    private String password;

    /**
     * 数据源属性
     * 注解连用：@ConfigurationProperties 和 @Bean
     *
     * @return org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
     */
    @Bean(name = "baseDataSourceProperties")
    @ConfigurationProperties(prefix = "jdbc.basedb")
    @Primary
    public DataSourceProperties setDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 设置数据源
     *
     * @return javax.sql.DataSource
     */
    @Bean(name = "baseDataSource")
    @Primary // 主数据源，若不添加启动时可能报错
    public DataSource setDataSource(@Qualifier("baseDataSourceProperties") DataSourceProperties dataSourceProperties) {
        // ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create()
                .driverClassName(dataSourceProperties.getDriverClassName())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword());
        if (dataSourceProperties.getType() != null) {
            dataSourceBuilder.type(dataSourceProperties.getType());
        }
        return dataSourceBuilder.build();
    }

    @Bean(name = "baseSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            // 使用mybatis-plus时不能使用自带的 SqlSessionFactoryBean，要使用 MybatisSqlSessionFactoryBean
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(dataSource);
            // factoryBean.setConfigLocation(new ClassPathResource("mybatisConfigFilePath"));
            factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
            factoryBean.setTypeAliasesPackage(typeAliasesPackage);

            Properties baseProperties = new Properties();
            baseProperties.setProperty("helperDialect", "mysql");
            baseProperties.setProperty("offsetAsPageNum", "true");
            baseProperties.setProperty("rowBoundsWithCount", "true");
            baseProperties.setProperty("reasonable", "true");
            baseProperties.setProperty("params", "pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero");
            baseProperties.setProperty("supportMethodsArguments", "true");
            // 分页插件
            Interceptor interceptor = new PageInterceptor();
            interceptor.setProperties(baseProperties);
            factoryBean.setPlugins(new Interceptor[]{interceptor});
            // 支持驼峰
            factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

            sqlSessionFactory = factoryBean.getObject();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return sqlSessionFactory;
    }

    @Bean(name = "baseTransactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(@Qualifier("baseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "baseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
