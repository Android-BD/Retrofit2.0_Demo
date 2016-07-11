package com.example.zenglb.retrofittest.NewHttp;

/**
 * Created by anylife.zlb@gmail.com on 2016/7/11.
 */
public class NewBaseResponse<T> {
    private int code;
    private String error;
    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "NewBaseResponse{" +
                "code=" + code +
                ", error='" + error + '\'' +
                ", result=" + result +
                '}';
    }
}
