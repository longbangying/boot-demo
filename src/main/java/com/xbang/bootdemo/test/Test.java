package com.xbang.bootdemo.test;


import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {
        BigDecimal source = new BigDecimal(-1000);

        BigDecimal rate = new BigDecimal("1.000058");

        BigDecimal dest = source.multiply(rate);

        System.out.println("source:" + source.toString() + " dest:" + dest.toString() + " rate:" + rate.toString());

        System.out.println("rate:"+ dest.divide(source).toString());

    }




}
