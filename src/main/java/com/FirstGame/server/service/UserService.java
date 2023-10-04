package com.FirstGame.server.service;

import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserInRedis;

public interface UserService {
    /**
     * 检查注册信息的合法性
     * @param user
     * @return
     */
    public int checkUser(User user);

    /**
     * 校验登录密码
     * 正确以后，储存在内存中
     * @param userName
     * @param password
     * @return
     */
    public int checkPassword(String userName, String password);


    /**
     * 查找用户
     *
     * @param userId
     * @param userName
     * @return
     */
    public UserInRedis searchUser(Long userId, String userName);

    /**
     * 添加好友
     * @param userId
     * @return
     */
    public Boolean addUser(Long userId);
}
