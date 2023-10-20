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
import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserLogin;
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
        request.setUserName("IoGame1");
        request.setPassWord("IoGame1");
        request.setSex(1);

        // ---------------- 模拟请求 1-0 ----------------
        ofCommand(UserCmd.registerUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1 : registerUser value : {}", value);
        }).setDescription("register").setRequestData(request);


        // ---------------- 模拟请求 1-1 ----------------
        UserLogin userLogin = new UserLogin();
        userLogin.setUserName("IoGame");
        userLogin.setPassWord("IoGame");

        ofCommand(UserCmd.loginUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1 : loginUser value : {}", value);
        }).setDescription("loginUser").setRequestData(userLogin);

    }
}