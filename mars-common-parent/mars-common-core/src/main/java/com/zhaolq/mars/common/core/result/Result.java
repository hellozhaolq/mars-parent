package com.zhaolq.mars.common.core.result;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * ApiResult
 *
 * @Author zhaolq
 * @Date 2023/5/11 14:20:26
 * @Since 1.0.0
 */
@Schema(description = "返回信息")
public final class Result extends LinkedHashMap<String, Object> implements Serializable {
    public static final String RESULT_KEY_CODE = "code";
    public static final String RESULT_KEY_DATA = "data";
    public static final String RESULT_KEY_MSG = "msg";
    public static final String RESULT_KEY_SUCCESS = "success";
    public static final String RESULT_KEY_DATETIME = "datetime";
    private static final long serialVersionUID = 1L;    // 序列化版本号

    private Result() {
    }

    private Result(IError iError) {
        setCode(iError.getCode());
        setData(null);
        setMsg(iError.getMsg());
        setSuccess(iError.isSuccess());
        setDatetime();
    }

    private Result(IError iError, Object data) {
        setCode(iError.getCode());
        setData(data);
        setMsg(iError.getMsg());
        setSuccess(iError.isSuccess());
        setDatetime();
    }

    private Result(IError iError, String msg) {
        setCode(iError.getCode());
        setData(null);
        setMsg(msg);
        setSuccess(iError.isSuccess());
        setDatetime();
    }

    private Result(HttpStatus httpStatus, Object data) {
        setCode(httpStatus.value());
        setData(data);
        setMsg(httpStatus.getReasonPhrase());
        setSuccess(httpStatus.is2xxSuccessful());
        setDatetime();
    }

    /**
     * success
     */

    public static Result success() {
        return new Result(ErrorEnum.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(ErrorEnum.SUCCESS, data);
    }

    /**
     * failure
     */

    public static Result failure() {
        return new Result(ErrorEnum.FAILURE);
    }

    public static Result failure(IError iError) {
        return new Result(iError);
    }

    public static Result failure(String msg) {
        return new Result(ErrorEnum.FAILURE, msg);
    }

    /**
     * boo
     */

    public static Result boo(boolean flag) {
        return flag ? success() : failure();
    }

    /**
     * 返回Http状态码
     *
     * @param httpStatus httpStatus
     * @param data       通常为详细的异常信息Exception.getMessage()，若不想暴露请设置null
     * @return R<T>
     */
    public static Result httpStatus(HttpStatus httpStatus, Object data) {
        return new Result(httpStatus, data);
    }

    /**
     * 判断返回是否成功
     *
     * @param result
     * @return boolean 是否成功
     */
    public static boolean isSuccess(@Nullable Result result) {
        return Optional.ofNullable(result).map(rr -> rr.getSuccess() || HttpStatus.valueOf(rr.getCode()).is2xxSuccessful()).orElseGet(() -> Boolean.FALSE);
    }

    /**
     * 禁用put方法
     *
     * @param key   key
     * @param value value
     * @return java.lang.Object
     */
    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    public int getCode() {
        return (int) this.get(RESULT_KEY_CODE);
    }

    private Result setCode(int code) {
        super.put(RESULT_KEY_CODE, code);
        return this;
    }

    public Object getData() {
        return this.get(RESULT_KEY_DATA);
    }

    private Result setData(Object data) {
        super.put(RESULT_KEY_DATA, data);
        return this;
    }

    public String getMsg() {
        return (String) this.get(RESULT_KEY_MSG);
    }

    private Result setMsg(String msg) {
        super.put(RESULT_KEY_MSG, msg);
        return this;
    }

    public Boolean getSuccess() {
        return (Boolean) this.get(RESULT_KEY_SUCCESS);
    }

    private Result setSuccess(Boolean success) {
        super.put(RESULT_KEY_SUCCESS, success);
        return this;
    }

    public String getDatetime() {
        return (String) this.get(RESULT_KEY_DATETIME);
    }

    private Result setDatetime() {
        super.put(RESULT_KEY_DATETIME, Instant.now().truncatedTo(ChronoUnit.SECONDS).toString());
        return this;
    }
}
