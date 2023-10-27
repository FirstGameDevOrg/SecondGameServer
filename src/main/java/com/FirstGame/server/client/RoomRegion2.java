package com.FirstGame.server.client;

import com.FirstGame.server.action.room.RoomCmd;
import com.FirstGame.server.action.user.UserCmd;
import com.FirstGame.server.common.BO.RoomMsg;
import com.FirstGame.server.common.BaseResponse;
import com.alibaba.fastjson2.JSON;
import com.iohao.game.common.kit.log.IoGameLoggerFactory;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import org.slf4j.Logger;

import java.io.IOException;

public class RoomRegion2 extends AbstractInputCommandRegion {
    static final Logger log = IoGameLoggerFactory.getLoggerCommonStdout();

    static int send = 0;

    @Override
    public void initInputCommand() {
        // 模拟请求的主路由
        inputCommandCreate.cmd = RoomCmd.cmd;

        //---------------- 模拟请求 2-0 加入房间------------------------
        RoomMsg roomMsg = new RoomMsg();
        Long roomId = 1717892800944353280L;
        roomMsg.setRoomId(roomId);
        ofCommand(RoomCmd.enterRoom).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client1----enterRoom value : {}", value);
        }).setDescription("enterRoom").setRequestData(roomMsg);


        // ---------------- 模拟请求 2-1 结束回合上传游戏数据 ---------------
        ofCommand(RoomCmd.endRound).callback(BaseResponse.class, result -> {
            BaseResponse value = result.getValue();
            log.info("client2----endRound value : {}", value);
        }).setDescription("endRound").setRequestData(new RoomMsg(null,null));


        //---------------- 接收数据 ---------------------------
        listenBroadcast(RoomMsg.class, result -> {
            RoomMsg value = result.getValue();
            log.info("client1 gameMsg roomId :{}", JSON.toJSON(value.getRoomId()));
            byte[] data = value.getData();
            log.info("client1 gameMsg data :{}", JSON.toJSON(data));
            GameMsg msg = null;
            try {
                msg = ProtobufHelper.simpleTypeCodec.decode(data);
                log.info("client1 gameMsg ========== {}", JSON.toJSON(msg));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, RoomCmd.endRound, "游戏数据");




    }
}
