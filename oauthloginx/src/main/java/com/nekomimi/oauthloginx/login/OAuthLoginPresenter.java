package com.nekomimi.oauthloginx.login;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.nekomimi.oauthloginx.OAuthLoginX;
import com.nekomimi.oauthloginx.bean.OAuthConfig;
import com.nekomimi.oauthloginx.net.HTTP;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/2.
 */
public class OAuthLoginPresenter implements com.nekomimi.oauthloginx.login.OAuthLoginContract.Presenter{
    private static final String TAG = "OAuthLoginPresenter";
    private static final int GET_TOKEN_SUCCESS = 0;
    private com.nekomimi.oauthloginx.login.OAuthLoginContract.View view;

    public OAuthLoginPresenter(com.nekomimi.oauthloginx.login.OAuthLoginContract.View view){
        this.view = view;
    }

    @Override
    public void getToken(OAuthConfig config, String oauthCode) {
        final String url = config.getToken_url();
        config.setOauth_code_value(oauthCode);
        final Map<String,String> params = config.getTokenUrlParams();
        final TokenHandler handler = new TokenHandler(view);
        new Thread(){
            @Override
            public void run() {
                String result = HTTP.httpPost(url,params);
                Log.d(TAG, "thread run: result:" + result);
                Message message = new Message();
                message.what = GET_TOKEN_SUCCESS;
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
        view.closeWeb();
        view.showProgress();
    }

    @Override
    public void destroy() {

    }

    private static String getToken4Html(String html, OAuthConfig config){
        String token = "";
        boolean isJson = false;
        try {
            new JsonParser().parse(html);
            isJson = true;
        }catch (JsonParseException e) {
            isJson = false;
        }
        if (isJson){
            try {
                JSONObject jsonObject = new JSONObject(html);
                token = jsonObject.optString(config.getToken_key());
                Log.d(TAG, "getToken4Html: token:" + token);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else {
            String tokenKey = OAuthLoginX.getInstance().getConfig().getToken_key();
            Pattern pattern = Pattern.compile(tokenKey+"=(\\w*)&");
            Pattern pattern1 = Pattern.compile(tokenKey+"=(\\w*)");
            Matcher matcher = pattern.matcher(html);
            Matcher matcher1 = pattern1.matcher(html);
            if(matcher.find()){
                token = matcher.group(1);
            }else if(matcher1.find()){
                token = matcher1.group(1);
            }else {
                token = null;
            }
        }

        return token;
    }

    static class TokenHandler extends Handler{
        WeakReference<com.nekomimi.oauthloginx.login.OAuthLoginContract.View> weakReference;

        TokenHandler(com.nekomimi.oauthloginx.login.OAuthLoginContract.View view) {
            weakReference = new WeakReference<>(view);
        }
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: " + msg.toString());
            final com.nekomimi.oauthloginx.login.OAuthLoginContract.View view = weakReference.get();
            if(view == null){
                return;
            }
            view.hideProgress();
            if (msg.what == GET_TOKEN_SUCCESS){
                String tokenOri =  (String) msg.obj;
                String token = getToken4Html(tokenOri, OAuthLoginX.getInstance().getConfig());
                if (token!=null&&!token.equals("")){
                    view.success(token);
                }
            }
        }
    }

}
