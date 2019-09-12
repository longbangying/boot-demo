package com.xbang.bootdemo.cache;

import com.alibaba.fastjson.JSON;
import com.xbang.bootdemo.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Component
public class RedisCache implements Cache, ApplicationContextAware {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    @Autowired
    RedisTemplate redisTemplate;

    private String id;

    private Integer datasource;

    private ApplicationContext applicationContext;

    public RedisCache(String id, Integer datasource) {
        this.id = id;
        this.datasource = datasource;
    }

    public RedisCache(String id) {
       this(id,0);
    }

    public RedisCache() {
        this("xbang");
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        checkRedisTemplate();
        if(null != value &&  null != key){
            //getConnection().set(key.)
            log.info("key:{},value:{}", JSON.toJSONString(key),JSON.toJSONString(value));
            redisTemplate.opsForValue().set(key,value);
        }
    }

    @Override
    public Object getObject(Object key) {
        checkRedisTemplate();
        Object result = null;
        if(null != key){
            result =  redisTemplate.opsForValue().get(key);
        }
        log.info("key:{}",key);
        log.info("key:{},value:{}", JSON.toJSONString(key),JSON.toJSONString(result));
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        checkRedisTemplate();
        Object value = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return value;
    }

    @Override
    public void clear() {
        checkRedisTemplate();
        Set<String> keys = redisTemplate.keys("*");
        if(keys == null || keys.isEmpty()){
            return ;
        }
        long result = redisTemplate.delete(keys);
        log.info("Redis Cache has {} keys and clear {} keys",keys.size(),result);

    }

    @Override
    public int getSize() {
        checkRedisTemplate();
        Set<String> keys = redisTemplate.keys("*");
        return null == keys ? 0 : keys.size();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("对象:" + this.hashCode());
        this.applicationContext = applicationContext;
    }


    private void checkRedisTemplate(){
        log.info("对象1" + this.hashCode());
        if(null == redisTemplate ){
            redisTemplate = (RedisTemplate)SpringUtils.getApplicationContext().getBean("redisTemplate");
        }
        if(null == redisTemplate){
            throw  new RuntimeException("redisTemplate is null");
        }

    }

    public RedisConnection getConnection(){
        checkRedisTemplate();
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        redisConnection.select(getDatasource());
        return redisConnection;
    }

    public Integer getDatasource() {
        return datasource;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
