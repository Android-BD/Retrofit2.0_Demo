package com.example.zenglb.retrofittest.http;

/**
 * HttpResponse 和Base Response 能否合并
 *
 *我想大部分的api 返回的数据都是这种结构的
 *
 * Created by anylife.zlb@gmail.com on 2016/7/11.
 */
public class HttpResponse<T> {
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
        return "httpResponse{" +
                "code=" + code +
                ", error='" + error + '\'' +
                ", result=" + result +
                '}';
    }
}
