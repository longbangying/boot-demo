package com.xbang.bootdemo.dao.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xbang.bootdemo.dao.entity.TUser;

public class UserPage extends Page<TUser> {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
