package com.FirstGame.server.client;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ProtobufClass
public class GameMsg implements Serializable {
    private int order;
    private String msg;
}
