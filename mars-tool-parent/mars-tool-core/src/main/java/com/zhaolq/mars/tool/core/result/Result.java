package com.zhaolq.mars.tool.core.result;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ApiResult
 *
 * @author zhaolq
 * @date 2023/5/11 14:20:26
 * @since 1.0.0
 */
@Schema(description = "返回信息")
public final class Result extends HashMap<String, Object> {
    public static final String RESULT_KEY_CODE = "code";
    public static final String RESULT_KEY_DATA = "data";
    public static final String RESULT_KEY_MSG_EN = "msgEn";
    public static final String RESULT_KEY_MSG_CN = "msgCn";
    public static final String RESULT_KEY_SUCCESS = "success";
    public static final String RESULT_KEY_DATETIME = "datetime";

    private Result() {}

    private Result(IResultCode resultCode) {
        setCode(resultCode.getCode());
        setData(null);
        setMsgEn(resultCode.getDescEn());
        setMsgCn(resultCode.getDescCh());
        setSuccess(resultCode.isSuccess());
        setDatetime();
    }

    private Result(IResultCode resultCode, Object data) {
        setCode(resultCode.getCode());
        setData(data);
        setMsgEn(resultCode.getDescEn());
        setMsgCn(resultCode.getDescCh());
        setSuccess(resultCode.isSuccess());
        setDatetime();
    }

    private Result(IResultCode resultCode, String msgEn, String msgCn) {
        setCode(resultCode.getCode());
        setData(null);
        setMsgEn(msgEn);
        setMsgCn(msgCn);
        setSuccess(resultCode.isSuccess());
        setDatetime();
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

    public String getMsgEn() {
        return (String) this.get(RESULT_KEY_MSG_EN);
    }

    private Result setMsgEn(String msgEn) {
        super.put(RESULT_KEY_MSG_EN, msgEn);
        return this;
    }

    public String getMsgCn() {
        return (String) this.get(RESULT_KEY_MSG_CN);
    }

    private Result setMsgCn(String msgCn) {
        super.put(RESULT_KEY_MSG_CN, msgCn);
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

    /** success */

    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    /** failure */

    public static Result failure() {
        return new Result(ResultCode.FAILURE);
    }

    public static Result failure(IResultCode resultCode) {
        return new Result(resultCode);
    }

    public static Result failure(String msgEn, String msgCN) {
        return new Result(ResultCode.FAILURE, msgEn, msgCN);
    }

    public static Result failureEn(String msgEn) {
        return new Result(ResultCode.FAILURE, msgEn, null);
    }

    public static Result failureCn(String msgCN) {
        return new Result(ResultCode.FAILURE, null, msgCN);
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
}
