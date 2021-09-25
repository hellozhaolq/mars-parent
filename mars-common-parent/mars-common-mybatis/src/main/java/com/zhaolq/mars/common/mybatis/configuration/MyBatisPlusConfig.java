package com.zhaolq.mars.common.mybatis.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.OracleDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置类
 *
 * @author zhaolq
 * @since 2020/10/21 22:25
 */
@Configuration
// @MapperScan({"com.zhaolq.**.mapper"}) /* 这里扫描与在springboot启动类扫描有何区别 */
public class MyBatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        List<InnerInterceptor> interceptors = new ArrayList<>();

        // 自动分页
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 溢出总页数后是否进行处理(默认不处理,参见 插件#continuePage 方法)
        paginationInnerInterceptor.setOverflow(true);
        // 单页分页条数限制(默认无限制,参见 插件#handlerLimit 方法)
        paginationInnerInterceptor.setMaxLimit(100L);
        // 数据库类型(根据类型获取应使用的分页方言,参见 插件#findIDialect 方法)
        paginationInnerInterceptor.setDbType(DbType.ORACLE);
        // 方言实现类(参见 插件#findIDialect 方法)
        paginationInnerInterceptor.setDialect(new OracleDialect());

        interceptors.add(paginationInnerInterceptor);

        interceptor.setInterceptors(interceptors);

        // 乐观锁配置
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 防止全表更新与删除
        // 针对 update 和 delete 语句
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        // 动态表名


        return interceptor;
    }

    /*@Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }*/

}
