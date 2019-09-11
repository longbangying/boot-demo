package com.xbang.bootdemo.service.face;

import com.xbang.bootdemo.dao.entity.TProduct;
import com.xbang.bootdemo.dao.entity.TTrade;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xbang.bootdemo.dao.entity.TUser;
import com.xbang.bootdemo.vo.TradeVo;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.exception.trade.TradeException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
public interface ITTradeService extends IService<TTrade> {
    TTrade placeTheOrder(TradeVo tradeVo) throws BaseException;
}
