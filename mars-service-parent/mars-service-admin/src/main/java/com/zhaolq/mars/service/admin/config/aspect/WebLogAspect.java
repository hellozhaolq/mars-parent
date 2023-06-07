package com.zhaolq.mars.service.admin.config.aspect;

import java.util.Enumeration;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;

import com.zhaolq.mars.api.admin.entity.UserEntity;
import com.zhaolq.mars.service.admin.config.StrFormat;
import com.zhaolq.mars.service.admin.service.IUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 网络日志切面
 * 记录每个接口的入参，部分返回值
 *
 * @author zhaolq
 * @date 2023/6/6 14:26:05
 * @since 1.0.0
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Resource
    private IUserService userService;

    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.zhaolq..*..controller..*.*(..))")
    public void webLog() {}

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 开始打印请求日志
        HttpServletRequest request = getHttpServletRequest();

        UserEntity userBean = null;
        if (request != null) {
            userBean = getUserBean(request);
        }
        String account = Optional.ofNullable(userBean).orElse(new UserEntity()).getAccount();
        String name = Optional.ofNullable(userBean).orElse(new UserEntity()).getName();

        StrFormat str = StrFormat.init().addHead();
        str.addTitle("ThreadName");
        str.addContent("ThreadName", Thread.currentThread().getName());
        // 用户信息
        str.addTitle("User Information");
        str.addContent("account", account);
        str.addContent("name", name);
        // 请求头
        str.addTitle("RequestHeaders");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            str.addContent(headerName, headerValue);
        }
        // 请求相关参数
        str.addTitle("Request Info");
        str.addContent("URL", request.getRequestURL().toString());
        str.addContent("URI", request.getRequestURI().toString());
        str.addContent("HTTP Method", request.getMethod());
        str.addContent("Class Method", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        str.addContent("IP", request.getRemoteAddr());
        str.addContent("Client IP", getClientIP(request));
        str.addContent("Request Args", new Gson().toJson(joinPoint.getArgs()));

        str.addTail();
        log.debug(str.format());
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() {
        // 每个请求之间空一行
        log.debug(StrFormat.init().addHead().format());
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint 切入点信息
     * @return 原方法的返回值
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 自定义了setter函数，代码中修改入参都会导致这里不是原始入参
        Object[] args = proceedingJoinPoint.getArgs();

        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.debug("接口耗时: {}ms, 响应结果: {}", endTime - startTime, new Gson().toJson(result));
        return result;
    }

    private UserEntity getUserBean(HttpServletRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("1");
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        userEntity = userService.getOne(wrapper);
        return userEntity;
    }

    // 获取HttpServletRequest
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    private String getClientIP(HttpServletRequest request) {
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip;
        for (String header : headers) {
            ip = request.getHeader(header);
            if (false == isUnknown(ip)) {
                return getMultistageReverseProxyIp(ip);
            }
        }
        ip = request.getRemoteAddr();
        return getMultistageReverseProxyIp(ip);
    }

    private String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (false == isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    private boolean isUnknown(String checkString) {
        return StringUtils.isBlank(checkString) || "unknown".equalsIgnoreCase(checkString);
    }

}
