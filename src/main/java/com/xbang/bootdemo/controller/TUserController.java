package com.xbang.bootdemo.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xbang
 * @since 2019-09-11
 */
@RestController
@RequestMapping("/tUser")
public class TUserController {
    @RequestMapping("test")
    public String test(){
        return "success!";
    }
}

