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
 * @author zhaolq
 * @date 2023/5/17 20:17
 * @since 1.0.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * 和CorsWebFilter有什么区别
     *
     * @return org.springframework.web.cors.reactive.CorsWebFilter
     * @see org.springframework.web.filter.CorsFilter
     * @see org.springframework.web.cors.reactive.CorsWebFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 当allowCredentials设置为true时，CORS 规范不允许使用"*" ，并且从 5.3 开始，该组合被拒绝，转而使用allowedOriginPatterns 。
        // corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setExposedHeaders(Collections.singletonList(""));
        corsConfiguration.setMaxAge(Duration.ofSeconds(3600));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOriginPatterns(new String[]{"*"})
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
