package com.FirstGame.server.client;

import com.FirstGame.server.api.UserCmd;
import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserLogin;
import com.FirstGame.server.common.BaseResponse;
import com.iohao.game.common.kit.log.IoGameLoggerFactory;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import org.slf4j.Logger;


public class DemoRegion2 extends AbstractInputCommandRegion  {
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

        // ---------------- 模拟请求 1-0 ----------------
        ofCommand(UserCmd.registerUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client2 : registerUser value : {}", value);
        }).setDescription("register").setRequestData(request);


        // ---------------- 模拟请求 1-1 ----------------
        UserLogin userLogin = new UserLogin();
        userLogin.setUserName("IoGame");
        userLogin.setPassWord("IoGame");
        userLogin.setToken("D6738CE4932C3471972FE9E5D529744E");

        ofCommand(UserCmd.loginUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client2 : loginUser value : {}", value);
        }).setDescription("loginUser").setRequestData(userLogin);

    }
}
