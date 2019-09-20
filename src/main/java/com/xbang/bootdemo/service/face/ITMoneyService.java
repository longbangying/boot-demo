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

    boolean increase(Long id) throws BaseException;

    boolean decrease(Long id) throws BaseException;
    //悲观锁
    void increaseWithLock(Long id) throws BaseException;

    void decreaseWithLock(Long id) throws BaseException;

    //乐观锁
    void increaseWithOptimisticLock (Long id) throws BaseException;

    void decreaseWithOptimisticLock(Long id) throws BaseException;
}
