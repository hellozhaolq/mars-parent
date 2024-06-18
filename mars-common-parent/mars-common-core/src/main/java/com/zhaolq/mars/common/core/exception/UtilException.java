package com.zhaolq.mars.common.core.exception;

import com.zhaolq.mars.common.core.result.ICode;

/**
 * 工具类异常
 *
 * @Author zhaolq
 * @Date 2022/1/30 10:00
 */
public class UtilException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    public UtilException() {
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

    public UtilException(ICode code) {
        super(code);
    }

    public UtilException(String message, ICode code) {
        super(message, code);
    }

    public UtilException(String message, Throwable cause, ICode code) {
        super(message, cause, code);
    }

    public UtilException(Throwable cause, ICode code) {
        super(cause, code);
    }

    public UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ICode code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
