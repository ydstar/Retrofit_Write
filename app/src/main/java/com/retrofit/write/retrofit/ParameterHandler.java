package com.retrofit.write.retrofit;

/**
 * Author: 信仰年轻
 * Date: 2021-07-02 14:52
 * Email: hydznsqk@163.com
 * Des:
 */
public interface ParameterHandler<T> {
    void apply(RequestBuilder requestBuilder, T value);


    class Query<T> implements ParameterHandler<T> {

        private String key; // 保存 就是参数的 key = userName ,password

        public Query(String key) {
            this.key = key;
        }

        @Override
        public void apply(RequestBuilder requestBuilder, T value) {
            requestBuilder.addQueryName(key, value.toString());
        }
    }


}
