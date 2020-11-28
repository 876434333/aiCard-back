package com.vma.push.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDetail {
    private String id;

    private String orderId;

    private String offerId;

    private String normsId;

    private Integer offerNum;

    private BigDecimal offerPrice;

    private BigDecimal offerPriceDiscount;

    private BigDecimal totalPrice;

    private BigDecimal totalPriceDiscount;

    private BigDecimal charges;

    private String staffId;

    private Date createTime;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getNormsId() {
        return normsId;
    }

    public void setNormsId(String normsId) {
        this.normsId = normsId;
    }

    public Integer getOfferNum() {
        return offerNum;
    }

    public void setOfferNum(Integer offerNum) {
        this.offerNum = offerNum;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public BigDecimal getOfferPriceDiscount() {
        return offerPriceDiscount;
    }

    public void setOfferPriceDiscount(BigDecimal offerPriceDiscount) {
        this.offerPriceDiscount = offerPriceDiscount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPriceDiscount() {
        return totalPriceDiscount;
    }

    public void setTotalPriceDiscount(BigDecimal totalPriceDiscount) {
        this.totalPriceDiscount = totalPriceDiscount;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}