package com.xbang.bootdemo.service.face;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xbang.bootdemo.dao.entity.TProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xbang.bootdemo.dao.entity.TTrade;
import com.xbang.bootdemo.dao.page.ProductPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
public interface ITProductService extends IService<TProduct> {

    TProduct checkProduct(TProduct tProduct);

    TProduct checkProduct(Long productId);

    TProduct addProduct(TProduct tProduct);

    IPage selectPage(ProductPage productPage);

    IPage<TProduct> page(ProductPage productPage);

    void inventoryReduction(TTrade tTrade);

    void checkTrade(TTrade tTrade);



}
