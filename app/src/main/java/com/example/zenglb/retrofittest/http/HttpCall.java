package com.example.zenglb.retrofittest.http;

import android.util.Log;

import com.example.zenglb.retrofittest.response.BaseResponse;
import com.example.zenglb.retrofittest.utils.TextUtils;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目  git@github.com:MasonLiuChn/RetrofitPlus.git  学习
 * 
 *
 * @author anylife.zlb@gmail.com
 */
@Deprecated
public  class HttpCall<T extends BaseResponse> {
	private static String TAG=HttpCall.class.getSimpleName();  //调试TAG
	private static Gson gson = new Gson();

	/**
	 * 只有这个方法对外暴露
	 *
	 * Call<T> call, Response<T> response  Call<ResponseBody>
	 */
	public  void call(final Call<BaseResponse> call, final HttpCallback httpCallback) {
		call.enqueue(new Callback<BaseResponse>() {
			@Override
			public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
				if(response.isSuccessful()){                        // code:[200,300）
                    int responseCode=response.body().getCode();     //这个code才是应用层的Code,进行进一步的数据和事件分发!
                    if(responseCode==HttpClient.HTTP_SUCCESS){
						httpCallback.onSuccess(response.body());
                    }else{
						httpCallback.onFailure(responseCode,response.body().getError());
                    }
                }else{
					try {
						String errorBodyStr=TextUtils.convertUnicode(response.errorBody().string());
						Log.e(TAG,errorBodyStr);
						BaseResponse baseResponse=gson.fromJson(errorBodyStr,BaseResponse.class );
						if(null!=baseResponse){
							httpCallback.onFailure(baseResponse.getCode(),baseResponse.getError());
							Log.e(TAG, baseResponse.getCode()+"%% %%"+baseResponse.getError());
						}
					}catch (IOException e){
						e.printStackTrace();
					}

                }
			}

			@Override
			public void onFailure(Call<BaseResponse> call, Throwable t) {
				httpCallback.onError(t);
			}

		});


	} //httpCall function end。



}
