package com.xbang.bootdemo.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TOOL {

    private static final AtomicLong success = new AtomicLong(0);

    private static final AtomicLong fail = new AtomicLong(0);


    public static  void success(){
        success.incrementAndGet();
    }

    public static  void fail(){
        fail.incrementAndGet();
    }

    public static Long  getSuccessCount(){
        return success.get();
    }

    public static Long getFailCount(){
        return fail.get();
    }

    public static Long getTotal(){
        return getSuccessCount() + getFailCount();
    }

    public static void clear(){
        success.set(0);
        fail.set(0);
    }

}
