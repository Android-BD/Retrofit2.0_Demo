# Retrofit2.0_Demo
![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/banner.jpg)

如果所有api 返回格式都和github api v3 一样Restful,那直接的使用也会很爽，但是由于不同的业务场景并不会一样

假如你的Server api和github API V3一样Restful 并且返回结果的json样式也是一样;请忽视以下内容，关掉本页面。
but 假如你的服务器返回的数据格式大致如下类似,请往下看：

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

根据服务器的api再次封装一下。更加简洁的Http请求处理.个人推荐下面的简洁访问样式（api 由github 提供）
```
     /**
      * List your repositories
      */
     @GET("/user/repos")
     Call<List<Repositories>> getRepositories(@Query("page") int page);
    
    /**
	 * 获取Repositories 数据
	 */
	private void getRepositories(final int page){
		Call<List<Repositories>> newsCall = HttpCall.getApiService(mActivity).getRepositories(page);
		newsCall.enqueue(new HttpCallBack<List<Repositories>>(this) {
			@Override
			public void onSuccess(List<Repositories> repositiories) {
				Log.d("Repositories",repositiories.toString());
			}

			@Override
			public void onFailure(int code, String message) {
			    super.onFailure(code,message);

			}
		});
	} //
```
上面的例子使用的是github 的api (V3)。   https://developer.github.com/v3/orgs/#list-your-organizations

![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/banner.jpg)




#在Vanke zhuzher App Test App 的使用，更多见代码
```

        //1.登录提交的参数
        LoginParams loginParams=new LoginParams();
        loginParams.setClient_id("if i should see you after long years,how should i greet");
        loginParams.setClient_secret("with tear? with slience");
        loginParams.setGrant_type("password");
        loginParams.setUsername("1882656xxxx");
        loginParams.setPassword("dddddd");

        //2.实例化Http的请求。泛型语法比较晦涩，然而我感觉很精简
        Call<HttpResponse<LoginResult>> checkMobileCall = xHttpCall.getApiService(this).goLogin(loginParams); //尝试登陆
        checkMobileCall.enqueue(new HttpCallBack<HttpResponse<LoginResult>>(this) {
            @Override
            public void onSuccess(HttpResponse<LoginResult> loginResultHttpResponse) {
                Log.e(TAG, loginResultHttpResponse.getResult());
                textView.setText(loginResultHttpResponse.getResult());
            }

            @Override
            public void onFailure(int code,String message) {
            	super.onFailure(code,message);
                textView.setText(code+"@@@@"+message);
            }
        });
        
        
        
    /*** test 2
     * 请求身份信息,返回的是List Array
     *
     */
    private void  requestIdentify(){
        Call<HttpResponse<List<IdentifyResult>>> getIdentityCall = xHttpCall.getApiService(this).getIdentities(); 
        getIdentityCall.enqueue(new HttpCallBack<HttpResponse<List<IdentifyResult>>>(this) {
            @Override
            public void onSuccess(HttpResponse<List<IdentifyResult>> getIdentityCallResponse) {
                Log.e(TAG, getIdentityCallResponse.getResult().toString());
                textView2.setText(getIdentityCallResponse.getResult().toString());
            }

            @Override
            public void onFailure(int code,String message) {
            	super.onFailure(code,message);
                textView2.setText(code+"@@@@"+message);
            }
        });
    }
        
```
More：any question,please contact me at anylife.zlb@gmail.com

(Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)
(Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)
(Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)


![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/device-2016-09-30-170835.png)


