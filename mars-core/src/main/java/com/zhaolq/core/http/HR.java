package com.zhaolq.core.http;

/**
 *
 *
 * @author zhaolq
 * @date 2020/11/2 23:56
 */
public class HR {

    private int code = 200;
    private String msg;
    private Object data;

    public static HR error() {
        return error(HS.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static HR error(String msg) {
        return error(HS.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static HR error(int code, String msg) {
        HR r = new HR();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static HR ok(String msg) {
        HR r = new HR();
        r.setMsg(msg);
        return r;
    }

    public static HR ok(Object data) {
        HR r = new HR();
        r.setData(data);
        return r;
    }

    public static HR ok() {
        return new HR();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
