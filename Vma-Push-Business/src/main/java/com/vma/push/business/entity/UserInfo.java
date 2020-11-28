package com.vma.push.business.entity;

import java.util.Date;

public class UserInfo {
    private String id;

    private String openId;

    private Date createTime;

    private Date modifyTime;

    private String wxSoftId;

    private String headIcon;

    private String nickName;

    private Integer sex;

    private Date lastActionTime;

    private Date lastAttachTime;

    private String name;

    private String hxImLogin;

    private String hxImPassword;

    private String phone;

    private String lastStaffId;

    private String fromUserId;

    private String lastEnterpriseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getWxSoftId() {
        return wxSoftId;
    }

    public void setWxSoftId(String wxSoftId) {
        this.wxSoftId = wxSoftId;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime(Date lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    public Date getLastAttachTime() {
        return lastAttachTime;
    }

    public void setLastAttachTime(Date lastAttachTime) {
        this.lastAttachTime = lastAttachTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHxImLogin() {
        return hxImLogin;
    }

    public void setHxImLogin(String hxImLogin) {
        this.hxImLogin = hxImLogin;
    }

    public String getHxImPassword() {
        return hxImPassword;
    }

    public void setHxImPassword(String hxImPassword) {
        this.hxImPassword = hxImPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastStaffId() {
        return lastStaffId;
    }

    public void setLastStaffId(String lastStaffId) {
        this.lastStaffId = lastStaffId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getLastEnterpriseId() {
        return lastEnterpriseId;
    }

    public void setLastEnterpriseId(String lastEnterpriseId) {
        this.lastEnterpriseId = lastEnterpriseId;
    }
}