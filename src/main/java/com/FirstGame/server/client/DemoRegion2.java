package com.FirstGame.server.client;

import com.FirstGame.server.api.UserCmd;
import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserInRedis;
import com.FirstGame.server.common.BO.UserLogin;
import com.FirstGame.server.common.BaseResponse;
import com.alibaba.fastjson2.JSON;
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
        userLogin.setUserName("IoGame1");
        userLogin.setPassWord("IoGame1");


        ofCommand(UserCmd.loginUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client2 : loginUser value : {}", value);
        }).setDescription("loginUser").setRequestData(userLogin);

        //---------------- 广播监听 ---------------------------
        listenBroadcast(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("broadcastMessage ========== \n{}", value);
        }, UserCmd.broadcastMsg, "收到添加好友信息");

        // ---------------- 模拟请求 1-5 同意添加好友 ----------------
        UserInRedis userInRedis = new UserInRedis();
        userInRedis.setUserId(6L);
        userInRedis.setUserName("IoGame");
        BaseResponse isAgree = BaseResponse.success(JSON.toJSONString(userInRedis));

        ofCommand(UserCmd.agreeAddUser).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client2 : agreeAddUser value : {}", value);
        }).setDescription("agreeAddUser").setRequestData(isAgree);



    }
}
