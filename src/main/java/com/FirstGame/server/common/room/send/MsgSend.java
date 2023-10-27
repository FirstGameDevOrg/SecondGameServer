/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.FirstGame.server.common.room.send;

import com.FirstGame.server.action.room.RoomCmd;
import com.FirstGame.server.common.BO.RoomMsg;
import com.FirstGame.server.common.BO.SimpleUserFriend;
import com.iohao.game.action.skeleton.annotation.DocActionSend;
import com.iohao.game.action.skeleton.annotation.DocActionSends;
import com.iohao.game.action.skeleton.core.ActionCommand;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.widget.light.room.AbstractFlowContextSend;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashSet;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@DocActionSends({
        //推送相关文档
        @DocActionSend(cmd = RoomCmd.cmd, subCmd = RoomCmd.enterRoom, dataClass = RoomMsg.class),
        @DocActionSend(cmd = RoomCmd.cmd, subCmd = RoomCmd.endRound, dataClass = RoomMsg.class),
        @DocActionSend(cmd = RoomCmd.cmd, subCmd = RoomCmd.invitePlayer, dataClass = SimpleUserFriend.class)
})
public class MsgSend extends AbstractFlowContextSend {

    public MsgSend(FlowContext flowContext) {
        super(flowContext);
    }



    @Override
    public void send() {
        /*
         * 注意，这样重写方法是同步的方式推送消息，
         * 并没有发挥出 light-domain-event 领域事件的能力
         *
         * 如果想发挥出领域事件的能力，可参考文档中的示例
         * https://www.yuque.com/iohao/game/gmfy1k
         *
         * 
         */
        CmdInfo cmdInfo = flowContext.getActionCommand().getCmdInfo();
        ActionCommand actionCommand = flowContext.getActionCommand();


        this.execute();
    }
}
