package com.xbang.bootdemo.service.impl;

import com.xbang.bootdemo.dao.entity.TProduct;
import com.xbang.bootdemo.dao.entity.TTrade;
import com.xbang.bootdemo.dao.entity.TUser;
import com.xbang.bootdemo.dao.mapper.TTradeMapper;
import com.xbang.bootdemo.service.face.ITProductService;
import com.xbang.bootdemo.service.face.ITTradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbang.bootdemo.service.face.ITUserService;
import com.xbang.bootdemo.vo.TradeVo;
import com.xbang.commons.DateUtils;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.exception.trade.TradeExceEnum;
import com.xbang.commons.exception.trade.TradeException;
import com.xbang.commons.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
@Service
public class TTradeServiceImpl extends ServiceImpl<TTradeMapper, TTrade> implements ITTradeService {

    @Autowired
    TTradeMapper tTradeMapper;

    @Autowired
    ITUserService itUserService;

    @Autowired
    ITProductService itProductService;

    /**
     * 下单接口
     * @param tUser
     * @param tProduct
     * @return
     * @throws TradeException
     */
    @Transactional
    @Override
    public TTrade placeTheOrder(TradeVo tradeVo) throws BaseException {
        if(tradeVo == null ){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        TUser tUser = itUserService.checkUser(tradeVo.getUserId());
        TProduct tProduct = itProductService.checkProduct(tradeVo.getProductId());

        if(null == tUser || null == tProduct){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }

        if(tProduct.getProductInventory() <= 0 ){
            //没有库存
            throw  new TradeException(TradeExceEnum.TRADE_EXCE_UNDERSTOCK);
        }
        TTrade tTrade = generateTrade(tUser,tProduct);

        boolean result = this.save(tTrade);
        if(result ){
            return tTrade;
        }
        throw  new BaseException(ResultEnum.RESULT_EXCEPTION);
    }


    private TTrade generateTrade(TUser tUser, TProduct tProduct){
        TTrade tTrade = new TTrade();
        tTrade.setProductId(tProduct.getId().intValue());
        tTrade.setUserId(tUser.getId().intValue());
        tTrade.setTradeStatus(1);
        tTrade.setCreateTime(LocalDateTime.now());
        tTrade.setTradeNo(DateUtils.getDateFormatTradeNo());
        tTrade.setTradeAmount(tProduct.getSalesPrice());
        return tTrade;
    }
}
