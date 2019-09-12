package com.xbang.bootdemo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.xbang.bootdemo.dao.entity.TProduct;
import com.xbang.bootdemo.dao.page.ProductPage;
import com.xbang.bootdemo.service.face.ITProductService;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.vo.result.BaseResult;
import com.xbang.commons.vo.result.Result;
import com.xbang.commons.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
@RestController
@RequestMapping("/tProduct")
@Slf4j
public class TProductController {

    @Autowired
    ITProductService itProductService;
    @RequestMapping(value = "addProduct",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result addProduct(@RequestBody TProduct tProduct){
        try{
            return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,itProductService.addProduct(tProduct));
        }catch (BaseException ex){
            log.error("{} : {}",ex.getError_code(),ex.getError_msg());
            return BaseResult.getResult(ResultEnum.RESULT_EXCEPTION,null);
        }
    }

    @RequestMapping(value = "list",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public Result productList(@RequestBody ProductPage productPage){
        return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,itProductService.selectPage(productPage));
    }




}

