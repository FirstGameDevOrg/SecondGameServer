package com.FirstGame.server;

import com.FirstGame.server.logic.HallLogicServer;
import com.FirstGame.server.repository.JedisUtils;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);

		// 游戏对外服端口
		int port = 10100;
		// 逻辑服
		var hallLogicServer = new HallLogicServer();
		// 启动游戏对外服、Broker（游戏网关）、游戏逻辑服
		// 这三部分在一个进程中相互使用内存通信
		NettySimpleHelper.run(port, List.of(hallLogicServer));



		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(10L);
			} catch (InterruptedException e) {
				log.error(StringUtils.EMPTY, e);
			}
			// 在这里执行需要的清理和关闭操作
			// 例如，关闭数据库连接、释放资源等
			//释放redis连接池
			JedisUtils.destroyPool();
			System.out.println("Shutting down gracefully...");
			context.close();
		}));

	}

}
