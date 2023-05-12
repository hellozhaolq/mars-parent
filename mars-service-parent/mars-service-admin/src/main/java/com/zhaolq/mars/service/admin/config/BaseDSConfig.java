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
 * MySQL DataSource Configuration
 *
 * @author zhaolq
 * @date 2023/4/25 14:59:20
 */
@Configuration
@MapperScan(basePackages = {"com.zhaolq.**.dao.base"}, sqlSessionTemplateRef = "baseSqlSessionTemplate")
@Slf4j
public class BaseDSConfig {
    private String mapperLocation = "classpath*:**/mappers/base/**/*.xml";
    private String typeAliasesPackage = "com.zhaolq.*.entity";

    @Value("${jdbc.basedb.driver-class-name}")
    private String driver;

    @Value("${jdbc.basedb.jdbc-url}")
    private String url;

    @Value("${jdbc.basedb.username}")
    private String username;

    @Value("${jdbc.basedb.password}")
    private String password;

    /**
     * @return javax.sql.DataSource
     * @ConfigurationProperties 和 @Bean 一起使用
     */
    @Bean(name = "baseDataSource")
    @ConfigurationProperties(prefix = "jdbc.basedb", ignoreInvalidFields = false)
    @Primary // 主数据源，若不添加启动时可能报错
    public DataSource setDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);

        // ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // dataSource.setDriverClass(jdbcDriver);
        // dataSource.setJdbcUrl(jdbcUrl);
        // dataSource.setUser(jdbcUserName);
        // dataSource.setPassword(jdbcPassWord);
        // // 关闭连接后不自动commit
        // dataSource.setAutoCommitOnClose(false);
        // dataSource.setTestConnectionOnCheckin(true);
        // dataSource.setTestConnectionOnCheckout(true);
        // dataSource.setAutomaticTestTable("c3p0TestTable");
        // dataSource.setIdleConnectionTestPeriod(300);
        // dataSource.setMaxIdleTime(25000);
        // dataSource.setPreferredTestQuery("SELECT 1 FROM dual");

        return DataSourceBuilder.create().build();
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
