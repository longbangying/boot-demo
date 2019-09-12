package com.xbang.bootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xbang.bootdemo.dao.entity.TProduct;
import com.xbang.bootdemo.dao.entity.TTrade;
import com.xbang.bootdemo.dao.mapper.TProductMapper;
import com.xbang.bootdemo.dao.page.ProductPage;
import com.xbang.bootdemo.service.face.ITProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.exception.trade.TradeExceEnum;
import com.xbang.commons.exception.trade.TradeException;
import com.xbang.commons.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
@Service
public class TProductServiceImpl extends ServiceImpl<TProductMapper, TProduct> implements ITProductService {
    @Autowired
    TProductMapper tProductMapper;


    @Override
    public TProduct checkProduct(TProduct tProduct) {
        Assert.notNull(tProduct,"tUser must be not null");
        return this.getById(tProduct.getId());
    }

    @Override
    public TProduct checkProduct(Long productId) {
        return this.getById(productId);
    }

    @Override
    public TProduct addProduct(TProduct tProduct) {
        if(null == tProduct ){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(StringUtils.isEmpty(tProduct.getProductName())){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(null == tProduct.getSalesPrice() || tProduct.getSalesPrice().compareTo(new BigDecimal("0")) <= 0){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(null == tProduct.getProductInventory() || tProduct.getProductInventory() < 0){
            throw  new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        boolean flag = this.save(tProduct);
        if(flag){
            return tProduct;
        }
        throw  new BaseException(ResultEnum.RESULT_EXCEPTION);
    }

    @Override
    public IPage selectPage(ProductPage productPage) {
        if(null == productPage){
            return this.page(new Page<>());
        }
        QueryWrapper<TProduct> wrapper = new QueryWrapper();
        if(!StringUtils.isEmpty(productPage.getProductName())){
            wrapper.eq("product_name",productPage.getProductName());
        }
        return this.page(productPage,wrapper);
    }


    //@Override
    public IPage<TProduct> page(ProductPage productPage) {
        QueryWrapper<TProduct> wrapper = new QueryWrapper();
        wrapper.eq("product_name",productPage.getProductName());
        return this.page(productPage,wrapper);
    }


    @Override
    public void inventoryReduction(TTrade tTrade) {
        checkTrade(tTrade);
        TProduct tProduct = tProductMapper.selectByIdForLock(tTrade.getProductId());//this.getById(tTrade.getProductId());
        if(null == tProduct){
            throw new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(tProduct.getProductInventory() < tTrade.getQuantity()){
            throw  new TradeException(TradeExceEnum.TRADE_EXCE_UNDERSTOCK);
        }

        tProduct.setProductInventory(tProduct.getProductInventory() - tTrade.getQuantity());

        boolean flag = this.updateById(tProduct);

        if(!flag){
            throw  new BaseException(ResultEnum.RESULT_EXCEPTION);
        }
    }

    @Override
    public void checkTrade(TTrade tTrade) {
        if(null == tTrade){
            throw new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(null == tTrade.getId() || tTrade.getId() == 0 ){
            throw new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(null == tTrade.getProductId() || tTrade.getProductId() == 0){
            throw new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if (null == tTrade.getQuantity() || tTrade.getQuantity() == 0){
            throw new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
        if(null == tTrade.getTradeAmount() || tTrade.getTradeAmount().compareTo(new BigDecimal("0")) <= 0){
            throw new BaseException(ResultEnum.RESULT_INVALID_PARAMETER);
        }
    }
}
