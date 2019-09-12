package com.xbang.bootdemo.service.impl;

import com.xbang.bootdemo.dao.entity.TTrade;
import com.xbang.bootdemo.dao.entity.TUser;
import com.xbang.bootdemo.dao.mapper.TUserMapper;
import com.xbang.bootdemo.service.face.ITProductService;
import com.xbang.bootdemo.service.face.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.exception.trade.TradeExceEnum;
import com.xbang.commons.exception.trade.TradeException;
import com.xbang.commons.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xbang
 * @since 2019-09-11
 */
@Service
@Slf4j
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

    @Autowired
    ITProductService itProductService;

    @Override
    public void deduction(TTrade tTrade) {
        itProductService.checkTrade(tTrade);

        TUser tUser = this.getById(tTrade.getUserId());

        if(null == tUser ){
            throw new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }

        if(tUser.getMoney().compareTo(tTrade.getTradeAmount()) < 0){
            throw new TradeException(TradeExceEnum.TRADE_EXCE_LACKBALANCE);
        }
        BigDecimal after = tUser.getMoney().subtract(tTrade.getTradeAmount());

        log.info("deduction {} for trade[{}]",after.toString(),tTrade.getTradeNo());
        tUser.setMoney(after);

        boolean flag = this.updateById(tUser);

        if(!flag){
            throw new BaseException(ResultEnum.RESULT_EXCEPTION);
        }



    }
}
