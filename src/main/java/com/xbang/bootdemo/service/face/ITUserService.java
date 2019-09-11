package com.xbang.bootdemo.service.face;

import com.xbang.bootdemo.dao.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xbang.commons.exception.BaseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
public interface ITUserService extends IService<TUser> {

    String selectTest();

    TUser checkUser(TUser tUser) throws BaseException;


    TUser checkUser(Long id) throws BaseException;
}
