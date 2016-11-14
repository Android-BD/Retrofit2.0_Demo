![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/banner.jpg)
#Retrofit 为什么需要再次封装

- api 不是那么的Restful  
- 统一请求过程中的处理
- http 错误处理  
## API 不是那么的Restful
我们知道github api（https://developer.github.com/v3） 是非常的restful 的风格，比如  
>List all organizations GET /organizations 这个请求返回
```
[
  {
    "login": "github",
    "id": 1,
    "url": "https://api.github.com/orgs/github",
    "repos_url": "https://api.github.com/orgs/github/repos",
    "events_url": "https://api.github.com/orgs/github/events",
    "hooks_url": "https://api.github.com/orgs/github/hooks",
    ... ...
  }
]
```
是不是很Restful，但是这样的格式有个弊端就是：当在浏览器调试api，后端查询出错时，很难查看错误码&错误信息。（当然用chrome的开发者工具可以看，但麻烦）

所以我们看见的http 数据返回格式一般是这样的：  
```
        {
            "code": 0, 
            "error": "",
            "result":
                   {
                       "access_token": "if i should see you after long years,how should i greet",
                       "token_type": "Bearer",
                       "expires": "2016-05-12 17:13:13",
                       "refresh_token": "with tear? with slience",
                       "scopes": "all"
                   }
        }
```  
这样的好处就是调试api方便，在任意浏览器都可以直观地看到错误码和错误信息

那么我们就不会像如下一样官方示例的样式进行请求：
```
Call<Organizations> requ = userService.getUser("anylife");
//异步网络请求
requ.enqueue(new CallBack<User>(){
    @Override
    public void onResponse(Call<Person> call, Response<Organizations> response) {
        //不那么Restful 以后
        成功（返回了你想要的数据，code =0，error=""）      (失败code=123,error="不存在啊"）
        是要分开处理的
    }

    @Override
    public void onFailure(Call<Person> call, Throwable t) {
        //这个每个请求都要去处理SocketTimeoutException,ConnectException，UnknownHostException 等等的会累死人
    }
});
```

那么就要进行改造CallBack<T> 
```
public abstract class HttpCallBack<T extends HttpResponse> implements Callback<T> {
	private static Gson gson = new Gson();
	@Override
	public void onResponse(Call<T> call, Response<T> response) {
		dismissDialog();
		if (response.isSuccessful()) {
			int responseCode = response.body().getCode();   //responseCode是api 里面定义的,进行进一步的数据和事件分发!
			if (responseCode == 0) {
				onSuccess(response.body());
			} else {
				onFailure(responseCode, response.body().getError());
			}
		} else {  // 一定要压倒所有case
			//================ test http 400-http 500 错误=================
			int code = response.raw().code();
			String message = response.raw().message();
			//================ test http 400-http 500 错误=================
			String errorBodyStr = "";
			try {
				errorBodyStr = TextUtils.convertUnicode(response.errorBody().string());
			} catch (IOException ioe) {
				Log.e("errorBodyStr ioe:", ioe.toString());
			}
			try {
				HttpResponse errorResponse = gson.fromJson(errorBodyStr, HttpResponse.class);
				if (null != errorResponse) {
					onFailure(errorResponse.getCode(), errorResponse.getError()); 
				} else {
					onFailure(-1, "ErrorResponse is null ");  
				}
			} catch (Exception jsonException) {//数据解析异常！
			}

		}//response is not Successful dispose over !

	}

	/**
	 * 区别处理Htpp error 和 业务逻辑的Error code ,如果有重复，需要区别处理
	 * <p>
	 * Invoked when a network exception occurred talking to the server or when an unexpected
	 * exception occurred creating the request or processing the response.
	 */
	@Override
	public void onFailure(Call<T> call, Throwable t) {
		dismissDialog();
		String temp = t.getMessage().toString();
		String errorMessage = "获取数据失败[Def-eor]" + temp;
		if (t instanceof SocketTimeoutException) {
			errorMessage = "服务器响应超时";
		} 
		onFailure(-1, errorMessage);
	}

}
```

那么请求的时候就变为类似这样  
```
		Call<T> loginCall = HttpCall.getApiService().goLogin(XXXXX); 
		loginCall.enqueue(new HttpCallBack<T>(this) {
			@Override
			public void onSuccess(HttpResponse<LoginResult> loginResultHttpResponse) {
				Log.e(TAG, loginResultHttpResponse.getResult().toString());
			}

			@Override
			public void onFailure(int code, String messageStr) {
				super.onFailure(code, messageStr);
			}
		});
```






