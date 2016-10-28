package com.nekomimi.oauthloginx;

import com.nekomimi.oauthloginx.bean.User;

/**
 * Created by Administrator on 2016/10/12.
 *
 */
public class DefaultOAuthManager extends OAuthManager {


    @Override
    public void getUId(OnGetCallback<String> listener) {

    }

    @Override
    public void getUsername(OnGetCallback<String> listener) {

    }

    @Override
    public void getUserAva(OnGetCallback<String> listener) {

    }

    @Override
    public void getUser(OnGetCallback<User> listener) {

    }

    public DefaultOAuthManager(String token, String channel) {
        super(token, channel);
    }


}
