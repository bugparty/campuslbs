package com.ifancc.campus.dao;

/**
 * Created by bowman on 13-12-24.
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ifancc.campus.bean.UserBean;
import com.ifancc.campus.exceptions.WeiboException;
import com.ifancc.campus.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;


import com.ifancc.campus.config.URL;
import com.turbomanage.httpclient.BasicHttpClient;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;

public class OAuthDao {
    String TAG = LogUtils.makeLogTag(OAuthDao.class);

    private String access_token;

    public OAuthDao(String access_token) {

        this.access_token = access_token;
    }

    public UserBean getOAuthUserInfo() throws WeiboException {

        String uidJson = getOAuthUserUIDJsonData();
        String uid = "";

        try {
            JSONObject jsonObject = new JSONObject(uidJson);
            uid = jsonObject.optString("uid");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }


        String url = URL.USER_SHOW;
        BasicHttpClient httpClient= new BasicHttpClient();
        ParameterMap params = httpClient.newParams().add("uid",uid).add("access_token", access_token);
        HttpResponse response = httpClient.get(url, params);

        String result = response.getBodyAsString();


        Gson gson = new Gson();
        UserBean user = new UserBean();
        try {
            user = gson.fromJson(result, UserBean.class);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, result);
        }

        return user;
    }

    private String getOAuthUserUIDJsonData() throws WeiboException {

        String url = URL.UID;
        BasicHttpClient httpClient = new BasicHttpClient();
        ParameterMap params = httpClient.newParams().add("access_token", access_token);
        HttpResponse response = httpClient.get(url, params);
        return response.getBodyAsString();

    }

}
