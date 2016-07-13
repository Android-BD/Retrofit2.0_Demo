# Retrofit2.0_Demo
Retrofit2.0 练习使用，依托Retrofit2.0（+okhttp3） 的强大,根据服务器的api再次封装一下。更加简洁的Http请求处理

        //1.登录提交的参数
        LoginParams loginParams=new LoginParams();
        loginParams.setClient_id("43244444444443214321");
        loginParams.setClient_secret("4444444432222143214321");
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

More：any question,please contact me at anylife.zlb@gmail.com
