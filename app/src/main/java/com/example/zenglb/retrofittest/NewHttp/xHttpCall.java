package com.example.zenglb.retrofittest.NewHttp;

import android.content.Context;
import android.text.TextUtils;
import com.example.zenglb.retrofittest.BuildConfig;
import com.example.zenglb.retrofittest.LoginParams;
import com.example.zenglb.retrofittest.http.OkHttpClientUtil;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *Http 请求的设置
 * Created by Anylife.zlb@gmail.com on 2016/7/11.
 */
public class xHttpCall{
    //1.测试数据区
    private static String TOKEN="";    //暂时的拿过来使用。Bearer dJ1FyRk21bxbxPERuPmujjFLWwgGRO

    //2.正式数据定义区域
    private static ApiService apiService;
    private static String baseUrl = "https://test.4009515151.com/";
    public  static ApiService getApiService(Context context) {
        if (apiService == null) {
            //1.如果你需要在遇到诸如 401 Not Authorised 的时候进行刷新 token，可以使用 Authenticator，这是一个专门设计用于当验证出现错误的时候，进行询问获取处理的拦截器：
//            Authenticator mAuthenticator = new Authenticator() {
//                @Override public Request authenticate(Route route, Response response)
//                        throws IOException {
//                    Your.sToken = service.refreshToken();
//                    return response.request().newBuilder()
//                            .addHeader("Authorization", Your.sToken)
//                            .build();
//                }
//            };

            /**
             * 拦截器，能拦截成功吗？感觉但事后都是一样的东西在运行
             */
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    if (TextUtils.isEmpty(TOKEN)){  //然而这并不是动态的。
                        Request authorised = originalRequest.newBuilder()
                                .addHeader("X-Platform","Android")
                                .build();
                        return chain.proceed(authorised);
                    }else{
                        Request authorised = originalRequest.newBuilder()
                                .addHeader("X-Platform","Android")
                                .addHeader("Authorization", TOKEN)    //一般的登录之前，登出之后的api 是不需要token 的。
                                .build();
                        return chain.proceed(authorised);
                    }
                }
            };

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .retryOnConnectionFailure(true)                 //出现错误进行重新的连接？重试几次？错误了有没有回调？
                    .connectTimeout(15, TimeUnit.SECONDS)           //设置超时时间 15 秒
                    .addNetworkInterceptor(interceptor)             //网络拦截器。
                    .build();

            okHttpClient = OkHttpClientUtil.getSSLClient(okHttpClient, context, "cert.crt");

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
     *
     */
    public interface ApiService {
        /**
         * 检查号码是否存在
         */
        @GET("api/lebang/staffs/mobile/{mobile}")
        Call<HttpResponse<EasyResult>> checkMobile(@Path("mobile") String mobile);

        /**
         * 登录
         */
        @POST("api/lebang/oauth/access_token")
        Call<HttpResponse<LoginResult>> goLogin(@Body LoginParams loginParams);  //设置一下Header！do call

//        @HTTPS
//        @Headers("User-Agent: Retrofit2.0Tutorial-App")
//        @GET("/search/users")
//        Call<GitResult> getUsersByName(@Query("q") String name);
//
//        @HTTPS
//        @Headers("User-Agent: Retrofit2.0Tutorial-App")
//        @GET("/search/users")
//        void getUsersByName2(@Query("q") String name, Callback<GitResult> callback);
//
//        @HTTPS
//        @GET("/search/users")
//        void testHttps(@Query("q") String name, Callback<GitResult> callback);
    }

}
