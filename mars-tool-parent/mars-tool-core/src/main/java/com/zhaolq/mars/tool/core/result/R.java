package com.zhaolq.mars.tool.core.result;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.lang.Nullable;

import com.zhaolq.mars.tool.core.date.DateUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ApiResult
 *
 * @author zhaolq
 * @date 2020/10/16 11:30
 */
@Schema(description = "返回信息")
@Data // 等同于@Getter、@Setter、@ToString、@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
    private String datetime = DateUtils.now();

    protected R(IResultCode resultCode) {
        this(resultCode.getCode(), null, resultCode.getDescEn(), resultCode.getDescCh(), resultCode.isSuccess(), null);
    }

    protected R(IResultCode resultCode, T data) {
        this(resultCode.getCode(), data, resultCode.getDescEn(), resultCode.getDescCh(), resultCode.isSuccess(), null);
    }

    protected R(IResultCode resultCode, String msgEn, String msgCh) {
        this(resultCode.getCode(), null, msgEn, msgCh, resultCode.isSuccess(), null);
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

}
