package com.FirstGame.server.action.room;


import com.FirstGame.server.client.ProtobufHelper;
import com.FirstGame.server.common.BO.RoomMsg;
import com.FirstGame.server.common.BO.SimpleUserFriend;
import com.FirstGame.server.common.BaseResponse;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.common.room.PlayerEntity;
import com.FirstGame.server.common.room.RoomEntity;
import com.FirstGame.server.common.room.flow.*;
import com.alibaba.fastjson2.JSON;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.widget.light.room.GameFlow;
import com.iohao.game.widget.light.room.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Objects;


@Slf4j
@Component
@ActionController(RoomCmd.cmd)
public class RoomAction {

    /** 游戏流程 */
    static GameFlow gameFlow = new GameFlow();
    static RoomService roomService = new RoomService();

    static {
        gameFlow
                // 游戏规则
                .setRoomRuleInfoCustom(new RoomRuleInfoCustom())
                // 游戏开始
                .setRoomGameStartCustom(new RoomGameStartCustom())
                // 创建玩家
                .setRoomPlayerCreateCustom(new RoomPlayerCreateCustom())
                // 房间创建
                .setRoomCreateCustom(new RoomCreateCustom())
                // 进入房间
                .setRoomEnterCustom(new RoomEnterCustom());

    }

    @ActionMethod(RoomCmd.enterRoom)
    public BaseResponse enterRoom(FlowContext flowContext, RoomMsg roomMsg){
        log.info("RoomMsg : {}", JSON.toJSON(roomMsg));

        Long roomId = roomMsg != null ? roomMsg.getRoomId() : null;

        // 房间
        RoomEntity room = roomId != null ? roomService.getRoom(roomId) : null;

        log.info("Room-1 : {}", JSON.toJSON(room));

        // 房间不存在，创建一个房间
        if (Objects.isNull(room)) {
            room = gameFlow.createRoom(null);
            roomId = room.getRoomId();
            roomService.addRoom(room);
        }
        log.info("Room-2 : {}", JSON.toJSON(room));

        long userId = flowContext.getUserId();
        PlayerEntity player = room.getPlayerById(userId);

        log.info("player-1 : {} ",JSON.toJSON(player));
        // 如果检查是否在房间内
        if (Objects.isNull(player)) {
            // 如果不在房间内先加入房间
            player = gameFlow.createPlayer();
            player.setId(userId);
            player.setRoomId(roomId);

            roomService.addPlayer(room, player);
        }
        log.info("player-2 : {} ",JSON.toJSON(player));

        log.info("room-3 : {}", JSON.toJSON(room));


        return BaseResponse.success(roomId.toString());

    }


    @ActionMethod(RoomCmd.endRound)
    public BaseResponse endRound(FlowContext flowContext,RoomMsg roomMsg) throws IOException {
        long userId = flowContext.getUserId();

        RoomEntity room = roomService.getRoomByUserId(userId);

        log.info("endRound room : {}", JSON.toJSON(room));

        //PlayerEntity player = room.getPlayerById(userId);
        if( roomMsg == null){
            return BaseResponse.fail(ErrorCode.NPE.getCode(),"roomMsg"+ErrorCode.NPE.getMsg());
        }
        //  广播给其他用户数据
        //room.broadcast(flowContext, roomMsg, userId);
        //测试用
        room.broadcast(flowContext, new RoomMsg(null, ProtobufHelper.getStr()),userId);
        return BaseResponse.success();
    }

    /**
     * 邀请好友进入房间
     * @param flowContext
     * @param friend
     * @return
     */
    @ActionMethod(RoomCmd.invitePlayer)
    public BaseResponse invitePlayer(FlowContext flowContext, SimpleUserFriend friend){
        log.info("friend : {}",JSON.toJSON(friend));
        RoomEntity room = roomService.getRoomByUserId(friend.getUserId());
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(RoomCmd.cmd, RoomCmd.broadcastMsg);

        // 广播上下文
        BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();

        RoomMsg msg = new RoomMsg(room.getRoomId(), new byte[]{});
        log.info("RoomMsg: {}", msg);
        //将申请者的信息发给好友
        broadcastContext.broadcast(cmdInfo, msg, friend.getFriendId());
        return BaseResponse.success();
    }

}
