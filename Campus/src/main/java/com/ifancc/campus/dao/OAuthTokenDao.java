package com.ifancc.campus.dao;

import com.ifancc.campus.exceptions.WeiboException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ifancc.campus.config.URL;
import com.ifancc.campus.util.HttpBuilder;

/**
 * Created by bowman on 13-12-25.
 */
public class OAuthTokenDao {
    public String[] login() throws WeiboException {
        String url = URL.OAUTH2_ACCESS_TOKEN;
        String jsonData = HttpBuilder.Get(url,"username", username,
                "password", password,
                "client_id", client_id,
                "client_secret", client_secret,
                "grant_type", grant_type);



        if ((jsonData != null) && (jsonData.contains("{"))) {
            try {
                JSONObject localJSONObject = new JSONObject(jsonData);
                String[] result = new String[2];
                result[0] = localJSONObject.optString("access_token");
                result[1] = localJSONObject.optString("expires_in");
                return result;
//            setUserId(localJSONObject.optLong("uid"));

            } catch (JSONException localJSONException) {

            }

        }
        return null;

    }

    public OAuthTokenDao(String username, String password, String key, String secret) {
        this.username = username;
        this.password = password;
        this.client_id = key;
        this.client_secret = secret;
    }

    private String username;
    private String password;
    private String client_id;
    private String client_secret;
    private String grant_type = "password";

}
