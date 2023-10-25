package com.FirstGame.server.common.room.flow;

import com.iohao.game.widget.light.room.AbstractRoom;


public class RoomGameStartCustom implements com.iohao.game.widget.light.room.flow.RoomGameStartCustom {
    @Override
    public boolean startBefore(AbstractRoom abstractRoom) {
        return false;
    }

    @Override
    public void startAfter(AbstractRoom abstractRoom) {

    }
}
