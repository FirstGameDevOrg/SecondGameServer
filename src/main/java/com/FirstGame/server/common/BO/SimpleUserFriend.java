package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@ProtobufClass
@Data
public class SimpleUserFriend implements Serializable {
    /**
     * 申请人Id
     */
    private Long userId;

    /**
     *
     */
    private Long friendId;
}
