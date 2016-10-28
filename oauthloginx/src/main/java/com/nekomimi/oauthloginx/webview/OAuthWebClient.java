package com.nekomimi.oauthloginx.webview;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nekomimi.oauthloginx.OAuthLoginX;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/1.
 */
public class OAuthWebClient extends WebViewClient {

    private static final String TAG = "OAuthWebClient";
    private OAuthCodeListener listener;
    public OAuthWebClient(OAuthCodeListener listener){
        this.listener = listener;
    }


    public interface OAuthCodeListener{
        void onCodeGet(String code);
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String code = findCode(url);
        if(code==null){
            return false;
        }else {
            listener.onCodeGet(code);
            return true;
        }
    }

    @TargetApi(21)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        return super.shouldOverrideUrlLoading(view, request);
        String url = request.getUrl().toString();
        String code = findCode(url);
        if(code==null){
            return false;
        }else {
            listener.onCodeGet(code);
            return true;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d(TAG, "onPageStarted: " + url);
    }

    private String findCode(String url){
        String code;
        String codeKey = OAuthLoginX.getInstance().getConfig().getOauth_code_key();
        Pattern pattern = Pattern.compile(codeKey+"=(\\w*)&");
        Pattern pattern1 = Pattern.compile(codeKey+"=(\\w*)");
        Matcher matcher = pattern.matcher(url);
        Matcher matcher1 = pattern1.matcher(url);
        if(matcher.find()){
            code = matcher.group(1);
        }else if(matcher1.find()){
            code = matcher1.group(1);
        }else {
            code = null;
        }
        return code;
    }
}