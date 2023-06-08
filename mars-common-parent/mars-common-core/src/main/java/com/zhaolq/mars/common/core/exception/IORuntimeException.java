package com.zhaolq.mars.common.core.exception;

/**
 * IO运行时异常，常用于对IOException的包装
 *
 * @author zhaolq
 * @date 2022/1/30 10:00
 */
public class IORuntimeException extends BaseException {
    private static final long serialVersionUID = 1L;

    public IORuntimeException() {
        super();
    }

    public IORuntimeException(String message) {
        super(message);
    }

    public IORuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IORuntimeException(Throwable cause) {
        super(cause);
    }

    public IORuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
