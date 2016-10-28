package com.nekomimi.oauthloginx.config;

import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/14.
 */
public class ManagerMap {
    public final static Map<String, String> managerMap = new ArrayMap<String, String>() {{
        put("BAIDU","com.nekomimi.oauthloginx.managerImpl.BaiduManager");
        put("GITHUB","com.nekomimi.oauthloginx.managerImpl.GithubManager");
    }};
}
