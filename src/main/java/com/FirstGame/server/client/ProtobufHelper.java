package com.FirstGame.server.client;

import com.alibaba.fastjson2.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ProtobufHelper {
    static Codec<GameMsg> simpleTypeCodec = ProtobufProxy.create(GameMsg.class);

    static int send = 0;
    static String[] strs = new String[]{
            "山外青山楼外楼",
            "一帘幽梦五更寒",
            "云鬓花颜金步摇",
            "芙蓉帐暖度春宵",
            "但见悲鸟号古木",
            "雄风雨雪号长云",
            "平生天地人",
            "金戈铁马气",
            "不相朝夕星",
            "随风翻滚轻尘",
            "世界清澈皎洁",
            "心灵远方永寂",
            "风起红旗恋旧梦",
            "红尘盛世魅多情",
            "桃李春风一杯酒",
            "江水鸥鸟共长天",
            "东篱把酒黄昏后",
            "有暗香盈袖",
            "莫让白头空对月",
            "洒下珠帘酒滴",
            "且将红袖斜飘",
            "更待薄罗静理",
            "洗去香肌犹怯",
            "正暇鸭头罗带",
            "轻盈蝴蝶愁眉",
            "好雨知时节",
            "当春乃发生",
            "随风潜入夜",
            "润物细无声",
            "微微晨曦暖窗帘",
            "一蓬花雪落江南",
            "桃花扇底翩翩舞",
            "两袖星河吹到山",
            "相见时难别亦难",
            "东风无力百花残",
            "万里悲秋常作客",
            "百年多病独登台",
            "谁负谁胜无人管",
            "前度红尘随风飞",
            "不似红楼梦中人",
            "河畔青芜更胜红",
            "不用鞭策走白马",
            "庐山谁不见",
            "数百猿相啼",
            "九折清泉入碧山",
            "万丈飞泉飘玉树",
            "我自岿然不动地",
            "秋水共长天一色",
            "黄河之水天上来",
            "洛阳亲友如相问",
            "一片冰心在玉壶"
    };


    public static byte[] getStr() throws IOException {
        String str = strs[(int)(Math.random()*1000) % strs.length];
        GameMsg msg = new GameMsg(send++,str);
        return simpleTypeCodec.encode(msg);
    }

    public static void main(String[] args) throws IOException {
        byte[] bytes = getStr();
        log.info("bytes : [{}]" ,JSON.toJSONString(bytes));
        GameMsg msg = simpleTypeCodec.decode(bytes);
        log.info("msg : {}",JSON.toJSON(msg));

    }


}
