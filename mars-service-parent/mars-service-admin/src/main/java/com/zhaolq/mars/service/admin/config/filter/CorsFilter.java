package com.zhaolq.mars.service.admin.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 跨域过滤器 通用解决方案
 *
 * @author zhaolq
 * @date 2023/6/6 8:36:04
 * @since 1.0.0
 */
@Slf4j
@Order(0)
@Component("MarsCorsFilter")
public class CorsFilter extends GenericFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 执行初始化操作
        log.debug(">>>>>>>> CorsFilter init");
        super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String allowOrigin = "*";
        String origin = req.getHeader(HttpHeaders.ORIGIN);
        String referer = req.getHeader(HttpHeaders.REFERER);
        String baseUrl = ObjectUtils.firstNonNull(origin, referer);
        if (!StringUtils.isBlank(baseUrl)) {
            allowOrigin = baseUrl.substring(0, referer.indexOf('/', 8));
        }

        // https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers
        // 解决跨域问题
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin);
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "origin,token");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,PUT,DELETE,OPTIONS");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization,Content-disposition,Content-Encoding");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        // 执行销毁操作
        log.debug(">>>>>>>> CorsFilter destroy");
        super.destroy();
    }
}
