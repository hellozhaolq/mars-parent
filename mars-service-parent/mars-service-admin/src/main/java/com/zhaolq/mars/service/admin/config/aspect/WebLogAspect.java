package com.zhaolq.mars.service.admin.config.aspect;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.zhaolq.mars.common.core.console.ConsoleKeyValue;
import com.zhaolq.mars.common.core.util.ServletUtil;
import com.zhaolq.mars.service.admin.entity.UserEntity;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 网络日志切面
 * 记录每个接口的入参，部分返回值
 *
 * @Author zhaolq
 * @Date 2023/6/6 14:26:05
 * @Since 1.0.0
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.zhaolq..*..controller..*.*(..))")
    public void webLog() {
        log.debug(">>>>>>>> WebLogAspect webLog");
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        log.debug(">>>>>>>> WebLogAspect doBefore");

        // 开始记录请求日志....
        // 开始记录请求日志....
        // 开始记录请求日志....

        ConsoleKeyValue content = ConsoleKeyValue.create().setDBCMode(false);

        // 切入点信息
        content.addTitle("JoinPoint");
        content.addKeyValue("Class Method", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            arguments[i] = args[i];
        }
        content.addKeyValue("Request Args", JSON.toJSONString(arguments));

        // 线程信息
        content.addTitle("ThreadName");
        content.addKeyValue("ThreadName", Thread.currentThread().getName());

        HttpServletRequest request = getHttpServletRequest();
        // 用户信息
        content.addTitle("User Information");
        content.addKeyValue("account", getUserBean(request).getAccount());
        content.addKeyValue("name", getUserBean(request).getName());
        // 请求头，包含Cookie和session
        content.addTitle("RequestHeaders");
        content.addKeyValues(new TreeMap<>(ServletUtil.getHeaderMap(request)));
        // 浏览器Cookies
        TreeMap<String, String> treeMap = new TreeMap<>();
        Map<String, Cookie> cookieMap = ServletUtil.readCookieMap(request);
        cookieMap.forEach((k, v) -> {
            treeMap.put(k, v.getValue());
        });
        content.addTitle("Request Cookies");
        content.addKeyValues(treeMap);
        // 请求体
        content.addTitle("RequestBody");
        content.addKeyValue("Body String", ServletUtil.getBody(request));
        content.addKeyValue("Body Bytes", new String(ServletUtil.getBodyBytes(request), StandardCharsets.UTF_8));
        // 请求参数
        content.addTitle("RequestParams");
        content.addKeyValues(ServletUtil.getParamMap(request));
        // 请求相关的其他参数
        content.addTitle("Request Other Info");
        content.addKeyValue("HTTP Method", request.getMethod());
        content.addKeyValue("URL", request.getRequestURL().toString());
        content.addKeyValue("URI", request.getRequestURI().toString());
        content.addKeyValue("IP", request.getRemoteAddr());
        content.addKeyValue("Client IP", ServletUtil.getClientIP(request));

        log.debug("\n" + content);
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() {
        log.debug(">>>>>>>> WebLogAspect doAfter");
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint 切入点信息
     * @return 原方法的返回值
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.debug(">>>>>>>> WebLogAspect doAround");
        // 自定义了setter函数，代码中修改入参都会导致这里不是原始入参
        Object[] args = proceedingJoinPoint.getArgs();

        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.debug("接口耗时: {}ms", endTime - startTime);
        log.debug("响应结果: \n{}", JSON.toJSONString(result, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue));

        return result;
    }

    private UserEntity getUserBean(HttpServletRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("未知账号");
        userEntity.setName("未知姓名");
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

}