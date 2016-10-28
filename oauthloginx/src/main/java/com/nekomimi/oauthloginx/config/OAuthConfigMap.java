package com.nekomimi.oauthloginx.config;

import android.support.v4.util.ArrayMap;

import com.nekomimi.oauthloginx.bean.OAuthConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/19.
 */
public class OAuthConfigMap {
    public final static Map<String, String> configMap = new ArrayMap<String, String>() {{
        put("BAIDU","getBaidu");
        put("GITHUB","getGithub");
    }};

    public void getGithub(OAuthConfig config){
        config.setWeb_name("GITHUB");
        config.setClient_id_key("client_id");
        config.setClient_secret_key("client_secret");
        config.setToken_key("access_token");
        config.setOauth_code_key("code");
        config.setRedirect_uri_key("redirect_uri");
        config.setOauth_url("https://github.com/login/oauth/authorize");
        config.setToken_url("https://github.com/login/oauth/access_token");
    }

    public void getBaidu(OAuthConfig config){
        config.setWeb_name("BAIDU");
        config.setClient_id_key("client_id");
        config.setClient_secret_key("client_secret");
        config.setToken_key("access_token");
        config.setOauth_code_key("code");
        config.setRedirect_uri_key("redirect_uri");
        config.setOauth_static_value("response_type=code");
        config.setOauth_url("https://github.com/login/oauth/authorize");
        config.setToken_url("https://github.com/login/oauth/access_token");
        HashMap<String, String> token_params_map = new HashMap<>();
        token_params_map.put("grant_type","authorization_code");
        config.setToken_other_params(token_params_map);
    }
}
