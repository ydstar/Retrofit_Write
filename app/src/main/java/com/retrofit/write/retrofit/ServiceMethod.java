package com.retrofit.write.retrofit;

import com.google.gson.Gson;
import com.retrofit.write.retrofit.http.GET;
import com.retrofit.write.retrofit.http.POST;
import com.retrofit.write.retrofit.http.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Author: 信仰年轻
 * Date: 2021-07-01 18:49
 * Email: hydznsqk@163.com
 * Des: 解析方法上的注解和参数上的注解
 */
public class ServiceMethod {

    private final Retrofit mRetrofit;
    private final Method mMethod;
    private String mHttpMethod; //请求的方式 是GET还是POST
    private String mRelativeUrl;//相对路径
    private final ParameterHandler[] mParameterHandlers;

    public ServiceMethod(Builder builder) {
        this.mRetrofit = builder.mRetrofit;
        this.mMethod = builder.mMethod;
        this.mHttpMethod = builder.mHttpMethod;
        this.mRelativeUrl = builder.mRelativeUrl;
        this.mParameterHandlers = builder.mParameterHandlers;
    }

    /**
     * 创建一个新的Call
     */
    public okhttp3.Call createNewCall(Object[] args) {
        //把基础url,相对url,请求方式(get或post),mParameterHandler和真正的参数传递进去
        RequestBuilder requestBuilder = new RequestBuilder(mRetrofit.mBaseUrl, mRelativeUrl, mHttpMethod, mParameterHandlers, args);
        return mRetrofit.mCallFactory.newCall(requestBuilder.build());
    }

    /**
     * 解析ResponseBody
     */
    public <T> T parseBody(ResponseBody responseBody) {
        // 获取解析类型 T 获取方法返回值的类型
        Type returnType = mMethod.getGenericReturnType();// 返回值对象
        Class <T> dataClass = (Class <T>) ((ParameterizedType) returnType).getActualTypeArguments()[0];
        // 解析工厂去转换
        Gson gson = new Gson();
        T body = gson.fromJson(responseBody.charStream(),dataClass);
        return body;
    }

    public static class Builder {

        private final Retrofit mRetrofit;
        private final Method mMethod;

        private String mHttpMethod; //请求的方式 是GET还是POST
        private String mRelativeUrl;//相对路径

        private final Annotation[] mMethodAnnotations;
        private final Annotation[][] mParameterAnnotations;
        private final ParameterHandler[] mParameterHandlers;

        public Builder(Retrofit retrofit, Method method) {
            this.mRetrofit = retrofit;
            this.mMethod = method;
            //方法注解的数组
            mMethodAnnotations = method.getAnnotations();
            //参数注解的二维数组,为什么是二维数组呢,因为1个参数上有可能会有多个注解,所以每个参数对应一个数组,那多个参数就是二维数组了
            mParameterAnnotations = method.getParameterAnnotations();
            mParameterHandlers = new ParameterHandler[mParameterAnnotations.length];
        }

        public ServiceMethod build() {
            //1.解析方法上的注解
            for(Annotation annotation:mMethodAnnotations){
                parseAnnotationMethod(annotation);
            }
            //2.解析参数注解
            for(int x=0;x<mParameterAnnotations.length;x++){
                Annotation annotation = mParameterAnnotations[x][0];
                //在这里只有Query这个注解
                if(annotation instanceof Query){
                    // 一个一个封装成 ParameterHandler,不同的参数注解选择不同的策略
                    //传进去的就是 参数的 key = userName ,password
                    mParameterHandlers[x]=new ParameterHandler.Query(((Query) annotation).value());
                }
            }
            return new ServiceMethod(this);
        }

        //1.解析方法上的注解
        private void parseAnnotationMethod(Annotation annotation) {
            if(annotation instanceof GET){
                parseMethodAndPath("GET",((GET) annotation).value());
            }else if(annotation instanceof POST){
                parseMethodAndPath("POST",((POST) annotation).value());
            }
            //还有一大堆其他解析...
        }

        private void parseMethodAndPath(String method, String value) {
            this.mHttpMethod =method;
            this.mRelativeUrl =value;
        }
    }
}
