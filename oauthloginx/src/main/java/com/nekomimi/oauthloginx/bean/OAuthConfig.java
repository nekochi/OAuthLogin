package com.nekomimi.oauthloginx.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.nekomimi.oauthloginx.config.OAuthConfigMap;
import com.nekomimi.oauthloginx.utils.PropertiesUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/9/1.
 *
 */
public class OAuthConfig implements Parcelable {
    private static final String TAG = "OAuthConfig";

    private String web_name;
    private String oauth_url;
    private String token_url;

    private String oauth_static_value;

    private String client_id_key;
    private String client_secret_key;
    private String oauth_code_key;
    private String token_key;
    private String redirect_uri_key;

    private String client_id_value;
    private String client_secret_value;
    private String oauth_code_value;
    private String token_value;
    private String redirect_uri_value;

    private HashMap<String,String> token_other_params;

    protected OAuthConfig(Parcel in) {
        web_name = in.readString();
        oauth_url = in.readString();
        token_url = in.readString();
        oauth_static_value = in.readString();
        client_id_key = in.readString();
        client_secret_key = in.readString();
        oauth_code_key = in.readString();
        token_key = in.readString();
        redirect_uri_key = in.readString();
        client_id_value = in.readString();
        client_secret_value = in.readString();
        oauth_code_value = in.readString();
        token_value = in.readString();
        redirect_uri_value = in.readString();
        token_other_params = in.readHashMap(HashMap.class.getClassLoader());
    }

    public static final Creator<OAuthConfig> CREATOR = new Creator<OAuthConfig>() {
        @Override
        public OAuthConfig createFromParcel(Parcel in) {
            return new OAuthConfig(in);
        }

        @Override
        public OAuthConfig[] newArray(int size) {
            return new OAuthConfig[size];
        }
    };

    public static OAuthConfig parse4Config(Context context, String name){
        name = name.toLowerCase();
        OAuthConfig oAuthConfig = new OAuthConfig();
        String m = OAuthConfigMap.configMap.get(name.toUpperCase());
        if (!TextUtils.isEmpty(m)){
            try {
                Class<?> o = Class.forName("com.nekomimi.oauthloginx.config.OAuthConfigMap");
                Method method = o.getMethod(m, OAuthConfig.class);
                method.invoke(o.newInstance(), oAuthConfig);
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Properties properties = PropertiesUtil.getProperties4File(context.getAssets().open(name + ".properties"));
                if (properties!=null){
                    oAuthConfig.client_id_value = properties.getProperty("client_id_value");
                    oAuthConfig.client_secret_value = properties.getProperty("client_secret_value");
                    oAuthConfig.redirect_uri_value = properties.getProperty("redirect_uri_value");
                    return oAuthConfig;
                }else {
                    Log.e(TAG, "Error! Does file " + name + ".properties file exist in assets?");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try {
                Properties properties = PropertiesUtil.getProperties4File(context.getAssets().open(name + ".properties"));
                if (properties != null) {
                    oAuthConfig.web_name = name.toUpperCase();
                    oAuthConfig.client_id_key = properties.getProperty("client_id_key");
                    oAuthConfig.client_secret_key = properties.getProperty("client_secret_key");
                    oAuthConfig.oauth_code_key = properties.getProperty("oauth_code_key");
                    oAuthConfig.token_key = properties.getProperty("token_key");
                    oAuthConfig.redirect_uri_key = properties.getProperty("redirect_uri_key");
                    oAuthConfig.redirect_uri_value = properties.getProperty("redirect_uri_value");
                    oAuthConfig.client_id_value = properties.getProperty("client_id_value");
                    oAuthConfig.client_secret_value = properties.getProperty("client_secret_value");
                    oAuthConfig.oauth_static_value = properties.getProperty("oauth_static_value");
                    oAuthConfig.oauth_url = properties.getProperty("oauth_url");
                    oAuthConfig.token_url = properties.getProperty("token_url");
                    String token_params_str = properties.getProperty("token_other_params");
                    HashMap<String, String> token_params_map = new HashMap<>();
                    if (token_params_str != null && !token_params_str.isEmpty()) {
                        String[] p = token_params_str.split(",");
                        if (p.length > 0) {
                            for (int i = 0; i < p.length; i++) {
                                String p1[] = p[i].split("=");
                                if (p1.length == 2) {
                                    token_params_map.put(p1[0], p1[1]);
                                }
                            }
                        }
                        if (token_params_map.size() > 0) {
                            oAuthConfig.setToken_other_params(token_params_map);
                        }
                    }
                    return oAuthConfig;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public OAuthConfig(){}

    public OAuthConfig(String web_name){
        this.web_name = web_name;
    }

    public String getWeb_name() {
        return web_name;
    }

    public void setWeb_name(String web_name) {
        this.web_name = web_name;
    }

    public String getOauth_url() {
        return oauth_url;
    }

    public void setOauth_url(String oauth_url) {
        this.oauth_url = oauth_url;
    }

    public String getToken_url() {
        return token_url;
    }

    public void setToken_url(String token_url) {
        this.token_url = token_url;
    }

    public String getStatic_value() {
        return oauth_static_value;
    }

    public void setStatic_value(String static_value) {
        this.oauth_static_value = static_value;
    }

    public String getClient_id_key() {
        return client_id_key;
    }

    public void setClient_id_key(String client_id_key) {
        this.client_id_key = client_id_key;
    }

    public String getClient_secret_key() {
        return client_secret_key;
    }

    public void setClient_secret_key(String client_secret_key) {
        this.client_secret_key = client_secret_key;
    }

    public String getOauth_code_key() {
        return oauth_code_key;
    }

    public void setOauth_code_key(String oauth_code_key) {
        this.oauth_code_key = oauth_code_key;
    }

    public String getToken_key() {
        return token_key;
    }

    public void setToken_key(String token_key) {
        this.token_key = token_key;
    }

    public String getClient_id_value() {
        return client_id_value;
    }

    public void setClient_id_value(String client_id_value) {
        this.client_id_value = client_id_value;
    }

    public String getClient_secret_value() {
        return client_secret_value;
    }

    public void setClient_secret_value(String client_secret_value) {
        this.client_secret_value = client_secret_value;
    }

    public String getOauth_code_value() {
        return oauth_code_value;
    }

    public void setOauth_code_value(String oauth_code_value) {
        this.oauth_code_value = oauth_code_value;
    }

    public String getToken_value() {
        return token_value;
    }

    public void setToken_value(String token_value) {
        this.token_value = token_value;
    }

    public String getRedirect_uri_value() {
        return redirect_uri_value;
    }

    public void setRedirect_uri_value(String redirect_uri_value) {
        this.redirect_uri_value = redirect_uri_value;
    }

    public String getRedirect_uri_key() {
        return redirect_uri_key;
    }

    public void setRedirect_uri_key(String redirect_uri_key) {
        this.redirect_uri_key = redirect_uri_key;
    }

    public HashMap<String, String> getToken_other_params() {
        return token_other_params;
    }

    public void setToken_other_params(HashMap<String, String> token_other_params) {
        this.token_other_params = token_other_params;
    }

    public String getOauth_static_value() {
        return oauth_static_value;
    }

    public void setOauth_static_value(String oauth_static_value) {
        this.oauth_static_value = oauth_static_value;
    }

    public String getLoginUrl(){
        StringBuilder sb = new StringBuilder(oauth_url);
        sb.append("?");
        if (client_id_key!=null&&!client_id_key.isEmpty()&&client_id_value!=null&&!client_id_key.isEmpty()){
            sb.append(client_id_key).append("=").append(client_id_value);
        }
        if (redirect_uri_key!=null&&!redirect_uri_key.isEmpty()&&redirect_uri_value!=null&&!redirect_uri_value.isEmpty()){
            sb.append("&").append(redirect_uri_key).append("=").append(redirect_uri_value);
        }
        if (oauth_static_value!=null&&!oauth_static_value.isEmpty()){
            sb.append("&").append(oauth_static_value);
        }
        return sb.toString();
    }

    public Map<String,String> getTokenUrlParams(){
        HashMap<String,String> params = new HashMap<>();
        params.put(getClient_id_key(), getClient_id_value());
        params.put(getClient_secret_key(), getClient_secret_value());
        params.put(getOauth_code_key(), getOauth_code_value());
        if(redirect_uri_key!=null&&!redirect_uri_key.isEmpty()&&redirect_uri_value!=null&&!redirect_uri_value.isEmpty()) {
            params.put(getRedirect_uri_key(), getRedirect_uri_value());
        }
        if(token_other_params!=null&&!token_other_params.isEmpty()){
            params.putAll(token_other_params);
        }
        return params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(web_name);
        parcel.writeString(oauth_url);
        parcel.writeString(token_url);
        parcel.writeString(oauth_static_value);
        parcel.writeString(client_id_key);
        parcel.writeString(client_secret_key);
        parcel.writeString(oauth_code_key);
        parcel.writeString(token_key);
        parcel.writeString(redirect_uri_key);
        parcel.writeString(client_id_value);
        parcel.writeString(client_secret_value);
        parcel.writeString(oauth_code_value);
        parcel.writeString(token_value);
        parcel.writeString(redirect_uri_value);
        parcel.writeMap(token_other_params);
    }


}
