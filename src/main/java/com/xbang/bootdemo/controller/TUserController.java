package com.xbang.bootdemo.controller;



import com.xbang.bootdemo.dao.entity.TUser;
import com.xbang.bootdemo.service.face.ITUserService;
import com.xbang.commons.vo.result.BaseResult;
import com.xbang.commons.vo.result.Result;
import com.xbang.commons.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/tUser")
public class TUserController {

    @Autowired
    ITUserService itUserService;
    @RequestMapping("list")
    public Result list(){
        List<TUser> users = itUserService.list();
        return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,users);
    }

    @RequestMapping("test")
    public Result test(){
        return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,itUserService.selectTest());
    }

}

