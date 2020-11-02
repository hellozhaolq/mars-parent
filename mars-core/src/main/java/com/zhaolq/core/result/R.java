package com.zhaolq.core.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/16 11:30
 */
@Getter
@Setter
@ToString
@ApiModel(description = "返回信息")
@NoArgsConstructor
public class R<T> {

    @ApiModelProperty(value = "状态码", required = true)
    private int code;
    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;
    @ApiModelProperty(value = "承载数据")
    private T data;
    @ApiModelProperty(value = "返回消息", required = true)
    private String msg;

    /**
     * 默认为空消息
     */
    public static final String DEFAULT_NULL_MESSAGE = "暂无承载数据";
    /**
     * 默认成功消息
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    public static final String DEFAULT_FAILURE_MESSAGE = "操作失败";
    /**
     * 默认未授权消息
     */
    public static final String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";


    private R(ResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private R(ResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private R(ResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private R(ResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.getCode() == code;
    }

    /**
     * 返回R
     *
     * @param data
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> data(T data) {
        return data(data, R.DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回R
     *
     * @param data
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> data(T data, String msg) {
        return data(HttpServletResponse.SC_OK, data, msg);
    }

    /**
     * 返回R
     *
     * @param code
     * @param data
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> data(int code, T data, String msg) {
        return new R<>(code, data, data == null ? R.DEFAULT_NULL_MESSAGE : msg);
    }

    /**
     * 返回R
     *
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> success(String msg) {
        return new R<>(ResultCode.SUCCESS, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> success(ResultCode resultCode) {
        return new R<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> success(ResultCode resultCode, String msg) {
        return new R<>(resultCode, msg);
    }

    public static <T> R<T> success(ResultCode resultCode, T msg) {
        return new R<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> fail(String msg) {
        return new R<>(ResultCode.FAILURE, msg);
    }

    /**
     * 返回R
     *
     * @param code
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> fail(int code, String msg) {
        return new R<>(code, null, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> fail(ResultCode resultCode) {
        return new R<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> fail(ResultCode resultCode, String msg) {
        return new R<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param flag
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> R<T> status(boolean flag) {
        return flag ? success(R.DEFAULT_SUCCESS_MESSAGE) : fail(R.DEFAULT_FAILURE_MESSAGE);
    }

}
