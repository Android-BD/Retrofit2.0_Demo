package com.demo.zenglb.retrofittest.http.param;

/**
 *
 * Created by zenglb on 2016/11/22.
 */
public class Datas<T> {
	private T data;

	public Datas(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Datas{" +
				"data=" + data +
				'}';
	}
}