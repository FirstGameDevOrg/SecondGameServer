package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

@Data
@ProtobufClass
public class UserLogin {
    private String userName;
    private String passWord;
    private String token;
}
