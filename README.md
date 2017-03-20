# Retrofit2.0 Example   
本文地址：https://github.com/AnyLifeZLB/Retrofit2.0_Demo   
更加完善的使用见：https://github.com/AnyLifeZLB/AndroidAppFrameWork  

![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/banner.jpg)

>  Retrofit2 官方：http://square.github.io/retrofit  
>  Retrofit2 中文教程：http://www.jianshu.com/p/308f3c54abdd  
>  Retrofit2 英文教程：https://futurestud.io/tutorials/retrofit-how-to-refresh-an-access-token


如果所有api 返回格式都和github api v3 一样Restful,那直接的使用也会很爽，但是由于不同的业务场景并不会一样

假如你项目定的Http api和github API V3一样Restful 返回结果的json样式也是一样;请忽视以下内容，关掉本页面。
but 假如你的服务器返回的数据格式大致如下类似,请往下看：

```
        //Http success
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
        
         // Http error
        {
            "code": HTTP_BAD_REQUEST,
            "error": "错误信息"
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
上面的例子使用的是github 的api (V3)。  https://developer.github.com/v3/orgs/#list-your-organizations

![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/banner.jpg)



#在本Demo 中的使用，更多见代码
```

        //1.post [LoginParams --> json] in http body
        LoginParams loginParams=new LoginParams();
        loginParams.setClient_id("if i should see you after long years,how should i greet");
        loginParams.setClient_secret("with tear? with slience");
        loginParams.setUsername("1882656xxxx");
        loginParams.setPassword("dddddd");

        //2.实例化Http的请求。泛型语法比较晦涩，然而我感觉很精简
        //发起调用也非常的简单，首先定义一个Call,把参数loginParams 和 返回 HttpResponse<LoginResult> 放进去
        //goLogin 这个http 请求在apiService 中用注解的方式定义好
        Call<HttpResponse<LoginResult>> loginCall = HttpCall.getApiService(this).goLogin(loginParams);

        //下面的基本就是代码自动生成了，Ctrl+O ,选择重写suc和failed (没有特殊的可以不重写，因为一般的failed都已经处理好了)
        loginCall.enqueue(new HttpCallBack<HttpResponse<LoginResult>>(this) {

            //Ctrl + O 自动生成重写的方法，处理Success 返回的数据
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
        
        
    /** test 2
     * 请求身份信息,返回的是List JsonArray  ---》  HttpResponse<List<IdentifyResult>>
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

# 重要的事情说三遍！
(Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)
(Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)
(Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)


![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/1111.png)


# Http 基础知识   (引用的链接都私有化了，不舍得分享啊)
> 使用Fiddler 抓包 能够更好的理解Http 协议

- Http 协议详解：https://www.xxx.yyy.zzz
- RFC 2616(科学上网)：http://www.ietf.org/rfc/rfc2616.txt

