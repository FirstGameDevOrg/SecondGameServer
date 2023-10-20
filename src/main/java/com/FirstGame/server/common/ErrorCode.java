package com.FirstGame.server.common;

import com.iohao.game.action.skeleton.core.exception.MsgExceptionInfo;
import lombok.Getter;

@Getter
public enum ErrorCode implements MsgExceptionInfo {
    SUCCESS(1,"成功"),
    DATABASEFAILED(1001,"数据库错误"),
    MISSUSERINFO(1002,"缺失用户名或者密码"),
    DUPLICATEUSERNAME(1003,"用户名重复"),
    USERNOTEXIST(1004,"用户不存在"),
    INCORRECTUSERINFO(1005,"用户名或者密码错误"),
    ACCOUNTONLINE(1006,"账户已经登录在线"),
    ;

    private final int code;
    private final String msg;

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
