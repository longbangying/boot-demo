package com.xbang.bootdemo.utils;


import com.xbang.commons.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class DistributedLockUtils {

    private ThreadLocal<RedisConnection> threadLocal = new ThreadLocal<>();

    private RedisConnectionFactory redisConnectionFactory;

    private static Map<String,RuntimeInfo> runtimeInfoMap =  new ConcurrentHashMap<>(1000);

    @Autowired
    public DistributedLockUtils(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }


    public boolean lock(String key,String value,boolean waitFor){
        RedisConnection redisConnection = getConnection();
        String requestId = (String) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getAttribute("requestId");
        statisticalCount(requestId);
        boolean lockFlag = redisConnection.setNX(key.getBytes(),value.getBytes());
        if(!lockFlag && waitFor){
            park(requestId);
            lock(key,value,Boolean.TRUE);
        }
        log.info("requestId:{}" ,requestId);
        return lockFlag;
    }


    public void unlock(String key){
        RedisConnection redisConnection = getConnection();
        redisConnection.del(key.getBytes());
        closeConnection(redisConnection);
        end();
    }


    public RedisConnection getConnection(){
        RedisConnection redisConnection = threadLocal.get();
        if(null == redisConnection){
            redisConnection = redisConnectionFactory.getConnection();
        }
        if(null == redisConnection){
            throw new BaseException("获取不到redis连接");
        }
        threadLocal.set(redisConnection);
        return redisConnection;
    }


    private void closeConnection(RedisConnection redisConnection){
        if(null != redisConnection){
            threadLocal.remove();
            redisConnection.close();
        }

    }

    private void park(long times){
        try {
            Thread.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void park(String requestId){
        RuntimeInfo  runtimeInfo = runtimeInfoMap.get(requestId);
        if(null == runtimeInfo){
            park(100);
        }else{
            park(runtimeInfo.getLoopCount() * 5);
        }
    }



    private void  statisticalCount(String requestId){
        RuntimeInfo  runtimeInfo = runtimeInfoMap.get(requestId);
        if(null == runtimeInfo ){
            runtimeInfo = new RuntimeInfo(requestId);
        }else{
            runtimeInfo.increaseLoopCount();
        }
        runtimeInfoMap.put(requestId,runtimeInfo);
    }

    private void end(){
        String requestId = (String) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getAttribute("requestId");
        RuntimeInfo  runtimeInfo = runtimeInfoMap.get(requestId);
        if(null != runtimeInfo){
            runtimeInfo.end();
        }
    }

    public Map getStatisticalInfo(){

        Map<String,Object> resultMap = new HashMap<>();
        Map<String,RuntimeInfo> temp = new HashMap<>(runtimeInfoMap);
        Integer min = 1;
        Integer max = min;
        RuntimeInfo minRuntimeInfo = null;
        RuntimeInfo maxRuntimeInfo = null;

        Iterator iterator = temp.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String,RuntimeInfo> entry = (Map.Entry)iterator.next();
            RuntimeInfo runtimeInfo = entry.getValue();
            if(runtimeInfo.getLoopCount() <= min){
                minRuntimeInfo = runtimeInfo;
                min = runtimeInfo.getLoopCount();
            }
            if(max < runtimeInfo.getLoopCount()){
                max = runtimeInfo.getLoopCount();
                maxRuntimeInfo = runtimeInfo;
            }


        }
        resultMap.put("max",maxRuntimeInfo);
        resultMap.put("min",minRuntimeInfo);
        resultMap.put("info",temp);
        resultMap.put("size",temp.size());

        return resultMap;
    }


    public Map clearStatisticalInfo(){
        Map<String,Object> resultMap = getStatisticalInfo();
        if(!runtimeInfoMap.isEmpty()){
            runtimeInfoMap.clear();
        }
        return resultMap;
    }

    public static  class RuntimeInfo{

        private String requestId;

        private Integer loopCount;

        private long startTime;

        private long endtime;

        private long time;

        public RuntimeInfo(String requestId, Integer loopCount) {
            this.requestId = requestId;
            this.loopCount = loopCount;
            this.startTime = System.currentTimeMillis();
        }

        public RuntimeInfo(String requestId) {
            this(requestId,1);
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public Integer getLoopCount() {
            return loopCount;
        }

        public void setLoopCount(Integer loopCount) {
            this.loopCount = loopCount;
        }

        public synchronized void increaseLoopCount(){
            this.loopCount ++;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public void end(){
            this.endtime = System.currentTimeMillis();
            this.time = this.endtime - this.startTime;
        }


    }



}
