package com.FirstGame.server.action.room;

import com.FirstGame.server.common.BaseResponse;
import com.FirstGame.server.common.room.PlayerEntity;
import com.FirstGame.server.common.room.RoomEntity;
import com.FirstGame.server.common.room.flow.*;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.widget.light.room.GameFlow;
import com.iohao.game.widget.light.room.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

        // TankRoomBroadcast.me().start();
    }

    @ActionMethod(RoomCmd.enterRoom)
    public BaseResponse enterRoom(FlowContext flowContext,Long roomId){
        // 房间
        RoomEntity room = roomService.getRoom(roomId);

        // 房间不存在，创建一个房间
        if (Objects.isNull(room)) {
            room = gameFlow.createRoom(null);
            roomId = room.getRoomId();
            roomService.addRoom(room);
        }

        long userId = flowContext.getUserId();
        PlayerEntity player = room.getPlayerById(userId);

        // 如果检查是否在房间内
        if (Objects.isNull(player)) {
            // 如果不在房间内先加入房间
            player = gameFlow.createPlayer();
            player.setId(userId);
            player.setRoomId(roomId);

            roomService.addPlayer(room, player);
        }

        return BaseResponse.success(roomId.toString());

    }

    @ActionMethod(RoomCmd.endRound)
    BaseResponse endRound(FlowContext flowContext,byte[] data){
        long userId = flowContext.getUserId();

        RoomEntity room = roomService.getRoomByUserId(userId);

        PlayerEntity player = room.getPlayerById(userId);

        //  广播给其他用户数据
        room.broadcast(flowContext, data);
        return BaseResponse.success();
    }

    /**
     * todo:邀请好友加入房间
     */


}
