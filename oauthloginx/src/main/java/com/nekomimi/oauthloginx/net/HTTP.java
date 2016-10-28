package com.nekomimi.oauthloginx.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/2.
 */
public class HTTP {
    private static final String TAG = "HTTP";
    public static String httpGet(String url){
        StringBuffer result = new StringBuffer();
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            URL httpUrl = new URL(url);
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String b = "";
            while ((b = br.readLine()) != null){
                result.append(b).append("\n");
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (connection != null){
                connection.disconnect();
            }
        }
        return result.toString();
    }


    public static String httpPost(String url, Map<String,String> params){
        StringBuilder result = new StringBuilder();
        InputStream is = null;
        StringBuilder paramsStr = new StringBuilder();
        for (Map.Entry<String, String> entry:params.entrySet()){
            paramsStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (!paramsStr.toString().isEmpty()||!paramsStr.toString().equals("")){
            paramsStr.deleteCharAt(paramsStr.length()-1);
        }
        Log.d(TAG, "httpPost: params: "+paramsStr.toString());
        HttpURLConnection connection = null;
        try {
            URL httpUrl = new URL(url);
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            connection.getOutputStream().write(paramsStr.toString().getBytes());
            is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String b = "";
            while ((b = br.readLine()) != null){
                result.append(b).append("\n");
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (connection != null){
                connection.disconnect();
            }
        }
        return result.toString();
    }
}
