package com.xbang.bootdemo.service.impl;

import com.xbang.bootdemo.dao.entity.TProduct;
import com.xbang.bootdemo.dao.mapper.TProductMapper;
import com.xbang.bootdemo.service.face.ITProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
}
