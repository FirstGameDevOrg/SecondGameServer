package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ankle
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ProtobufClass
public class UserInRedis {
    private Long userId;
    private String userName;
    //0:女 1:男
    private Integer sex;
    private String token;
}
