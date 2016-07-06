package com.example.zenglb.retrofittest.response;

/**
 * Created by chenfm01 on 2015/8/12.
 */
public class LoginResponse extends BaseResponse{

    private Result result;

    public LoginResponse(int code, String error, Result result) {
        super(code, error);
        this.result = result;
    }

    public class Result {

        private String access_token;
        private String token_type;
        private String refresh_token;
        private String scope;
        private int expires;//access_token过期时间

        public String getAccessToken() {
            return access_token;
        }

        public void setAccessToken(String access_token) {
            this.access_token = access_token;
        }

        public String getTokenType() {
            return token_type;
        }

        public void setTokenType(String token_type) {
            this.token_type = token_type;
        }

        public String getRefreshToken() {
            return refresh_token;
        }

        public void setRefreshToken(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public int getExpires() {
            return expires;
        }

        public void setExpires(int expires) {
            this.expires = expires;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "access_token='" + access_token + '\'' +
                    ", token_type='" + token_type + '\'' +
                    ", refresh_token='" + refresh_token + '\'' +
                    ", scope='" + scope + '\'' +
                    ", expires=" + expires +
                    '}';
        }
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
