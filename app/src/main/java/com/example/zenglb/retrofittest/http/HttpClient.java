package com.example.zenglb.retrofittest.http;

import android.text.TextUtils;

import com.example.zenglb.retrofittest.BuildConfig;
import com.example.zenglb.retrofittest.LoginParams;
import com.example.zenglb.retrofittest.WeatherJson;
import com.example.zenglb.retrofittest.response.BaseResponse;
import com.example.zenglb.retrofittest.response.LoginResponse;
import com.example.zenglb.retrofittest.response.OrganizationResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Http 访问的客户端，Retrofit2.0 + OKHttp3.0 + GSON
 *
 * 1.关于RetroFit2.0 & OKHttp 3.0 的配置 ：https://drakeet.me/retrofit-2-0-okhttp-3-0-config
 * 2.More:
 *       http://bxbxbai.github.io/2015/12/13/retrofit2/
 *       https://realm.io/cn/news/droidcon-jake-wharton-simple-http-retrofit-2/
 *
 *
 *
 * Created by zenglb on 2016/7/1.
 */
public class HttpClient {
    private static Retrofit mRetrofit;
    private static final String baseUrl="https://test.4009515151.com/";
    public static final int  HTTP_SUCCESS=0;  //http 请求成功 ,暂时测试放在这里
    private static String TOKEN="Bearer dJ1FyRk21bxbxPERuPmujjFLWwgGRO";    //暂时的拿过来使用。

    /**
     * 这个名字不好
     * @return
     */
    public static Retrofit retrofit() {
        if (mRetrofit == null) {

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
             * 拦截器
             */
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    if (TextUtils.isEmpty(TOKEN)){
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

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .retryOnConnectionFailure(true)                 //出现错误进行重新的连接？重试几次？错误了有没有回调？
                    .connectTimeout(15, TimeUnit.SECONDS)           //设置超时时间 15 秒
                    .addNetworkInterceptor(interceptor)             //网络拦截器。
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)    //这里的BaseUrl 呵呵
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //RxJava 适配器，这个不太敢这样，就怕Hold不住
                    .client(client)  // 是这样的设置吗？
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public interface getWeatherApi {
        @GET("adat/sk/{cityId}.html")
        Call<WeatherJson> getWeather(@Path("cityId") String cityId);
    }

//    public interface GitHubService {
//        @GET("repos/{owner}/{repo}/contributors")
//        Call<List<Contributor>> repoContributors(
//                @Path("owner") String owner,
//                @Path("repo") String repo);
//    }

    /**
     * 检查号码是否存在
     */
    public interface checkNumberApi {
        @GET("api/lebang/staffs/mobile/{mobile}")
        Call<BaseResponse>  checkNumber(@Path("mobile") String mobile);
    }

    /**
     * 测试登录
     */
    public interface LoginApi {
        @POST("api/lebang/oauth/access_token")
        Call<LoginResponse> goLogin(@Body LoginParams loginParams);  //设置一下Header！do call
    }

    /**
     *获取组织
     */
    public interface getOrganazationsApi{     //需要登录的就是添加那几个参数嘛
//        @Headers(
//                {
//                "Authorization:Bearer dJ1FyRk21bxbxPERuPmujjFLWwgGRO",
//                "User-Agent:Retrofit Test"   //这个是没有用的吧
//                } )

        @GET("api/lebang/organizations")
        Call<OrganizationResponse> getOrganazations();
    }


}
