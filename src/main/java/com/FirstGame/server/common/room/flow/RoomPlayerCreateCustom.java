package com.FirstGame.server.common.room.flow;


import com.FirstGame.server.common.room.PlayerEntity;

public class RoomPlayerCreateCustom implements com.iohao.game.widget.light.room.flow.RoomPlayerCreateCustom {
    @Override
    @SuppressWarnings("unchecked")
    public PlayerEntity createPlayer() {
        return new PlayerEntity();
    }
}
