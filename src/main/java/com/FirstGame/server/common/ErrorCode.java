package com.FirstGame.server.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(1,"成功"),
    DATABASEFAILED(1000,"数据库错误"),
    MISSINFORMATION(1001,"缺失用户名或者密码"),
    DUPLICATEUSERNAME(1002,"用户名重复"),
    USERNOTEXIST(1003,"用户不存在"),
    INCORRECTPASSWORD(1004,"密码错误"),
    ;

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
