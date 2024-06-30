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
 * MySQL DataSource Configuration
 * <pre>
 * 注解@Configuration和@Component的区别
 *      一句话概括，@Configuration中所有@Bean注解的方法都会被动态代理，因此调用该方法返回的是同一个实例。
 *      而调用@Component中所有@Bean注解的方法，每次一个新的实例。
 * </pre>
 * <pre>
 * 多数据源配置。为了更清晰，建议二选一。
 *  1、配置@Primary类型的bean，作为自动装配的值
 *  2、在@SpringBootApplication中排除springboot的自动配置，排除后不会创建某些默认的bean，如jdbcTemplate等。
 *      DataSourceAutoConfiguration.class
 *      DataSourceTransactionManagerAutoConfiguration.class
 *      JdbcTemplateAutoConfiguration.class
 * </pre>
 *
 * @Author zhaolq
 * @Date 2023/4/25 14:59:20
 */
@Slf4j
@Lazy(false)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Configuration
@MapperScan(basePackages = {"com.zhaolq.**.dao.base"}, sqlSessionTemplateRef = "baseSqlSessionTemplate")
public class BaseDSConfig {
    private String mapperLocation = "classpath*:**/mappers/base/**/*.xml";
    private String typeAliasesPackage = "com.zhaolq.*.entity";

    @Value("${mars.datasource.basedb.driver-class-name}")
    private String driver;

    @Value("${mars.datasource.basedb.url}")
    private String url;

    @Value("${mars.datasource.basedb.username}")
    private String username;

    @Value("${mars.datasource.basedb.password}")
    private String password;

    @Bean(name = "baseDataSourceProperties")
    @ConfigurationProperties(prefix = "mars.datasource.basedb")
    public DataSourceProperties setDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "baseDataSource")
    @ConfigurationProperties(prefix = "mars.datasource.basedb.hikari")
    public HikariDataSource setDataSource(@Qualifier("baseDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "baseSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            // 使用mybatis-plus时不能使用自带的 SqlSessionFactoryBean，要使用 MybatisSqlSessionFactoryBean
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(dataSource);
            // factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml")); // 设置MyBatis配置文件路径
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
    public PlatformTransactionManager setTransactionManager(@Qualifier("baseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * jdbcTemplate和SessionFactory的区别
     * <pre>
     *  1、jdbcTemplate是spring对jdbc的封装，SQL得自己写，一旦要写SQL，则会增加灵活和复杂性，当然也不利于跨数据库（毕竟每个数据库的SQL也不竟相同）；
     *  2、SqlSessionTemplate不用关心底层的数据库是哪个数据库，在编程方面更加对象化，比如save(Object obj)，操作的都是对象，也利用了缓存实现与数据库的读取操作，提高了性能。
     * </pre>
     */
    @Bean(name = "baseSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "baseJdbcTemplate")
    public JdbcTemplate setJdbcTemplate(@Qualifier("baseDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource, false);
    }


}
