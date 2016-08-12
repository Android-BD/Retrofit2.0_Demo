package com.example.zenglb.retrofittest.NewHttp;
import android.util.Log;

import com.example.zenglb.retrofittest.http.HttpClient;
import com.example.zenglb.retrofittest.response.BaseResponse;
import com.example.zenglb.retrofittest.utils.TextUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Rename as http callback.
 *
 * Created by Anylife.zlb@gmail.com on 2016/7/11.
 */
public abstract class HttpCallBack<T extends HttpResponse> implements Callback<T>{
    private String TAG=HttpCallBack.class.getSimpleName();
    private static Gson gson = new Gson();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){                                  //Http 状态码code:[200,300）
            int responseCode=response.body().getCode();               //responseCode是api 里面定义的,进行进一步的数据和事件分发!
            if(responseCode== HttpClient.HTTP_SUCCESS){
                onSuccess(response.body());
            }else{
                onFailure(responseCode,response.body().getError());
            }
        }else{
            try {
                String errorBodyStr= TextUtils.convertUnicode(response.errorBody().string());
                Log.e(TAG,errorBodyStr);
                //errorBodyStr is empty or is not a format json text,dispose !!!!!!!!!!!!
                BaseResponse baseResponse=gson.fromJson(errorBodyStr,BaseResponse.class );
                if(null!=baseResponse){
                    onFailure(baseResponse.getCode(),baseResponse.getError());
                    Log.e(TAG, baseResponse.getCode()+"%% %%"+baseResponse.getError());
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {    //网络问题会走该回调
        if(t instanceof SocketTimeoutException){
            //
        }else if(t instanceof ConnectException){
            //
        }else if(t instanceof RuntimeException){
            //
        }
        onFailure(-1,t.getMessage());                      //可能吧
    }


    public abstract void onSuccess(T t);

    public abstract void onFailure(int code,String message);


}
