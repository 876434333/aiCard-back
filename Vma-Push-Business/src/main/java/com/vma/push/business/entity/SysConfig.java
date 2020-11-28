package com.vma.push.business.entity;

public class SysConfig {
    private Integer id;

    private String imPrivate;

    private String imPublick;

    private String imSdkApp;

    private String accountType;

    private String wxPayCallbackUrl;

    private String branch;

    private String imgUrl;

    private String mobileLogo;

    private String wechatLogo;

    private String locationLogo;

    private String qiniuAccessKey;

    private String qiniuSecretKey;

    private String qiniuUrl;

    private String qiniuZone;

    private String qiniuBucket;

    //Add by plh at 2018-11-01 for 平台模式新增员工默认放在同一的部门下面
    private String platformDefaultDeptId;
    // 平台企业微信ID
    private String platformCorpId;
    // 平台小程序ID
    private String platformMiniAppId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImPrivate() {
        return imPrivate;
    }

    public void setImPrivate(String imPrivate) {
        this.imPrivate = imPrivate;
    }

    public String getImPublick() {
        return imPublick;
    }

    public void setImPublick(String imPublick) {
        this.imPublick = imPublick;
    }

    public String getImSdkApp() {
        return imSdkApp;
    }

    public void setImSdkApp(String imSdkApp) {
        this.imSdkApp = imSdkApp;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getWxPayCallbackUrl() {
        return wxPayCallbackUrl;
    }

    public void setWxPayCallbackUrl(String wxPayCallbackUrl) {
        this.wxPayCallbackUrl = wxPayCallbackUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMobileLogo() {
        return mobileLogo;
    }

    public void setMobileLogo(String mobileLogo) {
        this.mobileLogo = mobileLogo;
    }

    public String getWechatLogo() {
        return wechatLogo;
    }

    public void setWechatLogo(String wechatLogo) {
        this.wechatLogo = wechatLogo;
    }

    public String getLocationLogo() {
        return locationLogo;
    }

    public void setLocationLogo(String locationLogo) {
        this.locationLogo = locationLogo;
    }

    public String getQiniuAccessKey() {
        return qiniuAccessKey;
    }

    public void setQiniuAccessKey(String qiniuAccessKey) {
        this.qiniuAccessKey = qiniuAccessKey;
    }

    public String getQiniuSecretKey() {
        return qiniuSecretKey;
    }

    public void setQiniuSecretKey(String qiniuSecretKey) {
        this.qiniuSecretKey = qiniuSecretKey;
    }

    public String getQiniuUrl() {
        return qiniuUrl;
    }

    public void setQiniuUrl(String qiniuUrl) {
        this.qiniuUrl = qiniuUrl;
    }

    public String getQiniuBucket() {
        return qiniuBucket;
    }

    public void setQiniuBucket(String qiniuBucket) {
        this.qiniuBucket = qiniuBucket;
    }

    public String getPlatformDefaultDeptId() {
        return platformDefaultDeptId;
    }

    public void setPlatformDefaultDeptId(String platformDefaultDeptId) {
        this.platformDefaultDeptId = platformDefaultDeptId;
    }

    public String getPlatformCorpId() {
        return platformCorpId;
    }

    public void setPlatformCorpId(String platformCorpId) {
        this.platformCorpId = platformCorpId;
    }

    public String getPlatformMiniAppId() {
        return platformMiniAppId;
    }

    public void setPlatformMiniAppId(String platformMiniAppId) {
        this.platformMiniAppId = platformMiniAppId;
    }

    public String getQiniuZone() {
        return qiniuZone;
    }

    public void setQiniuZone(String qiniuZone) {
        this.qiniuZone = qiniuZone;
    }
}