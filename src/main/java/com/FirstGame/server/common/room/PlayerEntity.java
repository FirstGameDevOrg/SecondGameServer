package com.FirstGame.server.common.room;

import com.iohao.game.widget.light.room.AbstractPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;

@Getter
@Setter
@Accessors(chain = true)
public class PlayerEntity extends AbstractPlayer {
    @Serial
    private static final long serialVersionUID = 774219456031784563L;

    /** 房间 id */
    long roomId;
}
