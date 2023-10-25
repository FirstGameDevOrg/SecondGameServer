package com.FirstGame.server.common.room.flow;

import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.widget.light.room.CommonRuleInfo;
import com.iohao.game.widget.light.room.RuleInfo;


public class RoomRuleInfoCustom implements com.iohao.game.widget.light.room.flow.RoomRuleInfoCustom {
    static final CommonRuleInfo commonRuleInfo = new CommonRuleInfo();

    @Override
    public RuleInfo getRuleInfo(String ruleInfoJson) throws MsgException {
        // 暂时没什么特殊规则，不做 json 解析，使用默认的空规则
        return commonRuleInfo;
    }
}
