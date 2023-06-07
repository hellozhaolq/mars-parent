package com.zhaolq.mars.common.core.exception;

import com.zhaolq.mars.common.core.result.IResultCode;
import com.zhaolq.mars.common.core.result.ResultCode;

/**
 * 验证异常
 *
 * @author zhaolq
 * @date 2022/1/30 10:00
 */
public class ValidateException extends BaseException {
    public ValidateException() {
        super();
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Object... params) {
        super(message, params);
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

    @Override
    protected IResultCode setExceptionResultCode() {
        return ResultCode.UNKNOWN_ERROR;
    }
}
