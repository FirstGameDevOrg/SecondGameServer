package com.FirstGame.server.client;

import com.FirstGame.server.action.room.RoomCmd;
import com.FirstGame.server.action.user.UserCmd;
import com.FirstGame.server.common.BO.RoomMsg;
import com.FirstGame.server.common.BO.SimpleUserFriend;
import com.FirstGame.server.common.BaseResponse;
import com.alibaba.fastjson2.JSON;
import com.iohao.game.common.kit.log.IoGameLoggerFactory;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import org.slf4j.Logger;

import java.io.IOException;

public class RoomRegion1 extends AbstractInputCommandRegion {
    static final Logger log = IoGameLoggerFactory.getLoggerCommonStdout();


    @Override
    public void initInputCommand() {
        // 模拟请求的主路由
        inputCommandCreate.cmd = RoomCmd.cmd;

        //---------------- 模拟请求 2-0 加入房间------------------------
        RoomMsg roomMsg = new RoomMsg();
        ofCommand(RoomCmd.enterRoom).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1 : enterRoom value : {}", value);
        }).setDescription("enterRoom").setRequestData(roomMsg);


        // ---------------- 模拟请求 2-1 结束回合上传游戏数据 ---------------
        ofCommand(RoomCmd.endRound).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1----endRound value : {}", value);
        }).setDescription("endRound").setRequestData(new RoomMsg(null,null));



        // ---------------- 模拟请求 2-2 邀请好友 ---------------
        SimpleUserFriend userFriends = new SimpleUserFriend();
        userFriends.setUserId(6L);
        userFriends.setFriendId(7L);
        ofCommand(RoomCmd.invitePlayer).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1----invitePlayer value : {}", value);
        }).setDescription("invitePlayer").setRequestData(userFriends);

        //---------------- 接收数据 ---------------------------
        listenBroadcast(RoomMsg.class, result -> {
            RoomMsg value = result.getValue();
            byte[] data = value.getData();
            GameMsg msg = null;
            try {
                msg = ProtobufHelper.simpleTypeCodec.decode(data);
                log.info("client2 gameMsg ========== \n{}", JSON.toJSON(msg));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, RoomCmd.endRound, "游戏数据");


    }
}
