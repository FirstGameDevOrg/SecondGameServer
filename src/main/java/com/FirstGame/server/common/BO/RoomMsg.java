package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "Room.proto", filePackage = "Room.proto")
public class RoomMsg implements Serializable {
    /**
     * 房间id
     */
    Long roomId;
    /**
     * 传输数据
     */
    byte[] data;

    public RoomMsg(){}

    public RoomMsg(Long roomId, byte[] data){
        this.roomId = roomId;
        this.data = data;
    }


}
