package com.nekomimi.oauthlogin;

import com.google.gson.JsonParseException;

import org.json.JSONObject;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        String url = "access_token=bb94a924496e5a10c62a3841555350c9ce178a18&scope=&token_type=bearer";
        String code = "";
        String codeKey = "access_token";
//        String codeKey = OAuthLoginX.getInstance().getConfig().getOauth_code_key();
        Pattern pattern = Pattern.compile(codeKey+"=(\\w*)&");
        Pattern pattern1 = Pattern.compile(codeKey+"=\\w*)");
        Matcher matcher = pattern.matcher(url);
        Matcher matcher1 = pattern1.matcher(url);
        if(matcher.find()){
            code = matcher.group(1);
        }else if(matcher1.find()){
            code = matcher1.group();
        }else {
            code = null;
        }
        System.out.print(code);
    }

    @Test
    public void isCorrect() throws Exception {
        String h = "{ when=0 what=0 obj={\"expires_in\":2592000,\"refresh_token\":\"22.ead9bf6eaf31d8e80b864070bc40a298.315360000.1791708071.2416080428-8736956\",\"access_token\":\"21.90b3fb6e725a47dfb83696f1e9ba9177.2592000.1478940071.2416080428-8736956\",\"session_secret\":\"5b934e6dc86bbd6f0fb357e6dfd7f005\",\"session_key\":\"9mnRfQpNsPlpbciJZq\\/34qaoJBTCFUQWYuDMy6mQgdXBcUsxd6RIYhpg+Jx+LMHileo+bjWgD++BpDIe3pvg9bJbtDCnXWB+WA==\",\"scope\":\"basic\"}\n";
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject(h);
            result = jsonObject.optString("access_token");
        } catch (JsonParseException e) {
        }
        System.out.print(result);
    }
}