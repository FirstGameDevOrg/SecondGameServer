package com.FirstGame.server.api;

public interface UserCmd {
    /** 主路由 */
    int cmd = 1;
    /** 子路由 注册 */
    int registerUser = 0;
    /** 子路由 jackson */
    int loginUser = 1;
    /** 子路由 list */
    int list = 2;
}
