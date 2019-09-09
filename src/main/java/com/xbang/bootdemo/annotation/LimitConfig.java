package com.xbang.bootdemo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitConfig {
    //单位时间内允许访问的次数  默认是5
    int value() default  5;
    //单位时间 默认是1
    long time() default  1;
    //单位时间的单位 默认是秒
    TimeUnit unit() default TimeUnit.SECONDS;


    //默认每秒钟可以请求5次
}
