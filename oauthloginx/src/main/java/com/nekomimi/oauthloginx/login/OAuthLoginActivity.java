package com.nekomimi.oauthloginx.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.nekomimi.oauthloginx.OAuthLoginX;
import com.nekomimi.oauthloginx.bean.OAuthConfig;
import com.nekomimi.oauthloginx.webview.OAuthWebClient;

/**
 * Created by Administrator on 2016/9/2.
 */
public class OAuthLoginActivity extends AppCompatActivity implements OAuthLoginContract.View{
    private static final String TAG = "OAuthLoginActivity";
    private final static String CONFIG = "config";

    private OAuthConfig oAuthConfig;
    private OAuthWebClient.OAuthCodeListener listener;
    private OAuthLoginContract.Presenter presenter;
    private WebView webView;
    private ProgressDialog progressDialog;
    private LinearLayout root;
    private boolean isSuccess = false;
    public static void openLogin(Activity activity, OAuthConfig config){
        Intent intent = new Intent(activity, OAuthLoginActivity.class);
        intent.putExtra(CONFIG, config);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new LinearLayout(this);
        oAuthConfig = getIntent().getParcelableExtra(CONFIG);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(root, params);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) actionBar.hide();
        presenter = new OAuthLoginPresenter(this);
        listener = new OAuthWebClient.OAuthCodeListener() {
            @Override
            public void onCodeGet(String code) {
                Log.d(TAG, "onCodeGet: " + code);
                presenter.getToken(oAuthConfig, code);
            }
        };
        openWeb();
    }

    @Override
    public void openWeb(){
        webView = new WebView(this);
        OAuthWebClient webClient = new OAuthWebClient(listener);
        webView.setWebViewClient(webClient);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        root.addView(webView,params);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(oAuthConfig.getLoginUrl());
    }

    @Override
    public void success(String token) {
        Intent intent = new Intent(OAuthLoginX.TOKEN);
        intent.putExtra(OAuthLoginX.TOKEN, token);
        sendBroadcast(intent);
        isSuccess = true;
        Log.d(TAG, "success: " + token);
        finish();
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("登录中");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void closeWeb() {
        webView.stopLoading();
        root.removeView(webView);
        webView.destroy();
    }

    @Override
    protected void onDestroy() {
        if (!isSuccess) {
            Intent intent = new Intent(OAuthLoginX.TOKEN);
            intent.putExtra(OAuthLoginX.TOKEN, "");
            sendBroadcast(intent);
        }
        super.onDestroy();
    }
}
