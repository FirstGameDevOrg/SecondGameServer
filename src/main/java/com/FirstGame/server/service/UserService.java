package com.FirstGame.server.service;

import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserFriends;
import com.FirstGame.server.common.BO.UserInRedis;
import com.FirstGame.server.common.BaseResponse;

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
     *
     * @param userName
     * @return
     */
    public BaseResponse selectUserByName(String userName);

    /**
     * 设置key所对的用户信息
     * @param token
     * @param user
     * @return
     */
    public Boolean setUserInRedis(String token, User user);

    /**
     * 删除旧key
     * 更新key，或者value所对的用户信息
     * @param key
     * @return
     */
    public BaseResponse updateUserInRedis(Long userId);

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
     * @param friendId
     * @return
     */
    public Boolean addUser(Long userId,Long friendId);

    /**
     * 注册用户
     * @param user
     * @return
     */
    public BaseResponse insertUser(User user);

    /**
     * 添加好友
     * @param record
     * @return
     */
    public BaseResponse insertUserFriend(UserFriends record);

    /**
     * 更新好友关系表
     * @param record
     * @return
     */
    public BaseResponse updateByUserIdAndFriendId(UserFriends record);



}
