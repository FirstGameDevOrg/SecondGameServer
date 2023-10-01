package com.FirstGame.server.common;

import lombok.Getter;
@Getter
public enum ErrorCode {
    MISSINFORMATION(101,"缺失用户名或者密码"),
    DUPLICATEUSERNAME(102,"用户名重复");

    @Getter
    private int code;
    private String msg;
    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code) {
        for (ErrorCode e : ErrorCode.values()) {
            if (e.getCode() == code) {
                return e.getMsg();
            }
        }
        return null;
    }
}
