package com.xbang.bootdemo.dao.page;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xbang.bootdemo.dao.entity.TProduct;

public class ProductPage extends Page<TProduct> {

    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
