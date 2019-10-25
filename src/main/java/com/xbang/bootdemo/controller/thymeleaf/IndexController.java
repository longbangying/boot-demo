package com.xbang.bootdemo.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("thymeleaf")
public class IndexController {
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("login")
    public String login(){
        return "login";
    }
}
