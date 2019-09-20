package com.xbang.bootdemo.service.face;

import com.xbang.bootdemo.dao.entity.TMoney;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xbang.commons.exception.BaseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xbang
 * @since 2019-09-20
 */
public interface ITMoneyService extends IService<TMoney> {

    void increase(Long id) throws BaseException;

    void decrease(Long id) throws BaseException;

}
