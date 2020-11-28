package com.vma.push.business.entity;

import java.util.Date;

public class PointRate {
    private String id;

    private Float oemRate;

    private Float regionalRate;

    private Float agentRate;

    private Date createTime;

    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getOemRate() {
        return oemRate;
    }

    public void setOemRate(Float oemRate) {
        this.oemRate = oemRate;
    }

    public Float getRegionalRate() {
        return regionalRate;
    }

    public void setRegionalRate(Float regionalRate) {
        this.regionalRate = regionalRate;
    }

    public Float getAgentRate() {
        return agentRate;
    }

    public void setAgentRate(Float agentRate) {
        this.agentRate = agentRate;
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
}