/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.FirstGame.server.client;


import com.FirstGame.server.api.UserCmd;
import com.FirstGame.server.common.BO.*;
import com.FirstGame.server.common.BaseResponse;
import com.iohao.game.common.kit.log.IoGameLoggerFactory;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import org.slf4j.Logger;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
public class DemoRegion1 extends AbstractInputCommandRegion {
    static final Logger log = IoGameLoggerFactory.getLoggerCommonStdout();

    @Override
    public void initInputCommand() {
        // 模拟请求的主路由
        inputCommandCreate.cmd = UserCmd.cmd;

        // 模拟请求参数
        User request = new User();
        request.setUserName("IoGame");
        request.setPassWord("IoGame");
        request.setSex(1);

        // ---------------- 模拟请求 1-0 注册 ----------------
        ofCommand(UserCmd.registerUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1 : registerUser value : {}", value);
        }).setDescription("register").setRequestData(request);


        // ---------------- 模拟请求 1-1 登录 ----------------
        UserLogin userLogin = new UserLogin();
        userLogin.setUserName("IoGame");
        userLogin.setPassWord("IoGame");

        ofCommand(UserCmd.loginUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1 : loginUser value : {}", value);
        }).setDescription("loginUser").setRequestData(userLogin);

        // ---------------- 模拟请求 1-2 搜索好友 ----------------
        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setUserName("IoGame1");
        simpleUser.setUserId(null);

        ofCommand(UserCmd.searchUser).callback(UserInRedis.class, result -> {
            UserInRedis value = result.getValue();
            log.info("client1 : searchUser value : {}", value);
        }).setDescription("searchUser").setRequestData(simpleUser);

        // ---------------- 模拟请求 1-3 添加好友 ----------------
        SimpleUserFriend userFriends = new SimpleUserFriend();
        userFriends.setUserId(6L);
        userFriends.setFriendId(7L);

        ofCommand(UserCmd.addUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1 : addUser value : {}", value);
        }).setDescription("addUser").setRequestData(userFriends);




    }
}