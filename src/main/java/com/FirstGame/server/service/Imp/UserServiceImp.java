package com.FirstGame.server.service.Imp;


import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserInRedis;
import com.FirstGame.server.common.BaseResponse;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.common.Token;
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

    @Autowired
    private Token token;

    @Override
    public int checkUser(User user) {
        if( StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassWord()) ){
            return ErrorCode.MISSUSERINFO.getCode();
        }
        String userName = user.getUserName();
        User user1 =userMapper.selectByIdOrName(null,userName);
        if( user1 != null ){
            return ErrorCode.DUPLICATEUSERNAME.getCode();
        }
        return ErrorCode.SUCCESS.getCode();
    }

    @Override
    public BaseResponse selectUserByName(String userName) {
        if( StringUtils.isEmpty(userName) ){
            return BaseResponse.fail(ErrorCode.MISSUSERINFO.getCode(),ErrorCode.MISSUSERINFO.getMsg());
        }
        User user =userMapper.selectByIdOrName(null,userName);
        if( user == null ){
            return BaseResponse.fail(ErrorCode.USERNOTEXIST.getCode(),ErrorCode.USERNOTEXIST.getMsg());
        }
        return BaseResponse.success(JSON.toJSONString(user));
    }

    @Override
    public Boolean setUserInRedis(String key,User user) {
        UserInRedis userInRedis = transferUtils.convertToUserInRedis(user);
        //过期时间为一天
        String res = JedisClientUtils.setex(key, JSON.toJSON(userInRedis).toString(),24*60*60 );
        return res != null;

    }

    @Override
    public BaseResponse updateUserInRedis(String key) {
        String newKey = token.updateToken(key);
        String value = JedisClientUtils.get(key);
        String res = JedisClientUtils.setex(newKey, value,24*60*60 );
        Long res1 = JedisClientUtils.del(key);
        log.info("updateUserInRedis new key: {}  value: {} res : {} res1 : {} ",newKey,value,res,res1);
        if( res != null && res1.equals(1L) ){
            return BaseResponse.success(value);
        }
        return BaseResponse.fail();
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
    public Boolean addUser(Long userId, Long friendId) {
        if( friendId == null ){
            return false;
        }
        User user = userMapper.selectByIdOrName(userId,null);

        return false;
    }

    @Override
    public BaseResponse insertUser(User user) {
        int res = userMapper.insertUser(user);
        if( res == 0 ){
            return BaseResponse.fail();
        }
        return BaseResponse.success();
    }
}
