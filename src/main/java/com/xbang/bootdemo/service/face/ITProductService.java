package com.xbang.bootdemo.service.face;

import com.xbang.bootdemo.dao.entity.TProduct;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
