package com.FirstGame.server.repository;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

@Service
@Slf4j
public class JedisUtil {
    private static Jedis jedis;
    private static JedisPool jedisPool = null;

    @PostConstruct
    private void init(){
        ResourceBundle bundle = ResourceBundle.getBundle("jedisPool");
        if (bundle == null) {
            throw new IllegalArgumentException(
                    "[jedisPool.properties] is not found!");
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(bundle
                .getString("redis.pool.maxTotal")));
        config.setMaxIdle(Integer.valueOf(bundle
                .getString("redis.pool.maxIdle")));
        config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
        config.setTestOnBorrow(Boolean.valueOf(bundle
                .getString("redis.pool.testOnBorrow")));
        config.setTestOnReturn(Boolean.valueOf(bundle
                .getString("redis.pool.testOnReturn")));
        jedisPool = new JedisPool(config, bundle.getString("redis.ip"),
                Integer.valueOf(bundle.getString("redis.port")));
        // 从池中获取一个Jedis对象
        jedis = jedisPool .getResource();
    }


}
