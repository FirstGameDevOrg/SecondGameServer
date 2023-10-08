package com.FirstGame.server.common;

import redis.clients.jedis.Response;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200 ,"ok",data);
    }

    public static <T> BaseResponse<T> fail(int code, String msg) {
        return new BaseResponse<>(code, msg, null);
    }
    public static class Builder<T> {
        private int code;
        private String msg;
        private T data;

        // 设置 code 属性的方法
        public Builder<T> code(int code) {
            this.code = code;
            return this;
        }

        // 设置 msg 属性的方法
        public Builder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        // 设置 data 属性的方法
        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        // 构建 BaseResponse 对象的方法
        public BaseResponse<T> build() {
            return new BaseResponse<>(code, msg, data);
        }
    }

}
