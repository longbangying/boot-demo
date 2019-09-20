package com.xbang.bootdemo.service.impl;

import com.xbang.bootdemo.dao.entity.TMoney;
import com.xbang.bootdemo.dao.mapper.TMoneyMapper;
import com.xbang.bootdemo.service.face.ITMoneyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbang.bootdemo.utils.TOOL;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xbang
 * @since 2019-09-20
 */
@Slf4j
@Service
public class TMoneyServiceImpl extends ServiceImpl<TMoneyMapper, TMoney> implements ITMoneyService {

    private static final Integer MAX_RETRY_TIME = 15;

    private final Lock lock = new ReentrantLock();

    @Transactional
    @Override
    public boolean increase(Long id) {
        TMoney tMoney = this.getById(id);
        if(tMoney == null ){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }

        //自增
        tMoney.setMoney(tMoney.getMoney().add(new BigDecimal("1")));
        return this.updateById(tMoney);
    }

    @Transactional
    @Override
    public boolean decrease(Long id) {
        TMoney tMoney = this.getById(id);
        if(tMoney == null ){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(tMoney.getMoney().compareTo(new BigDecimal("1")) < 0 ){
            throw  new BaseException("余额不足");
        }
        //自减
        tMoney.setMoney(tMoney.getMoney().subtract(new BigDecimal("1")));
        return this.updateById(tMoney);
    }

    /**
     * 悲观锁自增
     * @param id
     * @throws BaseException
     */
    @Override
    public void increaseWithLock(Long id) throws BaseException {
        lock.lock();
        try {
            increase(id);
        } catch (BaseException e) {
            throw  e;
        }finally {
            lock.unlock();
        }

    }

    /**
     * 悲观锁自减
     * @param id
     * @throws BaseException
     */
    @Override
    public void decreaseWithLock(Long id) throws BaseException {
        lock.lock();
        try {
            decrease(id);
        }catch (BaseException ex){
            throw  ex;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void increaseWithOptimisticLock(Long id) throws BaseException {
        int retry = 0;
        boolean flag = Boolean.FALSE;
        while (retry <= MAX_RETRY_TIME  ){
            flag = increase(id);
            if(flag){
                break;
            }
            retry ++;
        }
        log.info("id:{} retry:{} result:{}",id,retry,flag );
        statistical(flag);
    }

    @Override
    public void decreaseWithOptimisticLock(Long id) throws BaseException {
        int retry = 0;
        boolean flag = false;
        while (retry <= MAX_RETRY_TIME){
            flag = decrease(id);
            if(flag){
                break;
            }
            retry ++;
        }
        log.info("id:{} retry:{} result:{}",id,retry,flag );
        statistical(flag);
    }


    private void statistical(boolean flag){
        if(!flag  ){
            TOOL.fail();
            throw new BaseException("更新失败");
        }
        TOOL.success();
    }
}
