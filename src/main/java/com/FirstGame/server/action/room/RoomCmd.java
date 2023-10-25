package com.FirstGame.server.action.room;

public interface RoomCmd {
    /** 主路由 */
    int cmd = 2;

    /** 创建并加入房间*/
    int enterRoom = 0;

    /**结束当前回合*/
    int endRound = 1;

}
