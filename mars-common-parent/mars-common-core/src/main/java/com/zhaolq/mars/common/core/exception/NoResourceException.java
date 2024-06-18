package com.zhaolq.mars.common.core.exception;

import com.zhaolq.mars.common.core.result.ICode;

/**
 * 资源文件或资源不存在异常
 *
 * @Author zhaolq
 * @Date 2022/1/30 10:00
 */
public class NoResourceException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    public NoResourceException() {
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

    public NoResourceException(ICode code) {
        super(code);
    }

    public NoResourceException(String message, ICode code) {
        super(message, code);
    }

    public NoResourceException(String message, Throwable cause, ICode code) {
        super(message, cause, code);
    }

    public NoResourceException(Throwable cause, ICode code) {
        super(cause, code);
    }

    public NoResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ICode code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
