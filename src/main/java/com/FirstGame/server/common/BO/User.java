package com.FirstGame.server.common.BO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
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
    private String friends;
}
