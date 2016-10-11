package com.example.zenglb.retrofittest.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.zenglb.retrofittest.R;
import com.example.zenglb.retrofittest.base.BaseActivity;
import com.example.zenglb.retrofittest.http.HttpCall;
import com.example.zenglb.retrofittest.http.HttpCallBack;
import com.example.zenglb.retrofittest.http.HttpResponse;
import com.example.zenglb.retrofittest.http.param.LoginParams;
import com.example.zenglb.retrofittest.http.result.EasyResult;
import com.example.zenglb.retrofittest.http.result.IdentifyResult;
import com.example.zenglb.retrofittest.http.result.LoginResult;

import java.util.List;

import retrofit2.Call;

/**
 * Http 的错误统一处理
 *
 *
 * anylife.zlb@gamil.com
 */
public class MainActivity extends BaseActivity {
	private final String TAG = MainActivity.class.getSimpleName();
	private TextView textView;
	private TextView textView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.message);
		textView2 = (TextView) findViewById(R.id.message2);


		//良好的互联网环境需要大家的共同努力，对于不正当使用带来的法律责任，由事由者承担
		//1.参数的封装
		LoginParams loginParams = new LoginParams();
		loginParams.setClient_id("5e96eac06151d0ce2dd9554d7ee167ce");
		loginParams.setClient_secret("aCE34n89Y277n3829S7PcMN8qANF8Fh");
		loginParams.setGrant_type("password");
		loginParams.setUsername("18826562075");
		loginParams.setPassword("zxcv1234");

		//2.实例化Http的请求。Call 语法看起来很繁琐，但是这也是Java的基础
		Call<HttpResponse<LoginResult>> loginCall = HttpCall.getApiService(this).goLogin(loginParams); //尝试登陆
		loginCall.enqueue(new HttpCallBack<HttpResponse<LoginResult>>(this) {
			@Override
			public void onSuccess(HttpResponse<LoginResult> loginResultHttpResponse) {
				Log.e(TAG, loginResultHttpResponse.getResult().toString());
				textView.setText(loginResultHttpResponse.getResult().toString());
				HttpCall.tempData = "Bearer " + loginResultHttpResponse.getResult().getAccessToken();
			}

			@Override
			public void onFailure(int code, String message) {
				super.onFailure(code,message);
				textView.setText(code + "  !loginCall!  " + message);
			}
		});


		//点击检查号码是否注册过了
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkNumber();
			}
		});

		//点击请求身份
		textView2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				requestIdentify();
			}
		});

	}

	/**
	 * 请求身份信息
	 *
	 */
	private void requestIdentify() {
		Call<HttpResponse<List<IdentifyResult>>> getIdentityCall = HttpCall.getApiService(this).getIdentities(); //尝试登陆
		getIdentityCall.enqueue(new HttpCallBack<HttpResponse<List<IdentifyResult>>>(this) {
			@Override
			public void onSuccess(HttpResponse<List<IdentifyResult>> getIdentityCallResponse) {
				Log.e(TAG, getIdentityCallResponse.getResult().toString());
				textView2.setText(getIdentityCallResponse.getResult().toString());
			}

//			@Override
//			public void onFailure(int code, String message) {
//				super.onFailure(code,message);
//				textView2.setText(code + "@@getIdentityCall@@" + message);
//			}
		});

	}

	/**
	 * 检查号码是否被注册了。
	 */
	private void checkNumber() {
		//2.实例化Http的请求。
		//这个时候的response 是一个没有用的response啊！
		Call<HttpResponse<EasyResult>> checkMobileCall = HttpCall.getApiService(this).checkMobile("18812345678"); //尝试登陆
		checkMobileCall.enqueue(new HttpCallBack<HttpResponse<EasyResult>>(this) {
			@Override
			public void onSuccess(HttpResponse<EasyResult> checkMobileHttpResponse) {
				Log.e(TAG, checkMobileHttpResponse.getResult().toString());
				textView.setText(checkMobileHttpResponse.getResult().toString());
			}

			@Override
			public void onFailure(int code, String message) {
				super.onFailure(code,message);
				textView.setText(code + "@@checkMobileCall@@" + message);      //
			}
		});
	}
}
