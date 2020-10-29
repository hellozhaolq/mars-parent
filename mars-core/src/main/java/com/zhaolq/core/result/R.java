package com.zhaolq.core.result;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/16 11:30
 */
public class R<T> {

    @ApiModelProperty(value = "状态码", required = true)
    private int code;
    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;
    @ApiModelProperty(value = "承载数据")
    private T data;
    @ApiModelProperty(value = "返回消息", required = true)
    private String msg;


}
