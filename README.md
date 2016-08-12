# Retrofit2.0_Demo

假如你的Server api和github API 一样Restful 并且返回结果的json样式也是一样。请忽视以下内容，关掉本页面。but 假如你的服务器返回的数据格式大致如下类似,请往下看：
(本Demo只是单纯的练习再次封装使用Retrofit 2.0,没有结合 Rxjava)


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


Retrofit2.0 练习使用，依托Retrofit2.0（+okhttp3） 的强大,根据服务器的api再次封装一下。更加简洁的Http请求处理.http 包和newhttp是两种封装样式，个人推荐下面的简洁访问样式（在 newhttp 包下面）

如果你喜欢，give me a Star, thank you.


        //1.登录提交的参数
        LoginParams loginParams=new LoginParams();
        loginParams.setClient_id("if i should see you after long years,how should i greet");
        loginParams.setClient_secret("with tear? with slience");
        loginParams.setGrant_type("password");
        loginParams.setUsername("1882656xxxx");
        loginParams.setPassword("dddddd");

        //2.实例化Http的请求。泛型语法比较晦涩，然而我感觉很精简
        Call<HttpResponse<LoginResult>> checkMobileCall = xHttpCall.getApiService(this).goLogin(loginParams); //尝试登陆
        checkMobileCall.enqueue(new HttpCallBack<HttpResponse<LoginResult>>() {
            @Override
            public void onSuccess(HttpResponse<LoginResult> loginResultHttpResponse) {
                Log.e(TAG, loginResultHttpResponse.getResult());
                textView.setText(loginResultHttpResponse.getResult());
            }

            @Override
            public void onFailure(int code,String message) {
                textView.setText(code+"@@@@"+message);

            }
        });
        
        
        
    /********************************************另外一种使用方式****************************************************
     * 请求身份信息,返回的是List Array
     *
     */
    private void  requestIdentify(){
        Call<HttpResponse<List<IdentifyResult>>> getIdentityCall = xHttpCall.getApiService(this).getIdentities(); 
        getIdentityCall.enqueue(new HttpCallBack<HttpResponse<List<IdentifyResult>>>() {
            @Override
            public void onSuccess(HttpResponse<List<IdentifyResult>> getIdentityCallResponse) {
                Log.e(TAG, getIdentityCallResponse.getResult().toString());
                textView2.setText(getIdentityCallResponse.getResult().toString());
            }

            @Override
            public void onFailure(int code,String message) {
                textView2.setText(code+"@@@@"+message);
            }
        });
    }
        
        
        
        

More：any question,please contact me at anylife.zlb@gmail.com

(Demo 中提供的数据和api 仅仅适用于本Demo的演示，交流。请勿传播扩散)
