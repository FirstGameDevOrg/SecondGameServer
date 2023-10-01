package com.FirstGame.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);


		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(10L);
			} catch (InterruptedException e) {
				log.error(StringUtils.EMPTY, e);
			}
			// 在这里执行需要的清理和关闭操作
			// 例如，关闭数据库连接、释放资源等
			System.out.println("Shutting down gracefully...");
			context.close();
		}));

	}

}
