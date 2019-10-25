package com.xbang.bootdemo.dao.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xbang
 * @since 2019-09-11
 */
public class TTrade extends Model<TTrade> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    @TableField("trade_no")
    private String tradeNo;

    /**
     * 订单金额
     */
    @TableField("trade_amount")
    private BigDecimal tradeAmount;

    @TableField("user_id")
    private Integer userId;

    /**
     * 产品ID
     */
    @TableField("product_id")
    private Integer productId;

    /**
     * 订单创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 订单状态 1-创建  2-订单完成 3-订单失败
     */
    @TableField("trade_status")
    private Integer tradeStatus;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 数量
     */
    @TableField("quantity_")
    private Integer quantity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TTrade{" +
                "id=" + id +
                ", tradeNo=" + tradeNo +
                ", tradeAmount=" + tradeAmount +
                ", userId=" + userId +
                ", productId=" + productId +
                ", createTime=" + createTime +
                ", tradeStatus=" + tradeStatus +
                ", updateTime=" + updateTime +
                ", quantity=" + quantity +
                "}";
    }
}
