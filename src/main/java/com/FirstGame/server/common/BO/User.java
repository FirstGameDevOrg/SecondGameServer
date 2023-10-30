package com.FirstGame.server.common.BO;


import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Data
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "User.proto", filePackage = "User.proto")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 用户id
     */
    Long userId;
    /**
     * 用户名
     */
    String userName;
    /**
     * 密码
     */
    String passWord;
    /**
     * 性别 0:女 1:男
     */
    Integer sex;
    /**
     * 邮箱地址
     */
    String emailAddress;
    /**
     * 手机号码
     */
    String phone;
}
