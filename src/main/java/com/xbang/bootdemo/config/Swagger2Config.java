package com.xbang.bootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .select().apis(RequestHandlerSelectors.basePackage("com.xbang.bootdemo.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder().title("bootDemo 接口文档")
                .description("SpringBoot整合Swagger")
                .version("0.1")
                .contact(new Contact("lby","www.baidu.com","1523550440@qq.com")).license("The Apache License").licenseUrl("http://www.baidu.com").build());

    }
}
