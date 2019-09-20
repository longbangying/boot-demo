package com.xbang.bootdemo.service.impl;

import com.xbang.bootdemo.dao.entity.TMoney;
import com.xbang.bootdemo.dao.mapper.TMoneyMapper;
import com.xbang.bootdemo.service.face.ITMoneyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.vo.result.ResultEnum;
import org.springframework.stereotype.Service;

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
@Service
public class TMoneyServiceImpl extends ServiceImpl<TMoneyMapper, TMoney> implements ITMoneyService {

    private final Lock lock = new ReentrantLock();


    @Override
    public void increase(Long id) {

        lock.lock();
        try {
            TMoney tMoney = this.getById(id);
            if(tMoney == null ){
                throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
            }

            //自增
            tMoney.setMoney(tMoney.getMoney().add(new BigDecimal("1")));
            this.updateById(tMoney);
        }catch (BaseException ex){
            throw  ex;
        }finally {
            lock.unlock();
        }


    }

    @Override
    public void decrease(Long id) {
        lock.lock();

        try {
            TMoney tMoney = this.getById(id);
            if(tMoney == null ){
                throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
            }
            if(tMoney.getMoney().compareTo(new BigDecimal("1")) < 0 ){
                throw  new BaseException("余额不足");
            }
            //自减
            tMoney.setMoney(tMoney.getMoney().subtract(new BigDecimal("1")));
            this.updateById(tMoney);
        } catch (BaseException e) {
            throw  e;
        }finally {
            lock.unlock();
        }
    }
}
