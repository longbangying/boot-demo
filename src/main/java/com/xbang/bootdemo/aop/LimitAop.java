package com.xbang.bootdemo.aop;

import com.example.commons.vo.result.BaseResult;
import com.example.commons.vo.result.ResultEnum;
import com.xbang.bootdemo.annotation.LimitConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Aspect
@Slf4j
public class LimitAop {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Pointcut("@annotation(com.xbang.bootdemo.annotation.LimitConfig)")
    public void pointcut (){

    }


    @Around("pointcut()")
    public Object  around(ProceedingJoinPoint joinPoint) throws Throwable{
        Class clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Method targetMethod = getMethodByName(clazz,methodName);
        if(null == targetMethod){
            return joinPoint.proceed(joinPoint.getArgs());
        }
        LimitConfig limitConfig = targetMethod.getAnnotation(LimitConfig.class);

        if(null == limitConfig ){
            return joinPoint.proceed(joinPoint.getArgs());
        }

        if(allow(limitConfig,(String) joinPoint.getArgs()[0])){
            return joinPoint.proceed(joinPoint.getArgs());
        }
        return BaseResult.getResult(ResultEnum.RESULT_ACCESS_LIMIT,"");
    }


    /**
     * tong
     * @param clazz
     * @param methodName
     * @return
     */
    public Method getMethodByName(Class clazz,String methodName){
       Method [] methods = clazz.getMethods();
       for(Method method : methods){
           if(method.getName() .equals(methodName)){
               return method;
           }
       }
       return null;
    }

    public boolean allow(LimitConfig limitConfig,String userName){
        RedisConnection redisConnection = null;
        try {
            redisConnection = redisConnectionFactory.getConnection();
            redisConnection.select(2);
            long currentTimeMillis  = System.currentTimeMillis();
            long zRems = redisConnection.zRemRangeByScore(userName.getBytes(),0,currentTimeMillis - limitConfig.unit().toMillis(limitConfig.time()));

            long currentSize = redisConnection.zCard(userName.getBytes());
            if(currentSize >= limitConfig.value()){
                return false;
            }
            redisConnection.zAdd(userName.getBytes(),currentTimeMillis,(currentTimeMillis+ "").getBytes());

            log.info("Key {} currentSize {}",userName,redisConnection.zCard(userName.getBytes()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(redisConnection != null ){
                redisConnection.close();
            }
        }
        return false;
    }

}
