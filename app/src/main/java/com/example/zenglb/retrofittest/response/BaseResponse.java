package com.example.zenglb.retrofittest.response;

/**
 *
 * 所有的Response 都包含这两项
 */
public  class BaseResponse {
    private int code;
    private String error;

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
