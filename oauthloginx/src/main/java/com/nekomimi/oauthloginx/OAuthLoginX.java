package com.nekomimi.oauthloginx;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.nekomimi.oauthloginx.bean.OAuthConfig;
import com.nekomimi.oauthloginx.login.OAuthLoginActivity;
import com.nekomimi.oauthloginx.config.ManagerMap;

/**
 * Created by Administrator on 2016/9/1.
 */
public class OAuthLoginX {

    private static final String TAG = "OAuthLoginX";
    public static final String TOKEN = "TOKEN";

    private Activity contextActivity;
    TokenBroadcastReceive broadcastReceive;

    public interface OnTokenCallback{
        void onTokenGet(OAuthManager manager);
    }

    private OAuthLoginX(){}

    private static OAuthLoginX instance;

    public static OAuthLoginX getInstance(){
        if(instance == null){
            instance = new OAuthLoginX();
        }
        return instance;
    }

    private OAuthConfig config;
    private LoginListener loginListener;
    private OAuthManager oAuthManager;


    public void startLogin(Activity activity, OAuthConfig config, LoginListener listener){
        contextActivity = activity;
        setConfig(config);
        setLoginListener(listener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TOKEN);
        broadcastReceive = new TokenBroadcastReceive();
        contextActivity.registerReceiver(broadcastReceive,intentFilter);
        OAuthLoginActivity.openLogin(activity, config);
    }

    class TokenBroadcastReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra(TOKEN);
            contextActivity.unregisterReceiver(broadcastReceive);
            if (TextUtils.isEmpty(token)&&loginListener!=null){
                loginListener.onLoginFail("fail");
            }
            String managerName = ManagerMap.managerMap.get(getConfig().getWeb_name().toUpperCase());
            try {
                oAuthManager = (OAuthManager) Class.forName(managerName).newInstance();
                oAuthManager.setToken(token);
                oAuthManager.setChannel(getConfig().getWeb_name());
            }catch (Exception e){
                e.printStackTrace();
                oAuthManager = new DefaultOAuthManager(token, config.getWeb_name());
            }
            if (loginListener!=null){
                if (token!=null&&!token.isEmpty()){
                    loginListener.onLoginSuccess(oAuthManager);
                }else {
                    loginListener.onLoginFail("fail");
                }
            }


        }
    }

    public OAuthConfig getConfig() {
        return config;
    }

    public void setConfig(OAuthConfig config) {
        this.config = config;
    }

    public LoginListener getLoginListener() {
        return loginListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
}
