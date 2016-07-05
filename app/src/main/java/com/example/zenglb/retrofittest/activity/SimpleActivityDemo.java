package com.example.zenglb.retrofittest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.zenglb.retrofittest.LoginParams;
import com.example.zenglb.retrofittest.R;
import com.example.zenglb.retrofittest.WeatherJson;
import com.example.zenglb.retrofittest.http.HttpCall;
import com.example.zenglb.retrofittest.http.HttpClient;
import com.example.zenglb.retrofittest.response.LoginResponse;
import com.example.zenglb.retrofittest.utils.TextUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *Retrofit 最原始的使用方式....
 *
 */
public class SimpleActivityDemo extends AppCompatActivity {
    private final String TAG=SimpleActivityDemo.class.getSimpleName();
    private TextView textView;


    /**
     * 检查号码是否存在
     */
    public interface CheckNumberApi {
        @GET("api/lebang/staffs/mobile/{mobile}")
        Call<ResponseBody>  checkNumber(@Path("mobile") String mobile);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demo);
        textView=(TextView) findViewById(R.id.message);

        CheckNumberApi apiStores = HttpClient.retrofit().create(CheckNumberApi.class);
        Call<ResponseBody> call = apiStores.checkNumber("1882656205"); //检查号码是否已经注册通过了
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                    try {
                        textView.setText(response.body().string()); //try 的很是烦人
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    try {
                        textView.setText(TextUtils.convertUnicode(response.errorBody().string()+"@@@@"+response.code())); //try 的很是烦人
//                        Log.e(TAG,TextUtils.convertUnicode(response.errorBody().string()+"#####"+response.code()));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,call.toString()+t.toString());
                textView.setText(call.toString()+t.toString()); //try 的很是烦人
            }
        });

    }


}
