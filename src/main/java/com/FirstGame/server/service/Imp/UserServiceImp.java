package com.FirstGame.server.service.Imp;


import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserInRedis;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.common.TransferUtils;
import com.FirstGame.server.repository.JedisClientUtils;
import com.FirstGame.server.repository.UserMapper;
import com.FirstGame.server.service.UserService;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransferUtils transferUtils;

    @Override
    public int checkUser(User user) {
        if( StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassWord()) ){
            return ErrorCode.MISSINFORMATION.getCode();
        }
        String userName = user.getUserName();
        User user1 =userMapper.selectByIdOrName(null,userName);
        if( user1 != null ){
            return ErrorCode.DUPLICATEUSERNAME.getCode();
        }
        return ErrorCode.SUCCESS.getCode();
    }

    @Override
    public int checkPassword(String userName,String password) {
        if( StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            return ErrorCode.MISSINFORMATION.getCode();
        }
        User user =userMapper.selectByIdOrName(null,userName);
        if( user == null ){
            return ErrorCode.USERNOTEXIST.getCode();
        }
        if( !user.getPassWord().equals(password) ){
            return ErrorCode.INCORRECTPASSWORD.getCode();
        }
        UserInRedis userInRedis = transferUtils.convertToUserInRedis(user);
        userInRedis.setOnline(true);
        //过期时间为120秒
        JedisClientUtils.setex(String.valueOf(userInRedis.getUserId()),
                JSON.toJSON(userInRedis).toString(),120 );
        return ErrorCode.SUCCESS.getCode();
    }


    @Override
    public UserInRedis searchUser(Long userId, String userName) {
        UserInRedis userInRedis = null;
        if( userId != null && JedisClientUtils.exists(String.valueOf(userId))){
            String json = JedisClientUtils.get(String.valueOf(userId));
            userInRedis = JSON.parseObject(json,UserInRedis.class);
        }else{
            User user = userMapper.selectByIdOrName(userId,userName);
            if( user == null ){
                return null;
            }
            userInRedis = transferUtils.convertToUserInRedis(user);
            userInRedis.setOnline(false);
            JedisClientUtils.setex(String.valueOf(userInRedis.getUserId()),
                    JSON.toJSON(userInRedis).toString(),120 );
        }
        log.info("UserInRedis searchUser result : {}",JSON.toJSON(userInRedis));
        return userInRedis;
    }

    @Override
    public Boolean addUser(Long userId) {
        return false;
    }
}
