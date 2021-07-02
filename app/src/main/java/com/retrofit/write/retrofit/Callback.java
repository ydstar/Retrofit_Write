package com.retrofit.write.retrofit;

/**
 * Author: 信仰年轻
 * Date: 2021-06-30 18:30
 * Email: hydznsqk@163.com
 * Des:
 */
public interface Callback<T> {

    void onResponse(Call<T> call, Response<T> response);

    void onFailure(Call<T> call, Throwable t);
}
