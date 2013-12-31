package com.ifancc.campus.util;

import com.turbomanage.httpclient.BasicHttpClient;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;

/**
 * Created by bowman on 13-12-25.
 */
public class HttpBuilder {
    public static String Get(String url,String ...args ){
        if (args.length % 2 == 1){
            throw new IllegalArgumentException("arguments should be in pairs");
        }
        BasicHttpClient httpClient= new BasicHttpClient();
        ParameterMap params = httpClient.newParams();
        for(int i=0; i< args.length;i+=2){
            params.add(String.format("%s",args[i]),args[i+1]);
        }
        HttpResponse response = httpClient.get(url, params);

        String result = response.getBodyAsString();

        return result;
    }
}
