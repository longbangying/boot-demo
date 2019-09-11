package com.xbang.bootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
@MapperScan({"com.xbang.bootdemo.dao.mapper"})
public class BootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootDemoApplication.class,args);
    }
}
