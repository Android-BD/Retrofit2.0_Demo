package com.example.zenglb.retrofittest;

import com.example.zenglb.retrofittest.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
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


    /**
     * 这个名字不好
     * @return
     */
    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)    //这里的BaseUrl 呵呵
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //RxJava 适配器，这个不太敢这样，就怕Hold不住
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
        Call<ResponseBody>  checkNumber(@Path("mobile") String mobile);
    }

    /**
     * 测试登录
     */
    public interface LoginApi {
        @POST("api/lebang/oauth/access_token")
        Call<LoginResponse> goLogin(@Body LoginParams loginParams);  //设置一下Header！
    }


}
