package com.retrofit.write.simple;

import com.retrofit.write.retrofit.Call;
import com.retrofit.write.retrofit.http.GET;
import com.retrofit.write.retrofit.http.Query;

/**
 * Author: 信仰年轻
 * Date: 2021-07-02 15:02
 * Email: hydznsqk@163.com
 * Des:
 */
public interface ServiceApi {

    @GET("login")// 登录接口 GET(相对路径)
    Call<UserLoginResult> userLogin(
            // @Query(后台需要解析的字段)
            @Query("userName") String userName,
            @Query("password") String userPwd);

}