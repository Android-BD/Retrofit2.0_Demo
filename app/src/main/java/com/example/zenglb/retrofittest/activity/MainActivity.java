package com.example.zenglb.retrofittest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.zenglb.retrofittest.LoginParams;
import com.example.zenglb.retrofittest.NewHttp.EasyResult;
import com.example.zenglb.retrofittest.NewHttp.LoginResult;
import com.example.zenglb.retrofittest.NewHttp.HttpCallBack;
import com.example.zenglb.retrofittest.NewHttp.HttpResponse;
import com.example.zenglb.retrofittest.NewHttp.xHttpCall;
import com.example.zenglb.retrofittest.R;
import com.example.zenglb.retrofittest.http.HttpCall;
import com.example.zenglb.retrofittest.http.HttpCallback;
import com.example.zenglb.retrofittest.http.HttpClient;
import com.example.zenglb.retrofittest.response.BaseResponse;
import com.example.zenglb.retrofittest.response.LoginResponse;
import com.example.zenglb.retrofittest.response.OrganizationResponse;

import retrofit2.Call;

/**
 * where you are
 * 1
 *
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG=MainActivity.class.getSimpleName();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.message);

        //1.参数的封装
        LoginParams loginParams=new LoginParams();
        loginParams.setClient_id("5e96eac06151d0ce2dd9554d7ee167ce");
        loginParams.setClient_secret("aCE34n89Y277n3829S7PcMN8qANF8Fh");
        loginParams.setGrant_type("password");
        loginParams.setUsername("18826562075");
        loginParams.setPassword("zxcv1234");

        //2.实例化Http的请求。
        Call<HttpResponse<LoginResult>> loginCall = xHttpCall.getApiService(this).goLogin(loginParams); //尝试登陆
        loginCall.enqueue(new HttpCallBack<HttpResponse<LoginResult>>() {
            @Override
            public void onSuccess(HttpResponse<LoginResult> loginResultHttpResponse) {
                Log.e(TAG, loginResultHttpResponse.getResult().toString());
                textView.setText(loginResultHttpResponse.getResult().toString());
            }

            @Override
            public void onFailure(int code,String message) {
                textView.setText(code+"@@@@"+message);      //
            }
        });



//        HttpClient.checkNumberApi apiStores = HttpClient.retrofit(this).create(HttpClient.checkNumberApi.class);
//        Call<BaseResponse> call = apiStores.checkNumber("18826562075"); //检查号码是否已经注册通过了
//
//        new HttpCall().call(call, new HttpCallback() {
//            @Override
//            public void onSuccess(BaseResponse response) {
//                textView.setText(response.getCode()+response.getError());
//            }
//
//            @Override
//            public void onFailure(int code, String message) {
//                Log.e(TAG,code+message);
//                textView.setText(code+message);   //hello kitty
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//        });

        //点击
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getOrganization();
                checkNumber();

            }
        });
    }

    /**
     * 检查号码是否被注册了。
     */
    private void checkNumber(){
        //2.实例化Http的请求。

        //这个时候的response 是一个没有用的response啊！
        Call<HttpResponse<EasyResult>> checkMobileCall = xHttpCall.getApiService(this).checkMobile("18826562075"); //尝试登陆
        checkMobileCall.enqueue(new HttpCallBack<HttpResponse<EasyResult>>() {
            @Override
            public void onSuccess(HttpResponse<EasyResult> checkMobileHttpResponse) {
                Log.e(TAG, checkMobileHttpResponse.getResult().toString());
                textView.setText(checkMobileHttpResponse.getResult().toString());
            }

            @Override
            public void onFailure(int code,String message) {
                textView.setText(code+"@@@@"+message);      //
            }
        });
    }

    /**
     * 获取组织架构
     *
     */
    private void getOrganization(){
        HttpClient.getOrganazationsApi getOrganazationsApi = HttpClient.retrofit(this).create(HttpClient.getOrganazationsApi.class);
        Call<OrganizationResponse> call = getOrganazationsApi.getOrganazations(); //这两句我也不想写在外面
        //3.第一种方式发射
        new HttpCall().call(call, new HttpCallback() {
            @Override
            public void onSuccess(BaseResponse response) {
//                LoginResponse loginResponse=(LoginResponse)response;
//                textView.setText(loginResponse.getResult().getAccessToken());
                OrganizationResponse organizationResponse=(OrganizationResponse)response;
                textView.setText(organizationResponse.getResult().toString());
            }

            @Override
            public void onFailure(int code, String message) {
                textView.setText(message+" @@@% "+code);
//                messageStr=message+" @@@% "+code;
            }

            @Override
            public void onError(Throwable t) {
                textView.setText(t.toString());
//                messageStr=t.toString();
            }
        });

    }



    /**
     * 登录
     *
     */
    private void Login(){

        //1.参数的封装
        LoginParams loginParams=new LoginParams();
        loginParams.setClient_id("5e96eac06151d0ce2dd9554d7ee167ce");
        loginParams.setClient_secret("aCE34n89Y277n3829S7PcMN8qANF8Fh");
        loginParams.setGrant_type("password");
        loginParams.setUsername("18826562075");
        loginParams.setPassword("zxcv1234");

        //2.弹药准备上膛
        HttpClient.LoginApi loginApi = HttpClient.retrofit(this).create(HttpClient.LoginApi.class);
        Call<LoginResponse> call = loginApi.goLogin(loginParams); //这两句我也不想写在外面

        //3.第一种方式发射
        new HttpCall().call(call, new HttpCallback() {
            @Override
            public void onSuccess(BaseResponse response) {
                LoginResponse loginResponse=(LoginResponse)response;
                textView.setText(loginResponse.getResult().getAccessToken());

            }

            @Override
            public void onFailure(int code, String message) {
                textView.setText(message+" @@@% "+code);
//                messageStr=message+" @@@% "+code;
            }

            @Override
            public void onError(Throwable t) {
                textView.setText(t.toString());
//                messageStr=t.toString();
            }
        });


        //4.第二种方式发射，这样子就很是麻烦，需要判断的东西实在是太多太多了
        //.弹药准备上膛

//        HttpClient.LoginApi loginApi2 = HttpClient.retrofit().create(HttpClient.LoginApi.class);
//        Call<LoginResponse> call2 = loginApi2.goLogin(loginParams); //这两句我也不想写在外面
//        call2.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {    //
//                if(response.isSuccessful()){                    // code:[200,300）
//                    int responseCode=response.body().getCode(); //这个code才是应用层的Code,进行进一步的数据和事件分发!
//                    if(responseCode==HttpClient.HTTP_SUCCESS){
//                        textView.setText(response.body().getResult().toString());   //账号密码正确，直接通过了
//                    }else{
//                        //dispose error case;
//                        textView.setText(response.body().getError());                //if code is not go to the end,why jump
//                    }
//                }else{      //
//                    try {
//                        textView.setText(TextUtils.convertUnicode(response.errorBody().string()+"@@@@"+response.code())); //try 的很是烦人
//                        Log.e(TAG,TextUtils.convertUnicode(response.errorBody().string()+"#####"+response.code()));
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Log.e(TAG,call.toString()+t.toString());
//                textView.setText(TextUtils.convertUnicode(call.toString()+t.toString())); //try 的很是烦人
//            }
//        });


    }





}
