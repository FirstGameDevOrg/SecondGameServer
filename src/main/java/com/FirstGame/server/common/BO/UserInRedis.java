package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author Ankle
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "User.proto", filePackage = "User.proto")
public class UserInRedis {
    Long userId;
    String userName;
    //0:女 1:男
    Integer sex;
    String token;
}
