package com.xbang.bootdemo.service.face;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xbang.bootdemo.dao.entity.TTrade;
import com.xbang.commons.exception.BaseException;

 /*
 * <p>
 *  服务类
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */

public interface ITTradeService extends IService<TTrade> {
    TTrade placeTheOrder(TTrade tradeVo) throws BaseException;
}

