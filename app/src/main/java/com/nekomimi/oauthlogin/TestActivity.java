package com.nekomimi.oauthlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekomimi.oauthloginx.LoginListener;
import com.nekomimi.oauthloginx.OAuthLoginX;
import com.nekomimi.oauthloginx.OAuthManager;
import com.nekomimi.oauthloginx.bean.OAuthConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/9/1.
 *
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginBtn;
    private TextView tokenTv;
    private Button usernameBtn;
    private TextView usernameTv;
    private Button avaBtn;
    private ImageView avaImg;
    private OAuthManager oAuth;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        loginBtn = (Button)findViewById(R.id.btn_login);
        tokenTv = (TextView)findViewById(R.id.tv_token);
        usernameBtn = (Button)findViewById(R.id.btn_username);
        usernameTv = (TextView)findViewById(R.id.tv_username);
        avaBtn = (Button)findViewById(R.id.btn_ava);
        avaImg = (ImageView)findViewById(R.id.img_ava);
        avaBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        usernameBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                OAuthConfig config = OAuthConfig.parse4Config(this,"github");

                OAuthLoginX.getInstance().startLogin(this, config, new LoginListener() {
                    @Override
                    public void onLoginSuccess(OAuthManager oAuthManager) {
                        oAuth = oAuthManager;
                        tokenTv.setText("Token:"+oAuthManager.getToken());
                    }

                    @Override
                    public void onLoginFail(String result) {
                        tokenTv.setText(result);
                    }
                });
                break;
            case R.id.btn_username:
                oAuth.getUsername(new OAuthManager.OnGetCallback<String>() {
                    @Override
                    public void onGet(String s) {
                        usernameTv.setText(s);
                    }
                });
                break;
            case R.id.btn_ava:
                oAuth.getUserAva(new OAuthManager.OnGetCallback<String>() {
                    @Override
                    public void onGet(String s) {
                        ImageLoader.getInstance().displayImage(s, avaImg);
                    }
                });
                break;
            default:
                break;
        }
    }
}
