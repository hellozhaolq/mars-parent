package com.zhaolq.mars.service.admin.config.filter;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.zhaolq.mars.service.admin.config.wrapper.RequestWrapper;
import com.zhaolq.mars.service.admin.config.wrapper.ResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Http Header 过滤器
 *
 * @Author zhaolq
 * @Date 2023/5/30 18:10:55
 * @Since 1.0.0
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class HttpFilter extends GenericFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 执行初始化操作
        log.debug(">>>>>>>> HttpHeaderFilter init");
        super.init(filterConfig);
    }

    /**
     * 执行过滤
     *
     * @param request  data
     * @param response data
     * @param chain    data
     * @throws IOException      data
     * @throws ServletException data
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug(">>>>>>>> HttpHeaderFilter doFilter");

        // Class.isInstance 是Java语言 instanceof 运算符的动态等效项。
        if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        HttpServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
        HttpServletResponse responseWrapper = new ResponseWrapper((HttpServletResponse) response);

        requestWrapper.getSession();

        // 当前会话完成后是否仍然保持打开状态。如果发送的值是 keep-alive，则连接是持久的，不会关闭，允许对同一服务器进行后续请求。
        responseWrapper.setHeader(HttpHeaders.CONNECTION, "close");

        chain.doFilter(requestWrapper, responseWrapper);
    }

    @Override
    public void destroy() {
        // 执行销毁操作
        log.debug(">>>>>>>> HttpHeaderFilter destroy");
        super.destroy();
    }
}
