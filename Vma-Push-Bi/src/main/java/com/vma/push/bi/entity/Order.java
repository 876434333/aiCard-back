package com.vma.push.bi.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private String id;

    private String orderNbr;

    private String offerImg;

    private String linkPhone;

    private String linkMan;

    private String offerId;

    private Integer offerNum;

    private Date createTime;

    private Integer status;

    private BigDecimal totalPrice;

    private String userId;

    private String staffId;

    private Date payTime;

    private String enterpriseId;

    private String departmentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNbr() {
        return orderNbr;
    }

    public void setOrderNbr(String orderNbr) {
        this.orderNbr = orderNbr;
    }

    public String getOfferImg() {
        return offerImg;
    }

    public void setOfferImg(String offerImg) {
        this.offerImg = offerImg;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Integer getOfferNum() {
        return offerNum;
    }

    public void setOfferNum(Integer offerNum) {
        this.offerNum = offerNum;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}