package com.zhaolq.mars.common.core.result;

/**
 * 状态码枚举抽象
 *
 * @Author zhaolq
 * @Date 2020/11/3 20:54
 */
public interface IError {

    /**
     * 获取状态码
     *
     * @return int
     */
    int getCode();

    /**
     * 获取信息
     *
     * @return java.lang.String
     */
    String getMsg();

    /**
     * 是否请求成功
     *
     * @return boolean
     */
    Boolean isSuccess();
}
