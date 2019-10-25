package com.xbang.bootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfiguration {
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("47.104.171.254");
        redisStandaloneConfiguration.setPassword("redis");
        return redisStandaloneConfiguration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return jedisConnectionFactory;

    }

    //@Bean
    public RedisConnectionFactory redisConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration);
        return jedisConnectionFactory;

    }


    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(){

        Set<String> sentinelHostAndPort = new HashSet<>();
        sentinelHostAndPort.add("192.168.153.131:6379");
        return  new RedisSentinelConfiguration("MyMaster",sentinelHostAndPort);

    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        return new StringRedisTemplate(redisConnectionFactory);
    }
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
