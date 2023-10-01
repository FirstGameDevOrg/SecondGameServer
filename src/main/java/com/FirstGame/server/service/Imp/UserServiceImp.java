package com.FirstGame.server.service.Imp;


import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.repository.UserMapper;
import com.FirstGame.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
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
        return 1;
    }
}
