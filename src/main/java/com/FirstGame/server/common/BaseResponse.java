package com.FirstGame.server.common;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
@ProtoFileMerge(fileName = "User.proto", filePackage = "User.proto")
public class BaseResponse implements Serializable {
    /**
     * 是否成功
     */
    Boolean success;
    /**
     * 错误码
     */
    Integer code;
    /**
     * 解释信息
     */
    String msg;
    /**
     * 数据
     */
    String data;

    public Boolean isSuccess() {
        return success;
    }

    public BaseResponse(Integer code, String msg, String data, boolean isSuccess) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = isSuccess;
    }

    public BaseResponse(){}

    public static  BaseResponse success() {
        return new BaseResponse(null,"success",null,true);
    }
    public static  BaseResponse success(int code,String msg) {
        return new BaseResponse(code ,msg, null,true);
    }

    public static  BaseResponse success(int code,String msg,String data) {
        return new BaseResponse(code ,msg, data,true);
    }

    public static  BaseResponse success(String data) {
        return new BaseResponse(null ,null, data,true);
    }

    public static  BaseResponse fail() {
        return new BaseResponse(null, "fail", null,false);
    }

    public static  BaseResponse fail(int code, String msg) {
        return new BaseResponse(code, msg, null,false);
    }

    public static  BaseResponse fail(String data) {
        return new BaseResponse(null, null, data,false);
    }

    public static class Builder {
        private int code;
        private String msg;
        private String data;
        private Boolean success;

        // 设置 code 属性的方法
        public Builder code(int code) {
            this.code = code;
            return this;
        }

        // 设置 msg 属性的方法
        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        // 设置 data 属性的方法
        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }

        // 构建 BaseResponse 对象的方法
        public BaseResponse build() {
            return new BaseResponse(code, msg, data, success);
        }

    }

}
