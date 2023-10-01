package com.FirstGame.server.service;

import com.FirstGame.server.common.BO.User;

public interface UserService {
    /**
     * 检查注册信息的合法性
     * @param user
     * @return
     */
    public int checkUser(User user);
}
