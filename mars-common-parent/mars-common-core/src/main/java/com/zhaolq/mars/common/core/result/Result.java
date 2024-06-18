package com.zhaolq.mars.common.core.result;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;

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

    private Result() {}

    private Result(ICode resultCode) {
        setCode(resultCode.getCode());
        setData(null);
        setMsg(resultCode.getMsg());
        setSuccess(resultCode.isSuccess());
        setDatetime();
    }

    private Result(ICode resultCode, Object data) {
        setCode(resultCode.getCode());
        setData(data);
        setMsg(resultCode.getMsg());
        setSuccess(resultCode.isSuccess());
        setDatetime();
    }

    private Result(ICode resultCode, String msg) {
        setCode(resultCode.getCode());
        setData(null);
        setMsg(msg);
        setSuccess(resultCode.isSuccess());
        setDatetime();
    }

    /** success */

    public static Result success() {
        return new Result(ErrorCode.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(ErrorCode.SUCCESS, data);
    }

    /** failure */

    public static Result failure() {
        return new Result(ErrorCode.FAILURE);
    }

    public static Result failure(ICode code) {
        return new Result(code);
    }

    public static Result failure(String msg) {
        return new Result(ErrorCode.FAILURE, msg);
    }

    /** boo */

    public static Result boo(boolean flag) {
        return flag ? success() : failure();
    }

    /**
     * 判断返回是否成功
     *
     * @param result
     * @return boolean 是否成功
     */
    public static boolean isSuccess(@Nullable Result result) {
        return Optional.ofNullable(result).map(Result::getSuccess).orElseGet(() -> Boolean.FALSE);
    }

    /**
     * 禁用put方法
     *
     * @param key key
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
        super.put(RESULT_KEY_DATETIME, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return this;
    }
}
