package com.FirstGame.server.api;

public interface UserCmd {
    /** 主路由 */
    int cmd = 1;
    /** 子路由 */
    int registerUser = 0;

    int loginUser = 1;

    int searchUser = 2;

    int addUser = 3;

    int broadcastMsg = 4;

    int agreeAddUser = 5;


}
