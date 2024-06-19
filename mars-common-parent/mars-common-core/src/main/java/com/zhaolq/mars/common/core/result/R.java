package com.zhaolq.mars.common.core.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * ApiResult
 *
 * @Author zhaolq
 * @Date 2020/10/16 11:30
 */
@Schema(description = "返回信息")
@Data
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
        this.datetime = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
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
     * 返回Http状态码
     *
     * @param httpStatus httpStatus
     * @param data       通常为详细的异常信息Exception.getMessage()，若不想暴露请设置null
     * @return R<T>
     */
    public static <T> R<T> httpStatus(HttpStatus httpStatus, T data) {
        return new R<>(httpStatus.value(), data, httpStatus.getReasonPhrase(), httpStatus.is2xxSuccessful());
    }

    /**
     * 判断返回是否成功
     *
     * @param r
     * @return boolean 是否成功
     */
    public static boolean isSuccess(@Nullable R<?> r) {
        return Optional.ofNullable(r).map(rr -> rr.getSuccess() || HttpStatus.valueOf(rr.getCode()).is2xxSuccessful()).orElseGet(() -> Boolean.FALSE);
    }
}
