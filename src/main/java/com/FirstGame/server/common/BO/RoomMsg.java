package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ProtobufClass
public class RoomMsg implements Serializable {
    private Long roomId;
    private byte[] data;

    public RoomMsg(){}

    public RoomMsg(Long roomId, byte[] data){
        this.roomId = roomId;
        this.data = data;
    }


}
