package com.vma.push.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserStaffRela {
    private String id;

    private String userId;

    private String staffId;

    private String enterpriseId;

    private Date createTime;

    private Date modifyTime;

    private Integer froms;

    private Integer isZan;

    private String name;

    private String mail;

    private String company;

    private String position;

    private String birthday;

    private String remark;

    private String phone;

    private Date lastAttachTime;

    private Date lastActionTime;

    private BigDecimal rate;

    private String departmentId;

    private Integer status;

    private Date clinchTime;

    private Long clinchRate;

    private String fromUserId;
    private Integer relaStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
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

    public Integer getFroms() {
        return froms;
    }

    public void setFroms(Integer froms) {
        this.froms = froms;
    }

    public Integer getIsZan() {
        return isZan;
    }

    public void setIsZan(Integer isZan) {
        this.isZan = isZan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastAttachTime() {
        return lastAttachTime;
    }

    public void setLastAttachTime(Date lastAttachTime) {
        this.lastAttachTime = lastAttachTime;
    }

    public Date getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime(Date lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getClinchTime() {
        return clinchTime;
    }

    public void setClinchTime(Date clinchTime) {
        this.clinchTime = clinchTime;
    }

    public Long getClinchRate() {
        return clinchRate;
    }

    public void setClinchRate(Long clinchRate) {
        this.clinchRate = clinchRate;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getRelaStatus() {
        return relaStatus;
    }

    public void setRelaStatus(Integer relaStatus) {
        this.relaStatus = relaStatus;
    }
}