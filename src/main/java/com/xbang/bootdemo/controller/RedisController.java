package com.xbang.bootdemo.controller;

import com.xbang.commons.vo.result.BaseResult;
import com.xbang.commons.vo.result.Result;
import com.xbang.commons.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
public class RedisController {

    private final StringRedisTemplate stringRedisTemplate;
    @Autowired
    public RedisController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @RequestMapping("/keys")
    public Result keys (){
        String value = stringRedisTemplate.opsForValue().get("bang");
        return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,value);
    }
}
