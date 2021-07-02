package com.retrofit.write.simple;

import android.util.Log;

import com.retrofit.write.retrofit.Retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Author: 信仰年轻
 * Date: 2021-07-02 15:02
 * Email: hydznsqk@163.com
 * Des:
 */
public class RetrofitClient {

    private final static ServiceApi mServiceApi;

    static {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i("TAG", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit
                .Builder()
                // 访问后台接口的主路径
                .baseUrl("https://www.fastmock.site/mock/b5b5b4f8bf5a7178e46771346c7940ca/YdHttpServer/")
                // 添加 OkHttpClient,不添加默认就是 光杆 OkHttpClient
                .client(okHttpClient)
                .build();

        // 创建一个 实例对象
        mServiceApi = retrofit.create(ServiceApi.class);
    }

    public static ServiceApi getServiceApi() {
        return mServiceApi;
    }
}
