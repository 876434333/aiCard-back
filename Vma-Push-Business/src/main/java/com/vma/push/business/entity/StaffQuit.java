package com.vma.push.business.entity;

import java.util.Date;

public class StaffQuit {
    private String id;

    private String quitStaffId;

    private String receiverStaffId;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuitStaffId() {
        return quitStaffId;
    }

    public void setQuitStaffId(String quitStaffId) {
        this.quitStaffId = quitStaffId;
    }

    public String getReceiverStaffId() {
        return receiverStaffId;
    }

    public void setReceiverStaffId(String receiverStaffId) {
        this.receiverStaffId = receiverStaffId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}