package com.retrofit.write.retrofit;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Author: 信仰年轻
 * Date: 2021-07-02 14:53
 * Email: hydznsqk@163.com
 * Des:
 */
public class RequestBuilder {
    ParameterHandler<Object>[] mParameterHandlers;
    Object[] args;
    HttpUrl.Builder httpUrl;

    public RequestBuilder(String baseUrl, String relativeUrl, String httpMethod, ParameterHandler[] parameterHandlers, Object[] args) {
        this.mParameterHandlers = (ParameterHandler<Object>[]) parameterHandlers;
        this.args = args;
        this.httpUrl = HttpUrl.parse(baseUrl+relativeUrl).newBuilder();
    }

    public Request build() {
        int count = args.length;
        for (int i=0;i < count;i++) {
            // userName = yd
            mParameterHandlers[i].apply(this,args[i]);
        }
        // POST 等等
        Request request = new Request
                .Builder()
                .url(httpUrl.build())
                .build();
        return request;
    }

    //https://www.fastmock.site/mock/b5b5b4f8bf5a7178e46771346c7940ca/YdHttpServer/login?userName=yd&password=123456
    public void addQueryName(String key, String value) {
        // userName = yd&password = 123456
        httpUrl.addQueryParameter(key,value);
    }

}
