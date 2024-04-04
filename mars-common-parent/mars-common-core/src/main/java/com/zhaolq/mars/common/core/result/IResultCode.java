package com.zhaolq.mars.common.core.result;

/**
 * 状态码枚举抽象
 *
 * @Author zhaolq
 * @Date 2020/11/3 20:54
 */
public interface IResultCode {

    /**
     * 获取状态码
     * @return int
     */
    int getCode();

    /**
     * 获取描述信息
     *
     * @return java.lang.String
     */
    String getDescEn();

    /**
     * 获取描述信息
     *
     * @return java.lang.String
     */
    String getDescCh();

    /**
     * 是否请求成功
     * @return boolean
     */
    Boolean isSuccess();
}
