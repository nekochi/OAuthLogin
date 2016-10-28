package com.nekomimi.oauthloginx.login;

import com.nekomimi.oauthloginx.bean.OAuthConfig;

/**
 * Created by Administrator on 2016/9/2.
 */
public interface OAuthLoginContract {
    interface View{
        void showProgress();
        void hideProgress();
        void closeWeb();
        void openWeb();
        void success(String token);
    }

    interface Presenter{
        void getToken(OAuthConfig config, String oauthCode);
        void destroy();
    }
}
