# 前言
本Demo只是作为随意的练习实验使用，不再更新。后期Retrofit相关更改放在：https://github.com/AnyLifeZLB/AndroidAppFrameWork


![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/banner.jpg)

>  Retrofit2 官方：http://square.github.io/retrofit  
>  Retrofit2 使用注意点总结：https://futurestud.io/tutorials/tag/retrofit


# 如果所有api 返回格式都和github api v3 一样Restful

根据服务器的api再次封装一下，个人推荐下面的简洁访问样式（api 由github 提供）
```
     // List your repositories
     @GET("/user/repos")
     Call<List<Repositories>> getRepositories(@Query("page") int page);

	//获取Repositories 数据 !
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


# 但，假如你的API 不是那么的Restful
但是由于不同的业务场景并不会一样，假如你项目定的Http api结构和github API V3一样Restful； 返回结果的json样式也是一样，
请忽视以下内容，关掉本页面基本不需要再封装什么。   
but 假如你的服务器返回的数据格式 不是那么的Restful而是大致如下类似,请往下看：

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

#在本Demo中,Api结构 不像github api (V3)那样的Restful，大概的使用和解释如下：（更多Clone后见代码）
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
                textView.setText(loginResultHttpResponse.getResult());
            }

            @Override
            public void onFailure(int code,String message) {
            	super.onFailure(code,message);
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
- (Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)
- (Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)
- (Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)


![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/1111.png)


# 关于更好的理解Retrofit-首先要理解HTTP，然后深入分析Retrofit,不要只是用，
- 解耦分析：http://www.jianshu.com/p/45cb536be2f4 
- Http 协议详解：(书籍：Http 图解，上野-宣 著)
- RFC 2616(科学上网)：http://www.ietf.org/rfc/rfc2616.txt   


# 欢迎对Demo进行指正改进
