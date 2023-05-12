package com.zhaolq.mars.tool.core.result;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ApiResult
 *
 * @author zhaolq
 * @date 2020/10/16 11:30
 */
@Schema(description = "返回信息")
public final class R<T> implements Serializable {
    @Schema(description = "状态码", example = "200")
    private int code;
    @Schema(description = "承载数据")
    private T data;
    @Schema(description = "返回消息")
    private String msgEn;
    @Schema(description = "返回消息")
    private String msgCh;
    @Schema(description = "是否成功", example = "true")
    private Boolean success;
    @Schema(description = "返回时间")
    private String datetime;

    private R(int code, T data, String msgEn, String msgCn, Boolean success) {
        this.code = code;
        this.data = data;
        this.msgEn = msgEn;
        this.msgCh = msgCn;
        this.success = success;
        this.datetime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    private R(IResultCode resultCode) {
        this(resultCode.getCode(), null, resultCode.getDescEn(), resultCode.getDescCh(), resultCode.isSuccess());
    }

    private R(IResultCode resultCode, T data) {
        this(resultCode.getCode(), data, resultCode.getDescEn(), resultCode.getDescCh(), resultCode.isSuccess());
    }

    private R(IResultCode resultCode, String msgEn, String msgCh) {
        this(resultCode.getCode(), null, msgEn, msgCh, resultCode.isSuccess());
    }

    /** success */

    public static <T> R<T> success() {
        return new R<>(ResultCode.SUCCESS);
    }

    public static <T> R<T> success(T data) {
        return new R<>(ResultCode.SUCCESS, data);
    }

    /** failure */

    public static <T> R<T> failure() {
        return new R<>(ResultCode.FAILURE);
    }

    public static <T> R<T> failure(IResultCode resultCode) {
        return new R<>(resultCode);
    }

    public static <T> R<T> failure(String msgEn, String msgCh) {
        return new R<>(ResultCode.FAILURE, msgEn, msgCh);
    }

    public static <T> R<T> failureEn(String msgEn) {
        return new R<>(ResultCode.FAILURE, msgEn, null);
    }

    public static <T> R<T> failureCh(String msgCh) {
        return new R<>(ResultCode.FAILURE, null, msgCh);
    }

    /** boo */

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

    public String getMsgEn() {
        return msgEn;
    }

    private void setMsgEn(String msgEn) {
        this.msgEn = msgEn;
    }

    public String getMsgCh() {
        return msgCh;
    }

    private void setMsgCh(String msgCh) {
        this.msgCh = msgCh;
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
