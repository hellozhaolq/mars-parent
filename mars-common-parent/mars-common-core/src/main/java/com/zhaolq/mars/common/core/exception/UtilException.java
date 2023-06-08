package com.zhaolq.mars.common.core.exception;

/**
 * 工具类异常
 *
 * @author zhaolq
 * @date 2022/1/30 10:00
 */
public class UtilException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UtilException() {
        super();
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

    public UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
