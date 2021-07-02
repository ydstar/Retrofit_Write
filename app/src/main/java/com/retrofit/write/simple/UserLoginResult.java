package com.retrofit.write.simple;


/**
 * Author: 信仰年轻
 * Date: 2021-07-02 15:02
 * Email: hydznsqk@163.com
 * Des:
 */
public class UserLoginResult {
    /**
     * code : 200
     * data : {"name":"yadong","age":29}
     * msg : 成功
     */

    private String code;
    private DataBean data;
    private String msg;


    public static class DataBean {
        /**
         * name : yadong
         * age : 29
         */

        private String name;
        private int age;

        @Override
        public String toString() {
            return "DataBean{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserLoginResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
