package com.xbang.bootdemo.service.impl;

import com.xbang.bootdemo.dao.entity.TMybatisplusLockTest;
import com.xbang.bootdemo.service.face.ITMybatisplusLockTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TMybatisplusLockTestServiceImplTest {

    @Autowired
    ITMybatisplusLockTestService itMybatisplusLockTestService;

    @Test
    public void test(){
        TMybatisplusLockTest tMybatisplusLockTest = itMybatisplusLockTestService.getById(1);

        tMybatisplusLockTest.setTargetValue(tMybatisplusLockTest.getTargetValue().add(new BigDecimal("1")));
        itMybatisplusLockTestService.updateById(tMybatisplusLockTest);
    }

}