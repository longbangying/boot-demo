package com.xbang.bootdemo.service.face;

import com.xbang.bootdemo.dao.entity.TTrade;
import com.xbang.bootdemo.dao.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xbang
 * @since 2019-09-11
 */
public interface ITUserService extends IService<TUser> {

    void deduction(TTrade tTrade);



}
