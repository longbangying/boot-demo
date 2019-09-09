package com.xbang.bootdemo.controller;

import com.example.commons.vo.result.BaseResult;
import com.example.commons.vo.result.Result;
import com.example.commons.vo.result.ResultEnum;
import com.xbang.bootdemo.annotation.LimitConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("limit")
public class LimitController {

    @RequestMapping("test")
    @LimitConfig(unit = TimeUnit.MINUTES)
    public Result test(@RequestParam ("userName") String userName){
        Map resultMap = new HashMap();
        resultMap.put("userName",userName);
        try {
            Thread.sleep(15 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,resultMap);
    }
}
