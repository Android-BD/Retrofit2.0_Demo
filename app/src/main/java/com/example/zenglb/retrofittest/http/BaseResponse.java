package com.example.zenglb.retrofittest.http;

/**
 * 所有的Response 都包含这两项，也可以去添加一个Msg 的字段
 *
 * anylife.zlb@gmail.com
 */
public  class BaseResponse  {
    private int code;
    private String error;

    public BaseResponse(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
