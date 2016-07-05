package com.example.zenglb.retrofittest.http;

import com.example.zenglb.retrofittest.response.BaseResponse;

import retrofit2.Response;

/**
 * 对 Retrofit 的Callback {@link retrofit2.Callback} 的再一次的封装，不希望在业务层写那么多的代码。
 *
 * Communicates responses from a server or offline requests. One and only one method will be
 * invoked in response to a given request.
 *
 * Android: Callbacks are executed on the application's main (UI) thread.
 *
 * @param <T> Successful response body type.
 */
public interface HttpCallback {
	/**
	 * http reponse code [200,300)
     */
	void onSuccess(BaseResponse response);   //请求成功，数据是想要的

	/**
	 * 失败，给出失败提示语
	 * @param code
	 * @param message
     */
	void onFailure(int code,String message);

	/**
	 * Invoked when a network exception occurred talking to the server or when an unexpected
	 * exception occurred creating the request or processing the response.
	 *
	 */
	void onError(Throwable t);                //请求失败，


}
