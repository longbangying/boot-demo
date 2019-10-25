package com.xbang.bootdemo.test;


import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class Test {

    public static void main(String[] args) {
        BigDecimal source = new BigDecimal(-1000);

        BigDecimal rate = new BigDecimal("1.000058");

        BigDecimal dest = source.multiply(rate);

        log.info("source:{} dest:{} rate:{}", source.toString(), dest.toString(), rate.toString());

        log.info("rate:{}", dest.divide(source).toString());

    }


}
