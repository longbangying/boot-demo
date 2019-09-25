package com.xbang.bootdemo.test.redis;

import com.xbang.bootdemo.constant.CharsetConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.Set;

@Slf4j
public class Redis {

    private static  JedisConnectionFactory jedisConnectionFactory;




    public static RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("47.104.171.254");
        redisStandaloneConfiguration.setPassword("redis");
        return redisStandaloneConfiguration;
    }


    public static JedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration){
        return new JedisConnectionFactory(redisStandaloneConfiguration);

    }

    static {
        jedisConnectionFactory =  redisConnectionFactory(redisStandaloneConfiguration());
    }



    public static void main(String[] args)  {
        String keyName = "xbang";
        RedisConnection jedisConnection = jedisConnectionFactory.getConnection();
        jedisConnection.select(2);
        Set<byte[]> sets = jedisConnection.zRange(keyName.getBytes(CharsetConstant.CHARSET),0,jedisConnection.zCard(keyName.getBytes(CharsetConstant.CHARSET)));
        for(byte[] bytes : sets){
            log.info(new String(bytes,CharsetConstant.CHARSET));
        }
        long ss = jedisConnection.zCard(keyName.getBytes(CharsetConstant.CHARSET));
        log.info("ss:{}",ss);
    }



}
