package com.FirstGame.server.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * create 2023/10/9 17:21
 */
@Slf4j
@Component
public class NettyServerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final NettyServer nettyServer;
    @Autowired
    public NettyServerApplicationListener(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            nettyServer.run();
        } catch (Exception e) {
            log.error("NettyServerApplicationListener onApplicationEvent", e);
        } // 在Spring应用程序上下文刷新后启动Netty服务器
    }



}
