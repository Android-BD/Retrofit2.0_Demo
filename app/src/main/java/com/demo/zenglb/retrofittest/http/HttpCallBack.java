package com.demo.zenglb.retrofittest.http;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.Log;
import android.widget.Toast;
import com.demo.zenglb.retrofittest.utils.TextUtils;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 这里的封装处理方式和api 协议结构有关。因为我们的Api 不是那么的restful-需要再处理一下
 * <p>
 * 一般的Success 的处理各不相同，但是fail会有很多相同的处理方式
 * 一定要处理好各种异常情况。
 */
//public abstract class BaseObserver<T> implements Observer<HttpResponse<T>> {

public abstract class HttpCallBack<T> implements Callback<HttpResponse<T>> {
    private final String TAG = HttpCallBack.class.getSimpleName();
    private static Gson gson = new Gson();
    private Context mContext;
    private final int RESPONSE_CODE_OK = 0;      //自定义的业务逻辑，成功返回积极数据
    private final int RESPONSE_CODE_FAILED = -1; //返回数据失败

    //是否需要显示Http 请求的进度，默认的是需要，但是Service 和 预取数据不需要
    private boolean showProgress = true;

    /**
     * @param mContext
     */
    public HttpCallBack(Context mContext) {
        this.mContext = mContext;
        if (showProgress) {
            //show your progress bar
            HttpUiTips.showDialog(mContext,true, "loading...");
        }
    }

    /**
     * @param mContext
     * @param showProgress 默认需要显示进程，不要的话请传 false
     */
    public HttpCallBack(Context mContext, boolean showProgress) {
        this.showProgress = showProgress;
        this.mContext = mContext;
        if (showProgress) {
            HttpUiTips.showDialog(mContext,true, null);
        }
    }


    public abstract void onSuccess(T t);

    /**
     * Default error dispose!
     * 一般的就是 AlertDialog 或 SnackBar
     *
     * @param code
     * @param message
     */
    @CallSuper  //if overwrite,you should let it run.
    public void onFailure(int code, String message) {
        if (code == RESPONSE_CODE_FAILED && mContext != null) {
            HttpUiTips.alertTip(mContext,message, code);
        } else {
            disposeEorCode(message, code);
        }
    }


    @Override
    public final void onResponse(Call<HttpResponse<T>> call, Response<HttpResponse<T>> response) {
        HttpUiTips.dismissDialog(mContext);
        if (response.isSuccessful()) {  //mean that   code >= 200 && code < 300
            int responseCode = response.body().getCode();
            //responseCode是业务api 里面定义的,根据responseCode进行进一步的数据和事件分发!
            if (responseCode == RESPONSE_CODE_OK) {
                onSuccess(response.body().getResult());
            } else {
                onFailure(responseCode, response.body().getError());
            }
        } else {
            //================ 1.handle http default error 4xx,5xx=================
            int code = response.raw().code();
            String message = response.raw().message();   //code 和 message 都是http Raw 数据，你抓包就能看见的
            Log.e("http-error", "code:" + code + "   message:" + message);
            //我们的项目返回404 的时候有可能是翻页到没有数据了,这一点很恶心
            if (code != 404) {
                onFailure(code, message);
                return;
            }

            //================ 2.把项目业务方面定义的错误提取处理处理，和业务逻辑，api 有关=================
            String errorBodyStr = "";
            try {   //我们的项目需要的UniCode转码，不是必须要的！
                errorBodyStr = TextUtils.convertUnicode(response.errorBody().string());
            } catch (IOException ioe) {
                Log.e("errorBodyStr ioe:", ioe.toString());
            }
            try {
                HttpResponse errorResponse = gson.fromJson(errorBodyStr, HttpResponse.class);
                if (null != errorResponse) {
                    onFailure(errorResponse.getCode(), errorResponse.getError());
                    //这里的code 如果定义和public void onFailure(Call<T> call, Throwable t) { 一样，要记得分开处理
                } else {
                    onFailure(RESPONSE_CODE_FAILED, "ErrorResponse is null ");  //!!!!!!
                }
            } catch (Exception jsonException) {
                onFailure(RESPONSE_CODE_FAILED, "http请求错误Json 信息异常"); //
                jsonException.printStackTrace();
            }
        }//response is not Successful dispose over !
    }

    @Override
    public final void onFailure(Call<HttpResponse<T>> call, Throwable t) {
        HttpUiTips.dismissDialog(mContext);
        String temp = t.getMessage().toString();

        String errorMessage = "获取数据失败[def-error]" + temp;
        if (t instanceof SocketTimeoutException) {
            errorMessage = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorMessage = "网络连接异常，请检查网络";
        } else if (t instanceof RuntimeException) {
            errorMessage = "运行时错误";
        } else if (t instanceof UnknownHostException) {
            errorMessage = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorMessage = "未知的服务器错误";
        }
        onFailure(RESPONSE_CODE_FAILED, errorMessage);
    }

    /**
     * 对通用问题的统一拦截处理
     *
     * @param code
     */
    private void disposeEorCode(String message, int code) {
        switch (code) {
            case 101:
            case 112:
            case 123:
            case 401:
                //退回到登录页面，
//				Intent intent = new Intent();
//				intent.setClass(mContext, LoginActivity.class);
//				mContext.startActivity(intent);
                break;
        }
        Toast.makeText(mContext, message + " # " + code, Toast.LENGTH_SHORT).show();
    }

}
