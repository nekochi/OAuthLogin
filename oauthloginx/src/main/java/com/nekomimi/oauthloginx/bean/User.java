package com.nekomimi.oauthloginx.bean;

/**
 * Created by Administrator on 2016/10/18.
 */
public class User {
    private String uid;
    private String username;
    private String ava_url;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAva_url() {
        return ava_url;
    }

    public void setAva_url(String ava_url) {
        this.ava_url = ava_url;
    }
}
