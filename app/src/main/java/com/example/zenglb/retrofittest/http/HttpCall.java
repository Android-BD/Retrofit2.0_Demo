package com.example.zenglb.retrofittest.http;

import android.content.Context;
import android.util.Log;

import com.example.zenglb.retrofittest.activity.MainActivity;
import com.example.zenglb.retrofittest.http.param.LoginParams;
import com.example.zenglb.retrofittest.http.result.EasyResult;
import com.example.zenglb.retrofittest.http.result.LoginResult;
import com.example.zenglb.retrofittest.http.result.Modules;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Http 请求的设置 Basic OTE4MTExODExNjYwOjEyMzQ=
 * 1.How to overwrite or not overWrite headers
 * 2.https://futurestud.io/tutorials/retrofit-2-manage-request-headers-in-okhttp-interceptor
 * <p>
 * Created by Anylife.zlb@gmail.com on 2016/7/11.
 */
public class HttpCall {
	//1.测试数据区
	private static String TOKEN;

	//2.正式数据定义区域,FBI WARMING,请不要把分享数据用作其他用途
	private static ApiService apiService;
	private static String baseUrl = "http://test.4009515151.com/";  //我想你是个好人

	/**
	 * 设置Token
	 */
	public static void setToken(String token) {
		TOKEN = token;
	}

	public static ApiService getApiService(Context context) {
		if (apiService == null) {
			/**
			 *
			 * 这是一个专门设计用于当验证出现错误的时候，进行询问获取处理的拦截器：
			 * 如果你需要在遇到诸如 401 Not Authorised 的时候进行刷新 token，可以使用 Authenticator
			 */
			Authenticator mAuthenticator2 = new Authenticator() {
				@Override
				public Request authenticate(Route route, Response response) throws IOException {
					if (responseCount(response) >= 2) {
						// If both the original call and the call with refreshed token failed,
						// it will probably keep failing, so don't try again.
						return null;
					}

					refreshToken();
//                    Your.sToken = service.refreshToken();

					return response.request().newBuilder()
							.header("Authorization", TOKEN)
							.build();
				}
			};

			/**
			 * 2.
			 *那个 if 判断意思是，如果你的 token 是空的，就是还没有请求到 token，比如对于登陆请求，是没有 token 的，
			 * 只有等到登陆之后才有 token，这时候就不进行附着上 token。另外，如果你的请求中已经带有验证 header 了，
			 * 比如你手动设置了一个另外的 token，那么也不需要再附着这一个 token.
			 *
			 * 如果你需要在遇到诸如 401 Not Authorised 的时候进行刷新 token，可以使用 Authenticator，
			 * 这是一个专门设计用于当验证出现错误的时候，进行询问获取处理的拦截器
			 *
			 */
			Interceptor mRequestInterceptor = new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					Request originalRequest = chain.request();

					//Auth 到底放在哪里好呢？
					if (TOKEN == null || alreadyHasAuthorizationHeader(originalRequest) || noNeedAuth(originalRequest)) {
						return chain.proceed(originalRequest);
					}
					Request authorised = originalRequest.newBuilder()
							.header("Authorization", TOKEN)
							.build();

					return chain.proceed(authorised);
				}
			};


			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//            loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY );

			OkHttpClient okHttpClient = new OkHttpClient.Builder()
					.retryOnConnectionFailure(true)                 //出现错误进行重新的连接？重试几次？错误了有没有回调？
					.connectTimeout(15, TimeUnit.SECONDS)           //设置超时时间 15 秒
					.addNetworkInterceptor(mRequestInterceptor)     //网络拦截器。
					.addInterceptor(loggingInterceptor)             //打印Log拦截器
					.authenticator(mAuthenticator2)
					.build();

//			okHttpClient = OkHttpClientUtil.getSSLClient(okHttpClient, context, "cert.crt");

			Retrofit client = new Retrofit.Builder()
					.baseUrl(baseUrl)
					.client(okHttpClient)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
			apiService = client.create(ApiService.class);
		}
		return apiService;
	}


	/**
	 * 同步刷新Token操作
	 */
	private static void refreshToken() {
		//1.参数的封装
		LoginParams loginParams = new LoginParams();
		loginParams.setClient_id("5e96eac06151d0ce2dd9554d7ee167ce");
		loginParams.setClient_secret("aCE34n89Y277n3829S7PcMN8qANF8Fh");
		loginParams.setGrant_type("refresh_token");
		loginParams.setRefresh_token(MainActivity.refreshToken);
		Call<HttpResponse<LoginResult>> refreshTokenCall = HttpCall.getApiService(null).refreshToken(loginParams, "refreshToken");

		try {
			retrofit2.Response<HttpResponse<LoginResult>> response = refreshTokenCall.execute();
			if (response.isSuccessful()) {
				HttpResponse<LoginResult> httpResponse = response.body();
				HttpCall.setToken("Bearer " + httpResponse.getResult().getAccessToken());
				MainActivity.refreshToken = httpResponse.getResult().getRefreshToken();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * 计算某个请求的请求次数！
	 *
	 * @param response
	 * @return
	 */
	private static int responseCount(Response response) {
		int result = 1;
		while ((response = response.priorResponse()) != null) {
			result++;
		}
		return result;
	}

	/**
	 * 判断是否请求中含有了Author
	 *
	 * @param originalRequest
	 */
	private static boolean alreadyHasAuthorizationHeader(Request originalRequest) {
		if (originalRequest.headers().toString().contains("Authorization")) {
			Log.e("WW", "已经添加了auth header");
			return true;
		}
		return false;
	}

	/**
	 * 是否不需要Auth 认证，比如刷新Token 不需要
	 *
	 * @param originalRequest
	 */
	private static boolean noNeedAuth(Request originalRequest) {
		if (originalRequest.headers().toString().contains("NoNeedAuthFlag")) {
			Log.e("WW", "这个不需要添加Auth 认证");
			return true;
		}
		return false;
	}


	/**
	 *
	 */
	public interface ApiService {
		/**
		 * 检查号码是否存在
		 */
		@Headers({
				"Accept: application/vnd.github.v3.full+json",
				"TestKey: Get*&^%$#@!test"
		})
		@GET("api/lebang/staffs/mobile/{mobile}")
		Call<HttpResponse<EasyResult>> checkMobile(@Path("mobile") String mobile);

		/**
		 * 登录
		 */
		@POST("api/lebang/oauth/access_token")
		Call<HttpResponse<LoginResult>> goLogin(@Body LoginParams loginParams);

		/**
		 * 刷新Token，这个Api他的生命周期在登录中，但是不需要Auth
		 *
		 * @param loginParams    刷新token 的参数
		 * @param noNeedAuthFlag 添加标志位，只要这个header 不是空那么Header 就会有这个字段，这样就行。建议填写非空的操作动词（比如refreshToken）
		 */
		@POST("api/lebang/oauth/access_token")
		Call<HttpResponse<LoginResult>> refreshToken(@Body LoginParams loginParams, @Header("NoNeedAuthFlag") String noNeedAuthFlag);  //设置一下Header！do call


		/**
		 * 登陆后请求可用的模块
		 */
		@GET("api/lebang/staffs/me/modules")
		Call<HttpResponse<Modules>> getModules();

	}

}
