package com.zhaolq.mars.common.core.exception;

/**
 * 资源文件或资源不存在异常
 *
 * @author zhaolq
 * @date 2022/1/30 10:00
 */
public class NoResourceException extends IORuntimeException {
    private static final long serialVersionUID = 1L;

    public NoResourceException() {
        super();
    }

    public NoResourceException(String message) {
        super(message);
    }

    public NoResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoResourceException(Throwable cause) {
        super(cause);
    }

    public NoResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
