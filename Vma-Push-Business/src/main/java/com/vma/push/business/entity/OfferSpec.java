package com.vma.push.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OfferSpec {
    private String id;

    private String offerName;

    private BigDecimal offerPrice;

    private Date createTime;

    private Date updateTime;

    private String code;

    private String createStaffId;

    private Integer status;

    private String enterpriseId;

    private BigDecimal discount;

    private String version;

    private Integer offerSale;

    private Integer isPayOnline;

    private Integer isDelete;

    private Date onsaleTime;

    private Integer extractType;

    private BigDecimal extractValue;

    private BigDecimal extractPer;

    private Integer type;

    private BigDecimal expressFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(String createStaffId) {
        this.createStaffId = createStaffId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getOfferSale() {
        return offerSale;
    }

    public void setOfferSale(Integer offerSale) {
        this.offerSale = offerSale;
    }

    public Integer getIsPayOnline() {
        return isPayOnline;
    }

    public void setIsPayOnline(Integer isPayOnline) {
        this.isPayOnline = isPayOnline;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getOnsaleTime() {
        return onsaleTime;
    }

    public void setOnsaleTime(Date onsaleTime) {
        this.onsaleTime = onsaleTime;
    }

    public Integer getExtractType() {
        return extractType;
    }

    public void setExtractType(Integer extractType) {
        this.extractType = extractType;
    }

    public BigDecimal getExtractValue() {
        return extractValue;
    }

    public void setExtractValue(BigDecimal extractValue) {
        this.extractValue = extractValue;
    }

    public BigDecimal getExtractPer() {
        return extractPer;
    }

    public void setExtractPer(BigDecimal extractPer) {
        this.extractPer = extractPer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }
}