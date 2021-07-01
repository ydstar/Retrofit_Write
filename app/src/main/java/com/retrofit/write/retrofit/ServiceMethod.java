package com.retrofit.write.retrofit;

import java.lang.reflect.Method;

/**
 * Author: 信仰年轻
 * Date: 2021-07-01 18:49
 * Email: houyadong@zhufaner.com
 * Des:
 */
public class ServiceMethod {


    public static class Builder {


        public Builder(Retrofit retrofit, Method method) {

        }

        public ServiceMethod build() {
            return new ServiceMethod();
        }
    }
}
