package com.demo.zenglb.retrofittest.http.result;

import com.demo.zenglb.retrofittest.http.HttpResponse;
import com.demo.zenglb.retrofittest.http.param.Datas;
import com.demo.zenglb.retrofittest.http.param.JobsData;
import com.demo.zenglb.retrofittest.http.param.LoginParams;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zenglb on 2017/3/20.
 */

public interface ApiService {
    /**
     * login/oauth2
     */
    @Headers("test: ")
    @POST("api/lebang/oauth/access_token")
    Call<HttpResponse<LoginResult>> goLogin(@Body LoginParams loginParams);

    /**
     * this request after login/oauth before logout
     * but no need oauth,so do not add auth header
     *
     * @param loginParams
     */
    @POST("api/lebang/oauth/access_token")
    @Headers("NoNeedAuthFlag: NoNeedAuthFlag")
    Call<HttpResponse<LoginResult>> refreshToken(@Body LoginParams loginParams);

    /**
     * test get something
     */
    @GET("api/lebang/staffs/me/modules")
    Call<HttpResponse<Modules>> getModules();

    /***
     * @param url
     * @return
     */
    @Streaming
    @GET()
    Observable<ResponseBody> downloadApp(@Url String url);


    //============================================  some typical http request   ==========================================

    /**
     * get Message List();
     * <p>
     * Result is json Array, not json object
     */
    @GET("api/lebang/staffs/me/msgs")
    Call<HttpResponse<List<Messages>>> getMessages(@Query("page1") int page, @Query("per_page") int perPage);


    /**
     * 测试完毕后         删除，删除，删除
     *
     * @param
     * @return
     */
    @POST("api/lebang/push/")
    Call<HttpResponse<EasyResult>> postPushData(@Body Datas<String> datas);

    /**
     * 完善接口           删除，删除，删除
     *
     * @param pushReportDatas
     * @return
     */
    @POST("api/lebang/staffs/me/")
    Call<HttpResponse<EasyResult>> postJobs(@Body List<JobsData> pushReportDatas);

}
