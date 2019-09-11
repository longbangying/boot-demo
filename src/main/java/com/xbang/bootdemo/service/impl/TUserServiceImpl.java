package com.xbang.bootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xbang.bootdemo.dao.entity.TUser;
import com.xbang.bootdemo.dao.mapper.TUserMapper;
import com.xbang.bootdemo.service.face.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xbang.commons.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {
    @Autowired
    TUserMapper tUserMapper;


    @Override
    public List<TUser> list() {
        return tUserMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public String selectTest() {
        return tUserMapper.selectTest();
    }

    @Override
    public TUser checkUser(TUser tUser) throws BaseException {
        Assert.notNull(tUser,"tUser must be not null");
        TUser checkUser = this.getById(tUser.getId());
        return checkUser;
    }

    @Override
    public TUser checkUser(Long id) throws BaseException {
        return tUserMapper.selectById(id);
    }
}
