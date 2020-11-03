package com.zhaolq.core.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class HttpResult<T> implements Serializable {

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

    private HttpResult(HttpStatus httpStatus) {
        this(httpStatus, null, httpStatus.getDescEn(), httpStatus.getDescCh());
    }

    private HttpResult(HttpStatus httpStatus, T data) {
        this(httpStatus, data, httpStatus.getDescEn(), httpStatus.getDescCh());
    }

    private HttpResult(HttpStatus httpStatus, String msgEn, String msgCh) {
        this(httpStatus, null, msgEn, msgCh);
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

    /** success */

    public static <T> HttpResult<T> success() {
        return new HttpResult<>(HttpStatus.OK);
    }

    public static <T> HttpResult<T> success(T data) {
        return new HttpResult<>(HttpStatus.OK, data);
    }

    public static <T> HttpResult<T> success(HttpStatus httpStatus) {
        return new HttpResult<>(httpStatus);
    }

    public static <T> HttpResult<T> success(HttpStatus httpStatus, T data) {
        return new HttpResult<>(httpStatus, data);
    }

    /** error */
    public static <T> HttpResult<T> error() {
        return new HttpResult<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> HttpResult<T> error(HttpStatus httpStatus) {
        return new HttpResult<>(httpStatus);
    }

    /** boo */

    public static <T> HttpResult<T> boo(boolean flag) {
        return flag ? success(HttpStatus.OK) : error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
