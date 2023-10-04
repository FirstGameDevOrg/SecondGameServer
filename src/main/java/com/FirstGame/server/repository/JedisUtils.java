package com.FirstGame.server.repository;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.ResourceBundle;

/**
 * @author Ankle
 */
@Service
@Slf4j
public class JedisUtils {
    private static JedisPool jedisPool = null;

    @PostConstruct
    private void init(){
        ResourceBundle bundle = ResourceBundle.getBundle("jedisPool");
        if (bundle == null) {
            throw new IllegalArgumentException(
                    "[jedisPool.properties] is not found!");
        }
        try{
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.parseInt(bundle
                    .getString("redis.pool.maxTotal")));
            config.setMaxIdle(Integer.parseInt(bundle
                    .getString("redis.pool.maxIdle")));
            config.setMinIdle(Integer.parseInt(bundle
                    .getString("redis.pool.minIdle")));
            config.setMaxWait(Duration.ofSeconds(Long.parseLong(bundle.
                    getString("redis.pool.maxWait"))));
            config.setTestOnBorrow(Boolean.parseBoolean(bundle
                    .getString("redis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.parseBoolean(bundle
                    .getString("redis.pool.testOnReturn")));
            jedisPool = new JedisPool(config, bundle.getString("redis.ip"),
                    Integer.parseInt(bundle.getString("redis.port")));
            log.info("注册ReidsPool成功：redis地址 {} ，端口号 {} ",
                    bundle.getString("redis.ip"), Integer.parseInt(bundle.getString("redis.port")));
        }catch (Exception e){
            log.info("jedisPool init failed ",e);
        }
    }

    /**
     *从池中获取一个Jedis对象
     * @return
     */
    public static Jedis getInstance() {
        return jedisPool.getResource();
    }

    /**
     * 关闭 Jedis 连接
     */
    public static void closeJedis(Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedis.close();
        }
    }

    /**
     *在应用程序关闭时释放连接池资源
     */
    public static void destroyPool() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }




}
