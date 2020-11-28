package com.vma.push.business.entity;

import com.vma.push.business.dto.req.ReqAddCircle;
import com.vma.push.business.dto.req.ReqUpdateCircle;
import com.vma.push.business.util.UuidUtil;

import java.util.Date;

public class CircleImg {
    private String id;

    private String imgUrl;

    private String circleId;

    private Date createTime;

    private Date modifyTime;

    private Integer status;

    private Integer iOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
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

    public Integer getiOrder() {
        return iOrder;
    }

    public void setiOrder(Integer iOrder) {
        this.iOrder = iOrder;
    }

    public CircleImg(){};

    public CircleImg(ReqAddCircle reqAddCircle){
        this.setId(UuidUtil.getRandomUuid());
        this.setStatus(1);
        this.setiOrder(1);
    }

    public CircleImg(ReqUpdateCircle reqUpdateCircle){
        this.setId(UuidUtil.getRandomUuid());
        this.setStatus(1);
        this.setiOrder(1);
    }
}