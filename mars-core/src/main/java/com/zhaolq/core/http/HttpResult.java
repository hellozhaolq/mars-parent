package com.zhaolq.core.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HttpResult
 *
 * @author zhaolq
 * @date 2020/10/16 11:30
 */
@ApiModel(description = "返回信息")
@Data // 等同于@Getter、@Setter、@ToString、@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult<T> {

    @ApiModelProperty(value = "状态码", required = true)
    private int code;
    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;
    @ApiModelProperty(value = "承载数据")
    private T data;
    @ApiModelProperty(value = "返回消息", required = true)
    private String msgEn;
    @ApiModelProperty(value = "返回消息", required = true)
    private String msgCh;

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


    private HttpResult(HttpStatus httpStatus) {
        this(httpStatus, null, httpStatus.getDescEn(), httpStatus.getDescCh());
    }

    private HttpResult(HttpStatus httpStatus, String msgEn, String msgCh) {
        this(httpStatus, null, msgEn, msgCh);
    }

    private HttpResult(HttpStatus httpStatus, T data) {
        this(httpStatus, data, httpStatus.getDescEn(), httpStatus.getDescCh());
    }

    private HttpResult(HttpStatus httpStatus, T data, String msgEn, String msgCh) {
        this(httpStatus.getCode(), data, msgEn, msgCh);
    }

    private HttpResult(int code, T data, String msgEn, String msgCh) {
        this.code = code;
        this.data = data;
        this.msgEn = msgEn;
        this.msgCh = msgCh;
        this.success = HttpStatus.OK.getCode() == code;
    }

    /**
     * 返回R
     *
     * @param data
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> data(T data) {
        return data(data, HttpStatus.OK.getDescEn(), HttpStatus.OK.getDescCh());
    }

    /**
     * 返回R
     *
     * @param data
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> data(T data, String msgEn, String msgCh) {
        return data(HttpStatus.OK.getCode(), data, msgEn, msgCh);
    }

    /**
     * 返回R
     *
     * @param code
     * @param data
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> data(int code, T data, String msgEn, String msgCh) {
        return new HttpResult<>(code, data, data == null ? HttpStatus.NO_CONTENT : msg);
    }

    /**
     * 返回R
     *
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> success(String msgEn, String msgCh) {
        return new HttpResult<>(HttpStatus.SUCCESS, msg);
    }

    /**
     * 返回R
     *
     * @param httpStatus
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> success(HttpStatus httpStatus) {
        return new HttpResult<>(httpStatus);
    }

    /**
     * 返回R
     *
     * @param httpStatus
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> success(HttpStatus httpStatus, String msgEn, String msgCh) {
        return new HttpResult<>(httpStatus, msg);
    }

    public static <T> HttpResult<T> success(HttpStatus httpStatus, T data) {
        return new HttpResult<>(httpStatus, data);
    }

    /**
     * 返回R
     *
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> fail(String msgEn, String msgCh) {
        return new HttpResult<>(HttpStatus.FAILURE, msg);
    }

    /**
     * 返回R
     *
     * @param code
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> fail(int code, String msgEn, String msgCh) {
        return new HttpResult<>(code, null, msgEn, msgCh);
    }

    /**
     * 返回R
     *
     * @param httpStatus
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> fail(HttpStatus httpStatus) {
        return new HttpResult<>(httpStatus);
    }

    /**
     * 返回R
     *
     * @param httpStatus
     * @param msg
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> fail(HttpStatus httpStatus, String msgEn, String msgCh) {
        return new HttpResult<>(httpStatus, msgEn, msgCh);
    }

    /**
     * 返回R
     *
     * @param flag
     * @return com.zhaolq.core.result.R<T>
     */
    public static <T> HttpResult<T> status(boolean flag) {
        return flag ? success(HttpStatus.OK.getDescEn(), HttpStatus.OK.getDescCh()) : fail(HttpStatus.INTERNAL_SERVER_ERROR.getDescEn(), HttpStatus.INTERNAL_SERVER_ERROR.getDescCh());
    }

}
