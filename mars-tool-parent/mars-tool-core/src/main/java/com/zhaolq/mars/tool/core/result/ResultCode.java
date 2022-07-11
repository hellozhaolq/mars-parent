package com.zhaolq.mars.tool.core.result;

/**
 * API状态码枚举
 *
 * @author zhaolq
 * @date 2020/11/3 19:56
 */
public enum ResultCode implements IResultCode {

    /* 成功 */
    SUCCESS(1, "success", "操作成功"),
    /* 失败 */
    FAILURE(2, "failure", "操作失败"),
    /* 未知错误 */
    UNKNOWN_ERROR(999999999, "unknown error", "未知错误"),


    /* 参数错误：10000-19999 */
    PARAM_ERROR(10000, "param error", "参数错误"),
    PARAM_IS_INVALID(10001, "param is invalid", "参数无效"),
    PARAM_IS_BLANK(10002, "param is blank", "参数空白"),
    PARAM_TYPE_BIND_ERROR(10003, "param type bind error", "参数类型绑定错误"),
    PARAM_NOT_COMPLETE(10004, "param not complete", "参数缺失"),

    /* 用户错误：20000-29999 */
    USER_ERROR(20000, "user error", "用户错误"),
    USER_NOT_LOGGED_IN(20001, "user not logged in", "用户未登录"),
    USER_LOGIN_ERROR(20002, "user login error", "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "user account forbidden", "账号已被禁用"),
    USER_NOT_EXISTED(20004, "user not existed", "用户不存在"),
    USER_HAS_EXISTED(20005, "user has existed", "用户已存在"),

    ;

    /**
     * 状态码
     */
    private final int code;
    /**
     * 英文信息描述
     */
    private final String descEn;
    /**
     * 中文信息描述
     */
    private final String descCh;

    private ResultCode(int code, String descEn, String descCh) {
        this.code = code;
        this.descEn = descEn;
        this.descCh = descCh;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDescEn() {
        return this.descEn;
    }

    @Override
    public String getDescCh() {
        return this.descCh;
    }

    @Override
    public Boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }

}
