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

import java.math.BigDecimal;
import java.time.LocalDateTime;

 /*
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

     /*
     * 下单接口
     * @param tUser
     * @param tProduct
     * @return
     * @throws TradeException
     */

    @Transactional
    @Override
    public  TTrade placeTheOrder(TTrade tTrade) throws BaseException {
        if(tTrade == null || null == tTrade.getUserId() || null== tTrade.getProductId() || null == tTrade.getQuantity() || 0 == tTrade.getQuantity() ){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        TUser tUser = itUserService.getById(tTrade.getUserId());
        TProduct tProduct = itProductService.checkProduct(tTrade.getProductId().longValue());
        if(null == tUser || null == tProduct){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(tProduct.getProductInventory() <= 0 ){
            //没有库存
            throw  new TradeException(TradeExceEnum.TRADE_EXCE_UNDERSTOCK);
        }
        tTrade.setTradeStatus(1);
        tTrade.setCreateTime(LocalDateTime.now());
        tTrade.setTradeNo(DateUtils.getDateFormatTradeNo());
        tTrade.setTradeAmount(tProduct.getSalesPrice().multiply(new BigDecimal(tTrade.getQuantity())));
        boolean result = this.save(tTrade);
        if(!result){
            throw  new BaseException(ResultEnum.RESULT_EXCEPTION);
        }
        //减库存
        itProductService.inventoryReduction(tTrade);
        itUserService.deduction(tTrade);
        tTrade.setTradeStatus(2);
        result = this.updateById(tTrade);
        if(!result ){
            throw  new BaseException(ResultEnum.RESULT_EXCEPTION);
        }
        return tTrade;

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
