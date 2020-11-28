package com.vma.push.business.entity;

import java.util.Date;

public class ShopTemplate {
    private String id;

    private String code;

    private Integer enterpriseNum;

    private Integer smallCost;

    private Integer cardCost;

    private String templateName;

    private Date createTime;

    private Date modifyTime;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEnterpriseNum() {
        return enterpriseNum;
    }

    public void setEnterpriseNum(Integer enterpriseNum) {
        this.enterpriseNum = enterpriseNum;
    }

    public Integer getSmallCost() {
        return smallCost;
    }

    public void setSmallCost(Integer smallCost) {
        this.smallCost = smallCost;
    }

    public Integer getCardCost() {
        return cardCost;
    }

    public void setCardCost(Integer cardCost) {
        this.cardCost = cardCost;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}