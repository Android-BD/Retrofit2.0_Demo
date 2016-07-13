# Retrofit2.0_Demo
Retrofit2.0 练习使用，依托Retrofit2.0（+okhttp3） 的强大,根据服务器的api再次封装一下。更加简洁的Http请求处理

        //1.登录提交的参数
        LoginParams loginParams=new LoginParams();
        loginParams.setClient_id("5e96eac06151d0ce2dd9554d7ee167ce");
        loginParams.setClient_secret("aCE34n89Y277n3829S7PcMN8qANF8Fh");
        loginParams.setGrant_type("password");
        loginParams.setUsername("18826562075");
        loginParams.setPassword("zxcv12345");

        //2.实例化Http的请求。
        Call<HttpResponse<LoginResult>> checkMobileCall = xHttpCall.getApiService(this).goLogin(loginParams); //尝试登陆
        checkMobileCall.enqueue(new HttpCallBack<HttpResponse<LoginResult>>() {
            @Override
            public void onSuccess(HttpResponse<LoginResult> loginResultHttpResponse) {
                Log.e(TAG, loginResultHttpResponse.getResult().toString());
                textView.setText(loginResultHttpResponse.getResult().toString());
            }

            @Override
            public void onFailure(int code,String message) {
                textView.setText(code+"@@@@"+message);

            }
        });

More：any question,please contact me at anylife.zlb@gmail.com
