package com.zhaolq.mars.service.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
 * 跨域
 *
 * @author zhaolq
 * @date 2023/5/17 15:52:47
 * @since 1.0.0
 */
@Component
@Order(1)
@Slf4j
public class CorsFilter implements Filter {
    /**
     * 初始化
     *
     * @param filterConfig data
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // 执行初始化操作
        log.debug(this.getClass().getCanonicalName());
    }

    /**
     * 执行过滤
     *
     * @param request data
     * @param response data
     * @param chain data
     * @throws IOException data
     * @throws ServletException data
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = null;
        HttpServletResponse res = null;
        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
        }
        // 此方法是 Java 语言instanceof运算符的动态等效项。
        if (HttpServletResponse.class.isInstance(response)) {
            res = (HttpServletResponse) response;
        }

        if (req == null || res == null) {
            return;
        }

        String originResponse = "*";
        String origin = req.getHeader(HttpHeaders.ORIGIN);
        String referer = req.getHeader(HttpHeaders.REFERER);
        String str = ObjectUtils.firstNonNull(origin, referer);
        if (!StringUtils.isBlank(str)) {
            originResponse = str.substring(0, referer.indexOf('/', 8));
        }

        // https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers
        // 解决跨域问题
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originResponse);
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,PUT,DELETE,OPTIONS");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-disposition");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");

        chain.doFilter(req, res);
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {
        // 执行销毁操作
    }
}
