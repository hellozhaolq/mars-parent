package com.zhaolq.mars.common.core.result;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

/**
 * ApiResult
 *
 * @Author zhaolq
 * @Date 2020/10/16 11:30
 */
@Schema(description = "返回信息")
public final class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;    // 序列化版本号

    @Schema(description = "状态码", example = "200")
    private int code;
    @Schema(description = "承载数据")
    private T data;
    @Schema(description = "返回消息")
    private String msg;
    @Schema(description = "是否成功", example = "true")
    private Boolean success;
    @Schema(description = "返回时间")
    private String datetime;

    private R(int code, T data, String msg, Boolean success) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = success;
        this.datetime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    private R(IError iError) {
        this(iError.getCode(), null, iError.getMsg(), iError.isSuccess());
    }

    private R(IError iError, T data) {
        this(iError.getCode(), data, iError.getMsg(), iError.isSuccess());
    }

    private R(IError iError, String msg) {
        this(iError.getCode(), null, msg, iError.isSuccess());
    }

    /**
     * success
     */

    public static <T> R<T> success() {
        return new R<>(ErrorEnum.SUCCESS);
    }

    public static <T> R<T> success(T data) {
        return new R<>(ErrorEnum.SUCCESS, data);
    }

    /**
     * failure
     */

    public static <T> R<T> failure() {
        return new R<>(ErrorEnum.FAILURE);
    }

    public static <T> R<T> failure(IError iError) {
        return new R<>(iError);
    }

    public static <T> R<T> failure(String msg) {
        return new R<>(ErrorEnum.FAILURE, msg);
    }

    /**
     * boo
     */

    public static <T> R<T> boo(boolean flag) {
        return flag ? success() : failure();
    }

    /**
     * 判断返回是否成功
     *
     * @param r
     * @return boolean 是否成功
     */
    public static boolean isSuccess(@Nullable R<?> r) {
        return Optional.ofNullable(r).map(R::getSuccess).orElseGet(() -> Boolean.FALSE);
    }

    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    private void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDatetime() {
        return datetime;
    }

    private void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}
