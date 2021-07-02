package com.retrofit.write.retrofit;

/**
 * Author: 信仰年轻
 * Date: 2021-06-30 18:30
 * Email: hydznsqk@163.com
 * Des:
 */
public interface Call<T> {
    void enqueue(Callback<T> callback);
}

