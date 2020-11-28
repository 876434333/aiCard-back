package com.vma.push.business.entity;

import java.util.Date;

public class EnterpriseFileDir {
    private String id;

    private String enterpriseId;

    private String dirName;

    private Date createTime;

    private String dirPassword;
    private int showOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDirPassword() {
        return dirPassword;
    }

    public void setDirPassword(String dirPassword) {
        this.dirPassword = dirPassword;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }
}