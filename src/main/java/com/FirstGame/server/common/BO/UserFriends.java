package com.FirstGame.server.common.BO;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@ProtobufClass
@Data
public class UserFriends implements Serializable {
    /**
    * 
    */
    private Integer id;

    /**
    * 申请人Id
    */
    private Long userId;

    /**
    * 
    */
    private Long friendId;

    /**
    * 
    */
    private String friendNickname;

    /**
    * 0 拒绝/删除 1接受
    */
    private Byte status;

    /**
    * 
    */
    private Date createdAt;

    /**
    * 
    */
    private Date updatedAt;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public void setFriendNickname(String friendNickname) {
        this.friendNickname = friendNickname;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}