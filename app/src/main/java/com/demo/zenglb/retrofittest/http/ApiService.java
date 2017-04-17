package com.demo.zenglb.retrofittest.http;

import com.demo.zenglb.retrofittest.http.HttpResponse;
import com.demo.zenglb.retrofittest.http.param.Datas;
import com.demo.zenglb.retrofittest.http.param.JobsData;
import com.demo.zenglb.retrofittest.http.param.LoginParams;
import com.demo.zenglb.retrofittest.http.result.EasyResult;
import com.demo.zenglb.retrofittest.http.result.LoginResult;
import com.demo.zenglb.retrofittest.http.result.Messages;
import com.demo.zenglb.retrofittest.http.result.Modules;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by zenglb on 2017/3/20.
 */

public interface ApiService {

    /**
     * login/oauth2 By rxjava2
     *
     */
    @Headers("NoNeedAuthFlag: NoNeedAuthFlag")
    @POST("api/lebang/oauth/access_token")
    Observable<HttpResponse<LoginResult>> goLoginByRxjava2(@Body LoginParams loginRequest);


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

//    /***
//     * @param url
//     * @return
//     */
//    @Streaming
//    @GET()
//    Observable<ResponseBody> downloadApp(@Url String url);


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
