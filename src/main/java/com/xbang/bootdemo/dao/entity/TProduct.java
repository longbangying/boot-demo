package com.xbang.bootdemo.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
public class TProduct extends Model<TProduct> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 产品说明
     */
    @TableField("product_desc")
    private String productDesc;

    /**
     * 销售价格
     */
    @TableField("sales_price")
    private BigDecimal salesPrice;

    /**
     * 库存
     */
    @TableField("product_inventory")
    private Integer productInventory;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TProduct{" +
        "id=" + id +
        ", productName=" + productName +
        ", productDesc=" + productDesc +
        ", salesPrice=" + salesPrice +
        ", productInventory=" + productInventory +
        "}";
    }
}
