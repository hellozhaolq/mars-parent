package com.zhaolq.mars.service.admin.config.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
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

    private HttpServletRequest httpServletRequest;

    public GlobalExceptionHandler(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public R<?> exceptionHandler(Exception e) {
        logError(httpServletRequest.getRequestURI(), e.getMessage());
        e.printStackTrace();
        return R.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public R<?> runtimeExceptionHandler(RuntimeException e) {
        logError(httpServletRequest.getRequestURI(), e.getMessage());
        e.printStackTrace();
        return R.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public R<?> bindExceptionHandler(BindException e) {
        logError(httpServletRequest.getRequestURI(), e.getMessage());
        e.printStackTrace();
        return R.httpStatus(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BaseRuntimeException.class)
    public R<?> baseRuntimeExceptionHandler(BaseRuntimeException e) {
        logError(httpServletRequest.getRequestURI(), e.getMessage());
        return R.failure(e.getiError());
    }

    private void logError(String requestURI, String errorMessage) {
        log.error("请求地址'{}'，异常信息'{}'", requestURI, errorMessage);
    }
}
