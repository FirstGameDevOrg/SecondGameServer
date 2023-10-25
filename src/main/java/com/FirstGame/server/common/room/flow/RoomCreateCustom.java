package com.FirstGame.server.common.room.flow;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.FirstGame.server.common.room.RoomEntity;
import com.iohao.game.widget.light.room.AbstractRoom;
import com.iohao.game.widget.light.room.CreateRoomInfo;

public class RoomCreateCustom implements com.iohao.game.widget.light.room.flow.RoomCreateCustom {
    Snowflake snowflake = IdUtil.getSnowflake();

    @Override
    @SuppressWarnings("unchecked")
    public AbstractRoom createRoom(CreateRoomInfo createRoomInfo) {
        long roomId = snowflake.nextId();

        RoomEntity room = new RoomEntity();
        room.setRoomId(roomId);

        return room;
    }
}
