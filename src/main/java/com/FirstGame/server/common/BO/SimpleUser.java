package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@ProtobufClass
@Data
public class SimpleUser implements Serializable {
    private Long userId;
    private String userName;
}
