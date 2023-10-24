package com.FirstGame.server.common.BO;


import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ProtobufClass
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Long userId;
    private String userName;
    private String passWord;
    //0:女 1:男
    private Integer sex;
    private String emailAddress;
    private String phone;
}
