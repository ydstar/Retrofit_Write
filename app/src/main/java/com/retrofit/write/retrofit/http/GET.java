package com.retrofit.write.retrofit.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: 信仰年轻
 * Date: 2021-06-30 18:31
 * Email: hydznsqk@163.com
 * Des:定义在方法上的
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface GET {
    String value();
}
