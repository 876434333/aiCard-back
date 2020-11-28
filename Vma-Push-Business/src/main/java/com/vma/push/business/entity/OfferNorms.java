package com.vma.push.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OfferNorms {
    private String id;

    private String name;

    private String offerId;

    private BigDecimal offerPrice;

    private Integer offerLeave;

    private Integer isDefault;

    private Integer normsType;

    private Date createTime;

    private Integer status;

    private String normsPic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Integer getOfferLeave() {
        return offerLeave;
    }

    public void setOfferLeave(Integer offerLeave) {
        this.offerLeave = offerLeave;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getNormsType() {
        return normsType;
    }

    public void setNormsType(Integer normsType) {
        this.normsType = normsType;
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

    public String getNormsPic() {
        return normsPic;
    }

    public void setNormsPic(String normsPic) {
        this.normsPic = normsPic;
    }
}