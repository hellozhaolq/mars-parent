package com.zhaolq.mars.service.admin.config.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zhaolq.mars.common.core.exception.BaseRuntimeException;
import com.zhaolq.mars.common.core.result.R;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常拦截
 * 与 @ControllerAdvice 不同的是，@RestControllerAdvice 默认情况下会将返回值转换为 JSON 格式。
 *
 * @ResponseStatus 当指定异常被抛出时，将返回指定的HTTP状态码给客户端。
 * @Author zhaolq
 * @Date 2024/6/17 16:46:05
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public R<?> exceptionHandler(Exception e) {
        log.error("请求地址'{}',请求体缺失'{}'", httpServletRequest.getRequestURI(), e.getMessage());
        return R.failure(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseRuntimeException.class)
    public R<?> baseRuntimeExceptionHandler(BaseRuntimeException e) {
        e.printStackTrace();
        return R.failure(e.getMessage());
    }


}
