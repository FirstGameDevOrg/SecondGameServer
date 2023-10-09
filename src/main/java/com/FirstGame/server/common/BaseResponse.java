package com.FirstGame.server.common;

import lombok.Data;
import redis.clients.jedis.Response;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private Boolean success;
    private Integer code;
    private String msg;
    private T data;

    public Boolean isSuccess() {
        return success;
    }

    public BaseResponse(Integer code, String msg, T data, boolean isSuccess) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = isSuccess;
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(null,"success",null,true);
    }

    public static <T> BaseResponse<T> fail() {
        return new BaseResponse<>(null, "fail", null,false);
    }

    public static <T> BaseResponse<T> success(int code,String msg) {
        return new BaseResponse<>(code ,msg, null,true);
    }

    public static <T> BaseResponse<T> fail(int code, String msg) {
        return new BaseResponse<>(code, msg, null,false);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(null ,null, data,true);
    }

    public static <T> BaseResponse<T> fail(T data) {
        return new BaseResponse<>(null, null, data,false);
    }

    public static class Builder<T> {
        private int code;
        private String msg;
        private T data;
        private Boolean success;

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

        public Builder<T> isSuccess(Boolean isSuccess) {
            this.success = isSuccess;
            return this;
        }

        // 构建 BaseResponse 对象的方法
        public BaseResponse<T> build() {
            return new BaseResponse<>(code, msg, data, success);
        }

    }

}
