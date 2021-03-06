package com.retrofit.write.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;

/**
 * Author: 信仰年轻
 * Date: 2021-07-01 17:44
 * Email: hydznsqk@163.com
 * Des: 1.动态代理
 *      2.解析方法上的注解和解析参数上的注解
 *      3.封装OkHttp请求
 */
public class Retrofit {


    String mBaseUrl;
    okhttp3.Call.Factory mCallFactory;

    private Map<Method, ServiceMethod> serviceMethodMap = new ConcurrentHashMap<>();

    public Retrofit(Builder builder) {
        this.mBaseUrl = builder.baseUrl;
        this.mCallFactory = builder.callFactory;
    }

    /**
     * 1.动态代理
     */
    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //每执行一个方法都会来到这里
                // 判断是不是 Object 的方法
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                //2.解析方法上的注解和解析参数上的注解
                ServiceMethod serviceMethod = loadServiceMethod(method);
                //3.封装OkHttp请求
                OkHttpCall okHttpCall = new OkHttpCall(serviceMethod, args);
                return okHttpCall;
            }
        });
    }

    /**
     * 2.解析方法上的注解和解析参数上的注解
     */
    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod serviceMethod = serviceMethodMap.get(method);
        if (serviceMethod == null) {
            //创建ServiceMethod,把Retrofit和Method都传递进去进行解析
            serviceMethod = new ServiceMethod.Builder(this, method).build();
            serviceMethodMap.put(method, serviceMethod);
        }

        return serviceMethod;
    }

    public static class Builder {
        String baseUrl;
        okhttp3.Call.Factory callFactory;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder client(okhttp3.Call.Factory factory) {
            this.callFactory = factory;
            return this;
        }

        public Retrofit build() {
            if (callFactory == null) {
                callFactory = new OkHttpClient();
            }
            return new Retrofit(this);
        }

    }
}
