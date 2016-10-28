package com.nekomimi.oauthloginx.managerImpl;

import android.os.AsyncTask;
import android.util.Log;

import com.nekomimi.oauthloginx.OAuthManager;
import com.nekomimi.oauthloginx.bean.User;
import com.nekomimi.oauthloginx.net.HTTP;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/14.
 */
public class BaiduManager extends OAuthManager{
    private static final String TAG = "BaiduManager";

    private static final String API_URL_USER = "https://openapi.baidu.com/rest/2.0/passport/users/getLoggedInUser";
    private static final String AVA_URL = "http://tb.himg.baidu.com/sys/portrait/item/";
    private static final String USERNAME_KEY = "uname";
    private static final String AVA_KEY = "portrait";
    private static final String UID_KEY = "uid";
    private static final String HOMEPAGE_KEY = "html_url";

    @Override
    public void getUId(OnGetCallback<String> listener) {
        new UserIDAst(listener).execute(API_URL_USER+"?access_token="+getToken());
    }

    @Override
    public void getUsername(OnGetCallback<String> listener) {
        new UsernameAst(listener).execute(API_URL_USER+"?access_token="+getToken());
    }

    @Override
    public void getUserAva(OnGetCallback<String> listener) {
        new AvaAst(listener).execute(API_URL_USER+"?access_token="+getToken());
    }

    @Override
    public void getUser(OnGetCallback<User> listener) {
        new UserAst(listener).execute(API_URL_USER+"?access_token="+getToken());
    }

    class UsernameAst extends AsyncTask<String,Void,String> {
        OnGetCallback<String> listener;
        UsernameAst(OnGetCallback<String> listener){
            this.listener = listener;
        }
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: " + strings[0]);
            return HTTP.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: " + s);
            String username = "";
            try {
                JSONObject jsonObject = new JSONObject(s);
                username = jsonObject.optString(USERNAME_KEY);
            }catch (JSONException e){
                e.printStackTrace();
            }
            listener.onGet(username);
        }
    }

    class AvaAst extends AsyncTask<String,Void,String>{
        OnGetCallback<String> listener;
        AvaAst(OnGetCallback<String> listener){
            this.listener = listener;
        }
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: " + strings[0]);
            return HTTP.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: " + s);
            String ava = "";
            try {
                JSONObject jsonObject = new JSONObject(s);
                ava = jsonObject.optString(AVA_KEY);
                ava = AVA_URL + ava;
            }catch (JSONException e){
                e.printStackTrace();
            }
            listener.onGet(ava);
        }
    }

    class UserIDAst extends AsyncTask<String,Void,String>{
        OnGetCallback<String> listener;
        UserIDAst(OnGetCallback<String> listener){
            this.listener = listener;
        }
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: " + strings[0]);
            return HTTP.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: " + s);
            String uid = "";
            try {
                JSONObject jsonObject = new JSONObject(s);
                uid = jsonObject.optString(UID_KEY);
            }catch (JSONException e){
                e.printStackTrace();
            }
            listener.onGet(uid);
        }
    }

    class UserAst extends AsyncTask<String,Void,String>{
        OnGetCallback<User> listener;
        UserAst(OnGetCallback<User> listener){
            this.listener = listener;
        }
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: " + strings[0]);
            return HTTP.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: " + s);
            User user = new User();
            try {
                JSONObject jsonObject = new JSONObject(s);
                user.setUid(jsonObject.optString(UID_KEY));
                user.setUsername(jsonObject.optString(USERNAME_KEY));
                user.setAva_url(AVA_URL + jsonObject.optString(AVA_KEY));
            }catch (JSONException e){
                e.printStackTrace();
            }
            listener.onGet(user);
        }
    }
}
