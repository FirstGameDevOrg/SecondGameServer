package com.FirstGame.server.common.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ankle
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInRedis {
    private Long userId;
    private String userName;
    //0:女 1:男
    private Integer sex;
    private String friends;
    private Boolean online;
}
