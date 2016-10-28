package com.nekomimi.oauthloginx;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface LoginListener {
    void onLoginSuccess(OAuthManager oAuthManager);
    void onLoginFail(String result);
}
