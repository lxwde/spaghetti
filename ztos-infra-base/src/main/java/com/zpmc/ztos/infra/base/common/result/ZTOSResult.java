package com.zpmc.ztos.infra.base.common.result;


import java.io.Serializable;

/**
 * 响应信息主体
 */
public class ZTOSResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 失败
     */
    public static final int FAIL = 500;

    private int code;

    private String msg;

    private T data;

    public static <T> ZTOSResult<T> success() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> ZTOSResult<T> success(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> ZTOSResult<T> success(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> ZTOSResult<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> ZTOSResult<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> ZTOSResult<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> ZTOSResult<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> ZTOSResult<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> ZTOSResult<T> restResult(T data, int code, String msg) {
        ZTOSResult<T> apiResult = new ZTOSResult<T>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
