package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Data
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "User.proto", filePackage = "User.proto")
public class SimpleUserFriend implements Serializable {
    /**
     * 申请人Id
     */
    Long userId;

    /**
     *好友id
     */
    Long friendId;
}
