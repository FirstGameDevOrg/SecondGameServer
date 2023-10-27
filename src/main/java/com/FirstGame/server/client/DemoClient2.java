package com.FirstGame.server.client;

import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;

import java.util.List;

public class DemoClient2 {
    public static void main(String[] args) {

        // 关闭模拟请求相关日志
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new UserRegion2(),
                new RoomRegion2()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                .startup();

    }
}
