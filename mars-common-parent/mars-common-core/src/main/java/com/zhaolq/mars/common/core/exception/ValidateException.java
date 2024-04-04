package com.zhaolq.mars.common.core.exception;

/**
 * 验证异常
 *
 * @Author zhaolq
 * @Date 2022/1/30 10:00
 */
public class ValidateException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ValidateException() {
        super();
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
