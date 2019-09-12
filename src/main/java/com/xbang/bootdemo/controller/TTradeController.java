package com.xbang.bootdemo.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.xbang.bootdemo.dao.entity.TTrade;
import com.xbang.bootdemo.service.face.ITTradeService;
import com.xbang.bootdemo.vo.TradeVo;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.vo.result.BaseResult;
import com.xbang.commons.vo.result.Result;
import com.xbang.commons.vo.result.ResultEnum;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
@RestController
@RequestMapping("/tTrade")
@Slf4j
public class TTradeController {
    @Autowired
    ITTradeService itTradeService;

    @RequestMapping(value = "placeOrder",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    //@Synchronized
    public
    Result placeTheTrade(@RequestBody TTrade tradeVo){
        log.info("param:{}", JSON.toJSONString(tradeVo));
        try {
            return  BaseResult.getResult(ResultEnum.RESULT_SUCCESS,itTradeService.placeTheOrder(tradeVo));
        } catch (BaseException e) {
            log.error(e.getMessage(),e);
            return Result.getResult(e.getError_msg());
        }
    }


}

