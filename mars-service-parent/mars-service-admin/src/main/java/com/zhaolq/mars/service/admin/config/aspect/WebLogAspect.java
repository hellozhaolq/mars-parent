package com.zhaolq.mars.service.admin.config.aspect;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

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

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.google.gson.Gson;

import com.zhaolq.mars.api.admin.entity.UserEntity;
import com.zhaolq.mars.common.core.console.ConsoleKeyValue;
import com.zhaolq.mars.common.core.util.ServletUtil;

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

        ConsoleKeyValue content = ConsoleKeyValue.create().setDBCMode(false);
        // 线程信息
        content.addTitle("ThreadName");
        content.addKeyValue("ThreadName", Thread.currentThread().getName());
        // 用户信息
        content.addTitle("User Information");
        content.addKeyValue("account", getUserBean(request).getAccount());
        content.addKeyValue("name", getUserBean(request).getName());
        // 请求头
        content.addTitle("RequestHeaders");
        content.addKeyValues(new TreeMap<>(ServletUtil.getHeaderMap(request)));
        // 请求相关参数
        content.addTitle("Request Info");
        content.addKeyValue("URL", request.getRequestURL().toString());
        content.addKeyValue("URI", request.getRequestURI().toString());
        content.addKeyValue("HTTP Method", request.getMethod());
        content.addKeyValue("Class Method", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        content.addKeyValue("IP", request.getRemoteAddr());
        content.addKeyValue("Client IP", ServletUtil.getClientIP(request));
        content.addKeyValue("Request Args", new Gson().toJson(joinPoint.getArgs()));

        log.debug("\n" + content);
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() {
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
