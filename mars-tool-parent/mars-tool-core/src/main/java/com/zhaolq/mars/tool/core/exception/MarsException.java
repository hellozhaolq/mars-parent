package com.zhaolq.mars.tool.core.exception;

/**
 * 自定义异常
 *
 * @author zhaolq
 * @date 2020/11/12 11:08
 */
public final class MarsException extends RuntimeException { // 应该继承 BaseException 类，待修改

    private String msg;
    private int code = 500;

    public MarsException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MarsException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MarsException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MarsException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
