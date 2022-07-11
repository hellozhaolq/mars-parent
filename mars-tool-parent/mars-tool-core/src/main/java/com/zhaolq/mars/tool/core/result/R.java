package com.zhaolq.mars.tool.core.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * ApiResult
 *
 * @author zhaolq
 * @date 2020/10/16 11:30
 */
@ApiModel(description = "返回信息")
@Data // 等同于@Getter、@Setter、@ToString、@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class R<T> implements Serializable {

    @ApiModelProperty(value = "状态码", required = true)
    private int code;
    @ApiModelProperty(value = "承载数据")
    private T data;
    @ApiModelProperty(value = "返回消息", required = true)
    private String msgEn;
    @ApiModelProperty(value = "返回消息", required = true)
    private String msgCh;
    @ApiModelProperty(value = "是否成功", required = true)
    private Boolean success;
    @ApiModelProperty(value = "返回时间", required = true)
    private String datetime = DateUtil.now();

    protected R(IResultCode resultCode) {
        this(resultCode.getCode(), null, resultCode.getDescEn(), resultCode.getDescCh(), resultCode.isSuccess());
    }

    protected R(IResultCode resultCode, T data) {
        this(resultCode.getCode(), data, resultCode.getDescEn(), resultCode.getDescCh(), resultCode.isSuccess());
    }

    protected R(IResultCode resultCode, String msgEn, String msgCh) {
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

}
