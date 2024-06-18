package com.zhaolq.mars.common.core.exception;

import com.zhaolq.mars.common.core.result.ICode;

/**
 * 验证异常
 *
 * @Author zhaolq
 * @Date 2022/1/30 10:00
 */
public class ValidateException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    public ValidateException() {
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

    public ValidateException(ICode code) {
        super(code);
    }

    public ValidateException(String message, ICode code) {
        super(message, code);
    }

    public ValidateException(String message, Throwable cause, ICode code) {
        super(message, cause, code);
    }

    public ValidateException(Throwable cause, ICode code) {
        super(cause, code);
    }

    public ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ICode code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
