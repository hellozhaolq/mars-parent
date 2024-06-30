package com.zhaolq.mars.service.admin.config;

import java.time.Duration;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 * 注意：这里是两种配置方式
 *
 * @Author zhaolq
 * @Date 2023/5/17 20:17
 */
@Configuration
public class CorsConfig {
    /**
     * 和CorsWebFilter有什么区别
     *
     * @see org.springframework.web.filter.CorsFilter
     * @see org.springframework.web.cors.reactive.CorsWebFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        // 当allowCredentials设置为true时，CORS 规范不允许使用"*" ，并且从 5.3 开始，该组合被拒绝，转而使用allowedOriginPatterns 。
        // corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setExposedHeaders(Collections.singletonList(""));
        corsConfiguration.setMaxAge(Duration.ofSeconds(3600));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许跨域的接口，例如：/openApi/*
                        .allowedOriginPatterns(new String[]{"*"}) // 放行哪些原始域
                        .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"}) // 接口调用方式，POST、GET等
                        .allowedHeaders("*")
                        .exposedHeaders("*")
                        .allowCredentials(true); // 是否发送Cookie
            }
        };
    }
}
