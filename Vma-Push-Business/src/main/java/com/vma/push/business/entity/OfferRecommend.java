package com.vma.push.business.entity;

import java.util.Date;

public class OfferRecommend {
    private String id;

    private String offerId;

    private String staffId;

    private String enterpriseId;

    private Integer sort;

    private Integer status;

    private Date createTime;

    private String voiceIntro;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVoiceIntro() {
        return voiceIntro;
    }

    public void setVoiceIntro(String voiceIntro) {
        this.voiceIntro = voiceIntro;
    }
}