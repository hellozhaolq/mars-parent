package com.zhaolq.mars.common.core.exception;

/**
 * 运行时异常，常用于对RuntimeException的包装
 *
 * @Author zhaolq
 * @Date 2024/6/14 14:57:34
 */
public class BaseRuntimeException extends BaseException {
    private static final long serialVersionUID = 1L;

    public BaseRuntimeException() {
        super();
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
