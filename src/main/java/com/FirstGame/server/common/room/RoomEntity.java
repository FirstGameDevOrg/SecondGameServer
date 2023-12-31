package com.FirstGame.server.common.room;

import com.FirstGame.server.common.room.send.MsgSend;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.widget.light.room.AbstractFlowContextSend;
import com.iohao.game.widget.light.room.AbstractRoom;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RoomEntity extends AbstractRoom {
    @Override
    @SuppressWarnings("unchecked")
    protected <T extends AbstractFlowContextSend> T createSend(FlowContext flowContext) {
        return (T) new MsgSend(flowContext);
    }
}
