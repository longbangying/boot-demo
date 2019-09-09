package com.xbang.bootdemo.test.redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.Set;

public class Redis {

    private static  JedisConnectionFactory jedisConnectionFactory;




    public static RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("47.104.171.254");
        redisStandaloneConfiguration.setPassword("redis");
        return redisStandaloneConfiguration;
    }


    public static JedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return jedisConnectionFactory;

    }

    static {
        jedisConnectionFactory =  redisConnectionFactory(redisStandaloneConfiguration());
    }



    public static void main(String[] args) {

        RedisConnection jedisConnection = jedisConnectionFactory.getConnection();
        jedisConnection.select(2);
        Set<byte[]> sets = jedisConnection.zRange("xbang".getBytes(),0,jedisConnection.zCard("xbang".getBytes()));
        for(byte[] bytes : sets){
            System.out.println(new String(bytes));
        }

        //long sss = jedisConnection.zRemRangeByScore("xbang".getBytes(),1,2);
        //System.out.println(sss);
        //jedisConnection.zAdd("xbang".getBytes(),3,"3".getBytes());

        long ss = jedisConnection.zCard("xbang".getBytes());
        System.out.println(ss);
    }



}
