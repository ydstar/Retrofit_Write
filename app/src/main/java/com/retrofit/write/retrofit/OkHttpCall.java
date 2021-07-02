package com.retrofit.write.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.Response;

/**
 * Author: 信仰年轻
 * Date: 2021-07-01 18:58
 * Email: hydznsqk@163.com
 * Des:
 */
public class OkHttpCall<T> implements Call<T> {

    private ServiceMethod mServiceMethod;
    private Object[] mArgs;

    public OkHttpCall(ServiceMethod serviceMethod, Object[] args) {
        this.mServiceMethod = serviceMethod;
        this.mArgs = args;
    }

    @Override
    public void enqueue(final Callback<T> callback) {
        // 发起一个请求，给一个回调就完结了
        Log.e("TAG", "正式发起请求");

        okhttp3.Call call = mServiceMethod.createNewCall(mArgs);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(OkHttpCall.this, e);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                //涉及到解析,不能在这里写上,ConvertFactory
                com.retrofit.write.retrofit.Response objectResponse = new com.retrofit.write.retrofit.Response();
                objectResponse.body = mServiceMethod.parseBody(response.body());
                if (callback != null) {
                    callback.onResponse(OkHttpCall.this, objectResponse);
                }
            }
        });
    }
}
