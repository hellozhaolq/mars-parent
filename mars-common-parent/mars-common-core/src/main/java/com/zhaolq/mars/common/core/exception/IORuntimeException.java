package com.zhaolq.mars.common.core.exception;

import com.zhaolq.mars.common.core.result.ICode;

/**
 * IO运行时异常，其超类是RuntimeException，而非Exception，与IOException完全不同。
 *
 * @Author zhaolq
 * @Date 2022/1/30 10:00
 */
public class IORuntimeException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    public IORuntimeException() {
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

    public IORuntimeException(ICode code) {
        super(code);
    }

    public IORuntimeException(String message, ICode code) {
        super(message, code);
    }

    public IORuntimeException(String message, Throwable cause, ICode code) {
        super(message, cause, code);
    }

    public IORuntimeException(Throwable cause, ICode code) {
        super(cause, code);
    }

    public IORuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ICode code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
