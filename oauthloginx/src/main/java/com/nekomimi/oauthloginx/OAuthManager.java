package com.nekomimi.oauthloginx;

import com.nekomimi.oauthloginx.bean.User;

/**
 * Created by Administrator on 2016/9/2.
 */
public abstract class OAuthManager {
    private String token;
    private String channel;


    public abstract void getUId(OnGetCallback<String> listener);
    public abstract void getUsername(OnGetCallback<String> listener);
    public abstract void getUserAva(OnGetCallback<String> listener);
    public abstract void getUser(OnGetCallback<User> listener);

    public OAuthManager(){}

    public OAuthManager(String token, String channel){
        this.token = token;
        this.channel = channel;
    }

    public interface OnGetCallback<T>{
        void onGet(T s);
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
