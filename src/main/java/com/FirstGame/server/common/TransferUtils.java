package com.FirstGame.server.common;

import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserInRedis;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 */
@Slf4j
@Service
public class TransferUtils {

    public UserInRedis convertToUserInRedis(User user){
        UserInRedis userInRedis = new UserInRedis();
        userInRedis.setUserId(user.getUserId() == null ? null : user.getUserId());
        userInRedis.setUserName(user.getUserName());
        userInRedis.setSex(user.getSex() == null ? null : user.getSex());
        userInRedis.setFriends(user.getFriends() != null ? user.getFriends() : "");
        log.info("convertToUserInRedis Before User : {}, After UserInRedis : {} ", JSON.toJSON(user),JSON.toJSON(userInRedis));
        return userInRedis;
    }
}
